"""FastAPI drop-in replacement for the Java job-portal-ai-service.

The service deliberately keeps the Java service's public routes and ApiResponse
envelope. It has no database or service-to-service dependency; Gemini is its
only integration.
"""
from __future__ import annotations

import json
import io
import base64
import os
import re
import time
from datetime import datetime
from threading import Thread
from typing import Any

import httpx
from fastapi import FastAPI, Request, UploadFile
from fastapi.exceptions import RequestValidationError
from fastapi.responses import JSONResponse

app = FastAPI(title="job-portal-ai-service", docs_url=None, redoc_url=None)

APPLICATION_SYSTEM = """You are a senior technical recruiter and career coach with 15+ years of experience in the Indian tech industry.
You specialize in candidate evaluation, cover letter writing, skills gap analysis, and career development.
Always provide objective, fair, and actionable assessments based only on the information provided.
When asked for JSON, respond ONLY with valid JSON — no explanation, no markdown fences."""
COMPANY_SYSTEM = """You are an expert employer branding copywriter specializing in company profiles for job portals.
You write authentic, inspiring content that appeals to job seekers without corporate jargon.
Write in a warm, human tone that reflects real company culture and values.
When asked for JSON, respond ONLY with valid JSON — no explanation, no markdown fences."""
JOB_SYSTEM = """You are a senior HR professional and technical recruiter with deep knowledge of the Indian job market (2025-2026).
You specialize in writing job descriptions, compensation benchmarking, and talent acquisition.
Always write in a professional, engaging, inclusive, and bias-free tone.
When asked for JSON, respond ONLY with valid JSON — no explanation, no markdown fences."""
RESUME_SYSTEM = """You are a senior resume writer and career coach with 15+ years of experience in the Indian tech job market.
You specialize in ATS-optimized resumes, career coaching, and professional branding.
Always be specific and results-oriented. Never use generic phrases like "hard-working", "team player", or "passionate".
When asked for JSON, respond ONLY with valid JSON — no explanation, no markdown fences."""
SEARCH_SYSTEM = """You are a job search expert and career advisor with deep knowledge of the Indian job market.
You extract structured search criteria from natural language and provide data-driven career recommendations.
Always use the exact enum values specified in the prompt — never invent new values.
When asked for JSON, respond ONLY with valid JSON — no explanation, no markdown fences."""

APPLICATION_SYSTEM += "\n"
COMPANY_SYSTEM += "\n"
JOB_SYSTEM += "\n"
RESUME_SYSTEM += "\n"
SEARCH_SYSTEM += "\n"


def configured(name: str, default: str) -> str:
    """Read environment settings first, then the unchanged Spring YAML file."""
    env_name = {
        "key": ("GEMINI_API_KEY", "GOOGLE_API_KEY"),
        "model": ("GEMINI_MODEL",),
        "max-output-tokens": ("GEMINI_MAX_OUTPUT_TOKENS",),
        "temperature": ("GEMINI_TEMPERATURE",),
    }[name]
    for candidate in env_name:
        if os.getenv(candidate):
            return os.environ[candidate]
    config_paths = (
        os.path.join(os.path.dirname(__file__), "application.yaml"),
        os.path.join(os.path.dirname(__file__), "src", "main", "resources", "application.yaml"),
    )
    for config_path in config_paths:
        try:
            config = open(config_path, encoding="utf-8").read()
            match = re.search(rf"^\s+{re.escape(name)}:\s*(.+?)\s*$", config, flags=re.MULTILINE)
            if match and match.group(1) not in {"api key", "null", "~"}:
                return match.group(1).strip().strip('"\'')
        except OSError:
            continue
    return default


def envelope(message: str, data: Any) -> dict[str, Any]:
    return {"message": message, "success": True, "data": data}


def java_prompt(template: str, *values: Any) -> str:
    # Java String.formatted renders a null reference as the literal "null".
    return (template % tuple("null" if value is None else value for value in values)) + "\n"


def text_response(content: str) -> dict[str, Any]:
    return {"content": content, "generatedAt": datetime.now().isoformat(timespec="seconds")}


def value(body: dict[str, Any], key: str, default: Any = "Not provided") -> Any:
    v = body.get(key)
    return default if v is None or v == "" else v


def java_value(body: dict[str, Any], key: str, default: Any) -> Any:
    value = body.get(key)
    return default if value is None else value


def joined(v: Any, separator: str = ", ", default: str = "Not provided") -> str:
    if v is None:
        return default
    return separator.join(str(x) for x in v)


def java_string(v: Any) -> str:
    return "null" if v is None else str(v)


def validate(body: dict[str, Any], required: dict[str, str]) -> None:
    errors = {k: msg for k, msg in required.items() if not isinstance(body.get(k), str) or not body[k].strip()}
    if errors:
        raise ValidationError(errors)


class ValidationError(Exception):
    def __init__(self, errors: dict[str, str]):
        self.errors = errors


async def gemini(system: str, prompt: str, json_mode: bool = False,
                 file_content: bytes | None = None, mime_type: str | None = None) -> Any:
    key = configured("key", "")
    if not key:
        raise RuntimeError("Gemini API key is not configured")
    model = configured("model", "gemini-3-flash-preview")
    config: dict[str, Any] = {
        "temperature": float(configured("temperature", "0.7")),
        "maxOutputTokens": int(configured("max-output-tokens", "2048")),
    }
    if json_mode:
        config.update(temperature=0.3, responseMimeType="application/json")
    parts: list[dict[str, Any]] = [{"text": prompt}]
    if file_content and mime_type:
        parts.insert(0, {"inlineData": {"mimeType": mime_type, "data": base64.b64encode(file_content).decode("ascii")}})
    payload = {
        "system_instruction": {"parts": [{"text": system}]},
        "contents": [{"role": "user", "parts": parts}],
        "generationConfig": config,
    }
    url = f"https://generativelanguage.googleapis.com/v1beta/models/{model}:generateContent"
    async with httpx.AsyncClient(timeout=90) as client:
        response = await client.post(url, params={"key": key}, json=payload)
    if response.is_error:
        raise RuntimeError(f"Failed to get response from Gemini: {response.text[:500]}")
    try:
        result = response.json()
        content = "".join(p.get("text", "") for p in result["candidates"][0]["content"]["parts"])
    except (KeyError, IndexError, TypeError, ValueError) as exc:
        raise RuntimeError("Failed to get response from Gemini: empty response") from exc
    if not json_mode:
        return content
    cleaned = re.sub(r"^```(?:json)?\s*|\s*```$", "", content.strip(), flags=re.I)
    try:
        return json.loads(cleaned)
    except json.JSONDecodeError as exc:
        raise RuntimeError("AI returned invalid JSON. Please try again.") from exc


async def ai_text(system: str, prompt: str) -> dict[str, Any]:
    return text_response(await gemini(system, prompt))


@app.exception_handler(ValidationError)
async def validation_error(_: Request, exc: ValidationError):
    return JSONResponse(status_code=400, content={"message": "Validation failed", "success": False, "data": exc.errors})


@app.exception_handler(RequestValidationError)
async def request_error(_: Request, exc: RequestValidationError):
    errors = {"request": "Invalid request body"}
    for item in exc.errors():
        loc = item.get("loc", [])
        errors[str(loc[-1] if loc else "request")] = item.get("msg", "Invalid value")
    return JSONResponse(status_code=400, content={"message": "Validation failed", "success": False, "data": errors})


@app.exception_handler(Exception)
async def unexpected(_: Request, exc: Exception):
    # Java maps AI failures to 503; configuration/network failures are AI failures.
    if isinstance(exc, RuntimeError):
        return JSONResponse(status_code=503, content={"message": str(exc), "success": False})
    return JSONResponse(status_code=500, content={"message": "An unexpected error occurred", "success": False})


async def body(request: Request) -> dict[str, Any]:
    try:
        data = await request.json()
    except json.JSONDecodeError as exc:
        raise ValidationError({"request": "Malformed JSON request body"}) from exc
    if not isinstance(data, dict):
        raise ValidationError({"request": "Request body must be an object"})
    return data


@app.get("/api/ai")
async def home():
    return "hello from ai service"


@app.post("/api/ai/application/cover-letter")
async def cover_letter(request: Request):
    b = await body(request); validate(b, {"jobTitle": "Job title is required", "candidateName": "Candidate name is required"})
    skills = joined(b.get("candidateSkills"))
    experience = joined(b.get("candidateExperience"), "; ")
    prompt = java_prompt("""Write a compelling, personalized cover letter.

Position: %s
Job Description: %s
Target Company: %s

Candidate Profile:
- Name: %s
- Professional Summary: %s
- Key Skills: %s
- Relevant Experience: %s

Write a 3-paragraph cover letter:
Paragraph 1 (Opening): Express specific enthusiasm for this exact role and company. Mention 1 specific thing about the role that excites you.
Paragraph 2 (Body): Connect 2-3 of the candidate's strongest experiences/skills directly to the job requirements. Be specific with examples.
Paragraph 3 (Closing): Confident call to action. Express eagerness to discuss further.

Rules:
- Write as the candidate (first person)
- Be specific — avoid generic statements
- Maximum 300 words
- Professional but warm tone
- Do NOT use placeholders like [Company Name] — use the actual company name or say "your team"
- Do NOT include subject line or date""",
        b["jobTitle"], java_value(b, "jobDescription", "Not provided"), java_value(b, "targetCompanyName", "your organization"),
        b["candidateName"], java_value(b, "candidateSummary", "Experienced professional"), skills, experience)
    return envelope("Cover letter generated", await ai_text(APPLICATION_SYSTEM, prompt))


@app.post("/api/ai/application/screening-score")
async def screening_score(request: Request):
    b = await body(request)
    required_skills = joined(b.get("requiredSkills"), ", ", "Not specified")
    candidate_skills = joined(b.get("candidateSkills"), ", ", "Not specified")
    candidate_exp = joined(b.get("candidateExperience"), "; ", "Not provided")
    candidate_edu = ("; ".join(str(x) for x in b["candidateEducation"])
                     if b.get("candidateEducation") else "Not provided")
    prompt = java_prompt("""Score this job application based on how well the candidate matches the requirements.

Job Requirements:
- Title: %s
- Experience Level Required: %s
- Required Skills: %s
- Key Responsibilities: %s

Candidate Profile:
- Professional Summary: %s
- Skills: %s
- Work Experience: %s
- Education: %s

Respond with ONLY this JSON (no explanation, no markdown):
{
  "score": 85,
  "skillsMatchScore": 90,
  "experienceMatchScore": 80,
  "educationMatchScore": 75,
  "matchedSkills": ["skill1", "skill2"],
  "missingSkills": ["skill3"],
  "strengths": ["strength1", "strength2"],
  "concerns": ["concern1"],
  "summary": "2-3 sentence honest assessment of this candidate's fit"
}

Score scale: 0-100 where 100 is a perfect match.
skillsMatchScore: how well candidate skills match required skills (0-100).
experienceMatchScore: how well candidate experience matches required level (0-100).
educationMatchScore: how well candidate education fits the role — use the actual education provided, not a guess (0-100).
score: overall weighted match considering all factors. Be objective and fair.""",
        value(b, "jobTitle", "Not specified"), value(b, "experienceLevel", "Not specified"), required_skills,
        value(b, "responsibilities", "Not provided"), value(b, "candidateSummary", "Not provided"),
        candidate_skills, candidate_exp, candidate_edu)
    return envelope("Candidate screened", await gemini(APPLICATION_SYSTEM, prompt, True))


@app.post("/api/ai/application/skills-gap")
async def skills_gap(request: Request):
    b = await body(request); validate(b, {"jobTitle": "Job title is required"})
    candidate_skills = joined(b.get("candidateSkills"), ", ", "None provided")
    required_skills = joined(b.get("requiredSkills"), ", ", "None provided")
    prompt = java_prompt("""Analyze the skills gap between a candidate and a job requirement.

Job Title: %s
Candidate's Current Skills: %s
Skills Required for the Job: %s

{
  "matchedSkills": ["skills candidate has that are required"],
  "missingSkills": ["required skills candidate completely lacks"],
  "partialMatch": ["skills candidate has partially or related version of"],
  "prioritySkillsToLearn": ["top 3 skills to learn first, in order of importance"],
  "learningRecommendations": [
    { "skill": "skill name", "why": "why this skill is important for the role", "howToLearn": "specific learning resource or approach" }
  ],
  "overallReadiness": "Ready or Partially Ready or Needs Development",
  "summary": "2-sentence honest assessment"
}""", b["jobTitle"], candidate_skills, required_skills)
    return envelope("Skills gap analyzed", await gemini(APPLICATION_SYSTEM, prompt, True))


@app.post("/api/ai/application/summarize-notes")
async def summarize_notes(request: Request):
    notes = await request.json()
    all_notes = "\n---\n".join(str(x) for x in notes)
    prompt = java_prompt("""Summarize these recruiter notes about a job candidate into a concise TL;DR.

Recruiter Notes:
%s

Write a 3-5 sentence summary covering:
1. Overall impression of the candidate
2. Key strengths mentioned
3. Any concerns or points to verify
4. Recommended next step (if mentioned)

Keep it factual, based only on what's in the notes.""", all_notes)
    return envelope("Notes summarized", await ai_text(APPLICATION_SYSTEM, prompt))


@app.post("/api/ai/company/describe")
async def company_describe(request: Request):
    b = await body(request); validate(b, {"name": "Company name is required"})
    prompt = java_prompt("""Write a compelling and authentic company "About Us" description for a job portal profile.

Company Details:
- Name: %s
- Industry: %s
- Company Type: %s
- Company Size: %s
- Tagline: %s
- Additional Context: %s

Write 2-3 engaging paragraphs that:
1. Open with what the company does and its mission/vision
2. Highlight the culture, team environment, and values
3. Describe growth opportunities and what makes it a great place to work

Rules:
- Maximum 200 words
- Write in third person
- Don't start with "We are" or "Our company"
- Make it inspiring for job seekers""",
        b["name"], java_value(b, "industry", "Technology"), java_value(b, "companyType", "Startup"),
        java_value(b, "size", "50-200 employees"), java_value(b, "tagline", "Not provided"), java_value(b, "additionalContext", "None"))
    return envelope("Company description generated", await ai_text(COMPANY_SYSTEM, prompt))


@app.post("/api/ai/company/taglines")
async def company_taglines(request: Request):
    b = await body(request); validate(b, {"name": "Company name is required"})
    prompt = java_prompt("""Generate 3 short, memorable, and inspiring company taglines.

Company Details:
- Name: %s
- Industry: %s
- Description: %s

Rules for each tagline:
- Maximum 8 words
- Must be unique and memorable
- Should reflect the company's identity
- Avoid generic phrases like "We are the best" or "Your trusted partner"
- Should appeal to job seekers and clients alike

{ "taglines": ["tagline 1", "tagline 2", "tagline 3"] }""",
        b["name"], java_value(b, "industry", "Technology"), java_value(b, "description", "Not provided"))
    return envelope("Taglines generated", await gemini(COMPANY_SYSTEM, prompt, True))


def job_prompt(kind: str, b: dict[str, Any]) -> str:
    title = java_value(b, "title", java_value(b, "jobTitle", None))
    category = java_value(b, "category", "General")
    if kind == "describe":
        skills = joined(b.get("skills"), ", ", "Not specified")
        return java_prompt("""Write a comprehensive, engaging, and inclusive job description.

Job Details:
- Title: %s
- Required Skills: %s
- Experience Level: %s
- Job Type: %s
- Work Mode: %s
- Category: %s
- Additional Context: %s

Format the response in clean markdown with EXACTLY these sections:
## About the Role
[2-3 compelling sentences describing the role and its impact]

## Key Responsibilities
- [6 specific, action-oriented bullet points]

## Requirements
- [5-6 must-have qualifications and skills]

## Nice to Have
- [3-4 bonus qualifications]

## What We Offer
- [4-5 benefits and perks]

Do NOT include placeholder company names.""", title, skills,
            java_value(b, "experienceLevel", "Not specified"), java_value(b, "jobType", "Not specified"),
            java_value(b, "workMode", "Not specified"), java_value(b, "category", "Not specified"),
            java_value(b, "additionalContext", "None"))
    if kind == "requirements":
        return java_prompt("""Generate professional job requirements and responsibilities for this role.

Job Title: %s
Category: %s

Format in markdown:
## Responsibilities
- [5 specific bullet points]

## Requirements
- [5 specific bullet points]

Keep it concise and ATS-friendly.""", title, category)
    if kind == "responsibilities":
        return java_prompt("""Generate 6 specific, action-oriented job responsibilities for this role.

Job Title: %s
Category: %s

Return ONLY a plain bullet list (no headings, no markdown headers):
- [responsibility 1]
- [responsibility 2]
...

Keep each bullet concise (under 15 words), start with a strong action verb.""", title, category)
    if kind == "benefits":
        return java_prompt("""Generate 6 competitive, attractive job benefits for this role.

Job Title: %s
Category: %s
Job Type: %s

Return ONLY a plain bullet list (no headings, no markdown headers):
- [benefit 1]
- [benefit 2]
...

Include a mix of: compensation perks, health/wellness, growth, flexibility, and culture benefits.
Keep each bullet concise and specific.""", title, category, java_value(b, "jobType", "Full-time"))
    if kind == "skills":
        return java_prompt("""Recommend the most relevant skills for this job posting.

Job Title: %s
Description: %s

List 8-10 specific, relevant skills that candidates should have.
Return a comma-separated list of skill names only.
Example: Java, Spring Boot, PostgreSQL, Docker, REST APIs, Microservices""", title, java_value(b, "description", ""))
    return java_prompt("""Recommend 8-10 relevant tags/keywords for this job posting that improve discoverability.

Job Title: %s
Description: %s

Return ONLY a comma-separated list of short tag names (1-3 words each).
Example: React, Frontend, JavaScript, Remote, Startup, Full Stack, Web Development""", title, java_value(b, "description", ""))


@app.post("/api/ai/job/describe")
async def job_describe(request: Request):
    b = await body(request); validate(b, {"title": "Job title is required"})
    return envelope("Job description generated", await ai_text(JOB_SYSTEM, job_prompt("describe", b)))


@app.get("/api/ai/job/requirements")
async def job_requirements(title: str, category: str | None = None):
    return envelope("Requirements generated", await ai_text(JOB_SYSTEM, job_prompt("requirements", {"title": title, "category": category})))


@app.post("/api/ai/job/salary-suggestion")
async def salary(request: Request):
    b = await body(request); validate(b, {"title": "Job title is required"})
    skills = joined(b.get("skills"), ", ", "Not specified")
    prompt = java_prompt("""Provide a realistic and competitive salary range for this role.

Role Details:
- Job Title: %s
- Required Skills: %s
- Experience Level: %s
- Job Type: %s
- Location: %s

{
  "minSalary": 600000,
  "maxSalary": 1200000,
  "currency": "INR",
  "period": "YEARLY",
  "marketInsight": "Brief 1-2 sentence insight about this role's compensation trend in the current Indian market"
}

minSalary and maxSalary must be numbers (not strings). Use realistic current Indian market rates.""",
        b["title"], skills, java_value(b, "experienceLevel", "MID"), java_value(b, "jobType", "FULL_TIME"), java_value(b, "location", "India"))
    return envelope("Salary range suggested", await gemini(JOB_SYSTEM, prompt, True))


@app.get("/api/ai/job/skills-recommendation")
async def skills_recommendation(title: str, description: str | None = None):
    return envelope("Skills recommended", await ai_text(JOB_SYSTEM, job_prompt("skills", {"title": title, "description": description})))


@app.get("/api/ai/job/responsibilities")
async def responsibilities(title: str, category: str | None = None):
    return envelope("Responsibilities generated", await ai_text(JOB_SYSTEM, job_prompt("responsibilities", {"title": title, "category": category})))


@app.get("/api/ai/job/benefits")
async def benefits(title: str, category: str | None = None, jobType: str | None = None):
    return envelope("Benefits generated", await ai_text(JOB_SYSTEM, job_prompt("benefits", {"title": title, "category": category, "jobType": jobType})))


@app.get("/api/ai/job/tags-recommendation")
async def tags(title: str, description: str | None = None):
    return envelope("Tags recommended", await ai_text(JOB_SYSTEM, job_prompt("tags", {"title": title, "description": description})))


@app.post("/api/ai/resume/summary")
async def resume_summary(request: Request):
    b = await body(request)
    experiences = ("; ".join(f"{java_string(x.get('jobTitle'))} at {java_string(x.get('company'))}{(': ' + x.get('description')) if x.get('description') is not None and str(x.get('description')).strip() else ''}" for x in b.get("workExperiences"))
                   if b.get("workExperiences") is not None else "Not provided")
    skills = joined(b.get("skills"), ", ", "Not provided")
    education = ("; ".join(f"{java_string(x.get('degree'))}{(' in ' + java_string(x.get('fieldOfStudy'))) if x.get('fieldOfStudy') is not None else ''}{(' from ' + java_string(x.get('institutionName'))) if x.get('institutionName') is not None else ''}" for x in b.get("educations"))
                 if b.get("educations") is not None else "Not provided")
    prompt = java_prompt("""Write a compelling professional summary for a resume.

Candidate Profile:
- Target Job Title: %s
- Years of Experience: %d
- Work Experience: %s
- Key Skills: %s
- Education: %s

Write a 3-4 sentence professional summary that:
1. Opens with seniority level and area of expertise
2. Highlights 2-3 key achievements or strengths with impact
3. Mentions specific technical skills relevant to the target role
4. Ends with a value proposition or career goal

Rules:
- Write in first person (no "I" at the start)
- Be specific and results-oriented
- Keep it under 80 words
- Make it ATS-friendly""",
        java_value(b, "targetJobTitle", "Software Developer"), java_value(b, "yearsOfExperience", 0), experiences, skills, education)
    return envelope("Professional summary generated", await ai_text(RESUME_SYSTEM, prompt))


@app.post("/api/ai/resume/experience-bullets")
async def experience_bullets(request: Request):
    b = await body(request); validate(b, {"jobTitle": "Job title is required", "rawDescription": "Raw description is required"})
    prompt = java_prompt("""Transform this work experience into powerful, ATS-friendly resume bullet points.

Role: %s at %s
Raw Description: %s
Achievements/Hints: %s

Generate exactly 4-5 bullet points that:
1. Start with strong action verbs (Developed, Led, Implemented, Architected, Optimized, Reduced, Increased, Delivered, Built, Designed)
2. Include quantifiable metrics where possible (percentages, numbers, time saved)
3. Highlight business impact and technical achievements
4. Are concise (under 20 words each)
5. Are ATS-friendly with relevant keywords

{
  "bullets": ["bullet point 1", "bullet point 2", "bullet point 3", "bullet point 4", "bullet point 5"]
}""", b["jobTitle"], java_value(b, "company", "the company"), b["rawDescription"], java_value(b, "achievementsHint", "None"))
    return envelope("Bullet points generated", await gemini(RESUME_SYSTEM, prompt, True))


@app.post("/api/ai/resume/parse")
async def parse_resume(request: Request):
    b = await body(request); validate(b, {"resumeText": "Resume text is required"})
    prompt = java_prompt("""Parse this resume text and extract all structured information.

Resume Text:
%s

Extract all available information and return JSON in this exact structure:
{
  "personalInfo": { "fullName": "", "email": "", "phone": "", "location": "", "linkedIn": "", "github": "", "website": "" },
  "summary": "",
  "workExperiences": [
    { "companyName": "", "jobTitle": "", "startDate": "YYYY-MM", "endDate": "YYYY-MM or null if current", "isCurrent": false, "description": "" }
  ],
  "educations": [
    { "schoolName": "", "degree": "", "field": "", "startDate": "YYYY-MM", "endDate": "YYYY-MM" }
  ],
  "skills": ["skill1", "skill2"],
  "certifications": [
    { "name": "", "issuer": "", "issueDate": "YYYY-MM" }
  ],
  "languages": [
    { "language": "", "proficiency": "BASIC or INTERMEDIATE or ADVANCED or NATIVE" }
  ]
}

Rules:
- Use null for missing fields
- Dates in YYYY-MM format
- If current job, set endDate to null and isCurrent to true
- Extract all skills mentioned anywhere in the resume""", b["resumeText"])
    return envelope("Resume parsed successfully", await gemini(RESUME_SYSTEM, prompt, True))


@app.post("/api/ai/resume/improvements")
async def improvements(request: Request):
    b = await body(request); validate(b, {"resumeContent": "Resume content is required"})
    prompt = java_prompt("""Analyze this resume and provide specific, actionable improvement suggestions.

Target Job Title: %s

Resume Content:
%s

{
  "overallScore": 72,
  "improvements": [
    { "section": "Summary or Experience or Skills or Education or General", "issue": "What is wrong or missing", "suggestion": "Specific action to fix it", "priority": "High or Medium or Low" }
  ],
  "strengths": ["what is already good about this resume"],
  "summary": "2-sentence overall assessment"
}

Provide 4-6 specific improvements. Score should be 0-100.""", java_value(b, "targetJobTitle", "Not specified"), b["resumeContent"])
    return envelope("Resume improvements analyzed", await gemini(RESUME_SYSTEM, prompt, True))


@app.post("/api/ai/resume/career-feedback")
async def career_feedback(request: Request):
    b = await body(request); validate(b, {"resumeContent": "Resume content is required"})
    return envelope("Career feedback generated", await career_feedback_result(b["resumeContent"], java_value(b, "targetJobTitle", "Not specified")))


def career_feedback_prompt(resume_content: str, target_job_title: str) -> str:
    b = {"targetJobTitle": target_job_title, "resumeContent": resume_content}
    return java_prompt("""Analyze this resume and deliver an honest, actionable career feedback report.

Target Job Title (if provided): %s
Resume Content:
%s

{
  "profileStrength": 65,
  "shortlistingIssues": ["Reason 1 why recruiters are skipping this profile", "Reason 2", "Reason 3"],
  "improvements": [
    { "area": "Skills | Summary | Experience | Education | Projects | General", "issue": "What specifically is weak or missing", "action": "Concrete step the candidate should take to fix it", "priority": "HIGH | MEDIUM | LOW" }
  ],
  "targetJobs": [
    { "jobTitle": "Recommended Job Title", "reason": "Why this role suits the current profile", "skillMatch": "HIGH | MEDIUM | LOW" }
  ],
  "overallSummary": "2-3 sentences of honest, encouraging career advice"
}

Rules:
- profileStrength: integer 0-100 reflecting overall job market readiness
- shortlistingIssues: 3-5 candid reasons a recruiter would skip this resume
- improvements: 4-6 items ordered by priority descending
- targetJobs: 3-5 realistic job titles matching current skills and experience level
- Be specific — mention actual skills, tools, or sections by name""", java_value(b, "targetJobTitle", "Not specified"), b["resumeContent"])
    

async def career_feedback_result(resume_content: str, target_job_title: str) -> Any:
    prompt = career_feedback_prompt(resume_content, target_job_title)
    return await gemini(RESUME_SYSTEM, prompt, True)


def extract_resume_text(filename: str, content: bytes) -> str:
    extension = os.path.splitext(filename.lower())[1]
    # Some browsers/proxies omit or rewrite the filename extension; identify
    # the common binary formats from their file signatures as a fallback.
    is_pdf = extension == ".pdf" or content.startswith(b"%PDF")
    is_docx = extension == ".docx" or (content.startswith(b"PK") and extension not in {".zip", ".doc"})
    if is_pdf:
        from pypdf import PdfReader
        reader = PdfReader(io.BytesIO(content))
        return "\n".join(page.extract_text() or "" for page in reader.pages)
    if is_docx:
        from docx import Document
        document = Document(io.BytesIO(content))
        paragraphs = [paragraph.text for paragraph in document.paragraphs]
        paragraphs.extend(cell.text for table in document.tables for row in table.rows for cell in row.cells)
        return "\n".join(paragraphs)
    if extension == ".txt":
        return content.decode("utf-8", errors="replace")
    raise ValidationError({"file": "Only PDF, DOCX, and TXT resume files are supported"})


@app.post("/api/ai/resume/analyze-upload")
async def analyze_resume_upload(request: Request):
    form = await request.form()
    file = form.get("file") or form.get("resume") or form.get("upload")
    if not isinstance(file, UploadFile) and not hasattr(file, "read"):
        raise ValidationError({"file": "Resume file is required"})
    filename = getattr(file, "filename", None) or "resume"
    content = await file.read()
    if not content:
        raise ValidationError({"file": "Resume file is empty"})
    resume_content = extract_resume_text(filename, content).strip()
    if not resume_content:
        mime_type = "application/pdf" if filename.lower().endswith(".pdf") or content.startswith(b"%PDF") else file.content_type
        if mime_type in {"application/pdf", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"}:
            resume_content = await gemini(
                "You extract text from uploaded resumes. Return only the readable resume text, without commentary or markdown.",
                "Extract all readable text from the attached resume so it can be analyzed.",
                file_content=content,
                mime_type=mime_type,
            )
    if not resume_content or not resume_content.strip():
        raise ValidationError({"file": "Could not extract text from the resume file. Upload a text-based PDF or DOCX."})
    result = await career_feedback_result(resume_content, "Not specified")
    return envelope("Resume analysis completed", result)


@app.post("/api/ai/search/enhance")
async def enhance_search(request: Request):
    b = await body(request); validate(b, {"query": "Search query is required"})
    prompt = java_prompt("""Extract structured job search criteria from this natural language query.

User Query: "%s"

Analyze the query and extract ALL implied and explicit search criteria.

Valid jobTypes: FULL_TIME, PART_TIME, CONTRACT, INTERNSHIP, FREELANCE
Valid workModes: REMOTE, HYBRID, ON_SITE
Valid experienceLevels: ENTRY, MID, SENIOR, LEAD, EXECUTIVE

{
  "keywords": ["keyword1", "keyword2"],
  "locations": ["city1", "city2"],
  "jobTypes": ["FULL_TIME"],
  "workModes": ["REMOTE"],
  "experienceLevels": ["ENTRY"],
  "minSalary": null,
  "skills": ["skill1", "skill2"]
}

Rules:
- Only include fields that are mentioned or clearly implied
- Use null for minSalary if not mentioned
- Use empty arrays [] for fields not mentioned
- "freshers" or "entry level" → ENTRY experience level
- "senior" or "5+ years" → SENIOR experience level
- "wfh" or "work from home" → REMOTE work mode""", b["query"])
    return envelope("Search enhanced", await gemini(SEARCH_SYSTEM, prompt, True))


@app.post("/api/ai/search/job-match")
async def job_match(request: Request):
    b = await body(request)
    candidate_skills = joined(b.get("candidateSkills"), ", ", "Not provided")
    job_skills = joined(b.get("jobSkills"), ", ", "Not provided")
    preferred_work_modes = joined(b.get("preferredWorkModes"), ", ", "Any")
    preferred_job_types = joined(b.get("preferredJobTypes"), ", ", "Any")
    prompt = java_prompt("""Calculate how well this job matches the candidate's profile and preferences.

Candidate Preferences:
- Preferred Work Modes: %s
- Preferred Job Types: %s
- Minimum Salary: %s
- Experience Level: %s
- Skills: %s

Job Details:
- Title: %s
- Work Mode: %s
- Job Type: %s
- Salary Range: %s - %s %s
- Industry: %s
- Experience Required: %s
- Required Skills: %s

{
  "matchScore": 85,
  "matchedCriteria": ["Work mode matches preference", "Salary above minimum"],
  "unmatchedCriteria": ["Requires experience level above candidate's current level"],
  "recommendation": "Highly Recommended or Recommended or Partial Match or Not Recommended",
  "summary": "2-sentence explanation of the match quality"
}

Score: 0-100 based on how well the job meets ALL candidate preferences.""",
        preferred_work_modes, preferred_job_types,
        f"{b['minSalary']} INR/year" if b.get("minSalary") is not None else "Not specified",
        java_value(b, "candidateExperienceLevel", "Not specified"), candidate_skills,
        java_value(b, "jobTitle", "Not specified"), java_value(b, "workMode", "Not specified"),
        java_value(b, "jobType", "Not specified"), java_value(b, "salaryMin", "Not disclosed"),
        java_value(b, "salaryMax", "Not disclosed"), java_value(b, "currency", "INR"),
        java_value(b, "industry", "Not specified"), java_value(b, "jobExperienceLevel", "Not specified"), job_skills)
    return envelope("Job match calculated", await gemini(SEARCH_SYSTEM, prompt, True))


@app.post("/api/ai/search/alert-suggestion")
async def alert_suggestion(request: Request):
    b = await body(request)
    skills = joined(b.get("skills"), ", ", "Not provided")
    job_titles = joined(b.get("previousJobTitles"), ", ", "Not provided")
    educations = joined(b.get("educations"), "; ", "Not provided")
    prompt = java_prompt("""Based on this candidate's profile, suggest optimal job alert criteria to find the best matching jobs.

Candidate Profile:
- Skills: %s
- Experience Level: %s
- Previous Job Titles: %s
- Education: %s

Valid jobTypes: FULL_TIME, PART_TIME, CONTRACT, INTERNSHIP, FREELANCE
Valid workModes: REMOTE, HYBRID, ON_SITE
Valid experienceLevels: ENTRY, MID, SENIOR, LEAD, EXECUTIVE

{
  "suggestedKeywords": ["keyword1", "keyword2"],
  "suggestedLocations": ["city1", "city2"],
  "suggestedJobTypes": ["FULL_TIME"],
  "suggestedWorkModes": ["REMOTE", "HYBRID"],
  "suggestedExperienceLevels": ["MID"],
  "suggestedIndustries": ["Technology", "Finance"],
  "reasoning": "Brief explanation of why these criteria were chosen"
}""", skills, b.get("experienceLevel"), job_titles, educations)
    return envelope("Alert criteria suggested", await gemini(SEARCH_SYSTEM, prompt, True))


def register_with_eureka() -> None:
    """Register and renew the service lease using the existing Eureka server."""
    base = (os.getenv("EUREKA_SERVER_URL") or os.getenv("EUREKA_DEFAULT_ZONE") or
            "http://localhost:8761/eureka")
    port = int(os.getenv("PORT", os.getenv("SERVER_PORT", "8089")))
    host = os.getenv("EUREKA_INSTANCE_HOSTNAME", "localhost")
    app_name = "JOB-PORTAL-AI-SERVICE"
    instance_id = f"{host}:job-portal-ai-service:{port}"
    payload = {"instance": {"instanceId": instance_id, "hostName": host, "app": app_name, "ipAddr": host,
                             "port": {"$": port, "@enabled": "true"}, "status": "UP",
                             "homePageUrl": f"http://{host}:{port}/", "statusPageUrl": f"http://{host}:{port}/api/ai",
                             "healthCheckUrl": f"http://{host}:{port}/api/ai", "vipAddress": "job-portal-ai-service",
                             "dataCenterInfo": {"@class": "com.netflix.appinfo.InstanceInfo$DefaultDataCenterInfo", "name": "MyOwn"}}}
    register_url = base.rstrip("/") + f"/apps/{app_name}"
    heartbeat_url = register_url + f"/{instance_id}"
    while True:
        try:
            response = httpx.post(register_url, json=payload,
                                  headers={"Content-Type": "application/json"}, timeout=5)
            if response.status_code in (200, 201, 204):
                httpx.put(heartbeat_url, timeout=5)
                time.sleep(20)
            else:
                time.sleep(10)
        except Exception:
            time.sleep(10)


Thread(target=register_with_eureka, daemon=True).start()


if __name__ == "__main__":
    import uvicorn

    uvicorn.run("app:app", host="0.0.0.0", port=int(os.getenv("PORT", os.getenv("SERVER_PORT", "8089"))))
