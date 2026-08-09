import { useState, useEffect } from "react"
import { useDispatch, useSelector } from "react-redux"
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card"
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { Textarea } from "@/components/ui/textarea"
import { Badge } from "@/components/ui/badge"
import { Separator } from "@/components/ui/separator"
import { Skeleton } from "@/components/ui/skeleton"
import { Sparkles, FileText, PenTool, Target, TrendingUp, Upload, Copy, BrainCircuit, ChevronRight } from "lucide-react"
import { toast } from "sonner"
import { fetchMyResumes, fetchResumeById } from "@/store/resume/resumeThunk"
import { analyzeResumeUpload } from "@/store/ai/aiThunk"
import { analyzeSkillsGap } from "@/store/ai/aiThunk"
import { calculateJobMatch } from "@/store/ai/aiThunk"
import CareerFeedbackDialog from "@/components/user/resumes/CareerFeedbackDialog"

const aiTools = [
  {
    id: "resume-analyzer",
    name: "Resume Analyzer",
    description: "Get AI-powered feedback on your resume to improve your chances",
    icon: FileText,
    color: "blue",
  },
  {
    id: "cover-letter",
    name: "Cover Letter Builder",
    description: "Generate professional cover letters tailored to specific jobs",
    icon: PenTool,
    color: "purple",
  },
  {
    id: "skill-gap",
    name: "Skill Gap Analyzer",
    description: "Identify skills you need to develop for your dream job",
    icon: Target,
    color: "green",
  },
  {
    id: "job-match",
    name: "Job Match Recommendations",
    description: "Get personalized job recommendations based on your profile",
    icon: TrendingUp,
    color: "orange",
  },
  {
    id: "career-feedback",
    name: "AI Career Feedback Engine",
    description: "Find out why you're not getting shortlisted, what to improve, and which jobs to target",
    icon: BrainCircuit,
    color: "violet",
  },
]

export default function AITools() {
  const [selectedTool, setSelectedTool] = useState(null)

  return (
    <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      {/* Header */}
      <div className="mb-8">
        <div className="flex items-center gap-2 mb-2">
          <div className="h-10 w-10 rounded-lg bg-brand flex items-center justify-center">
            <Sparkles className="h-6 w-6 text-white" />
          </div>
          <h1 className="text-3xl font-bold text-slate-900">AI-Powered Tools</h1>
        </div>
        <p className="text-slate-600">
          Leverage AI to enhance your job search and improve your application success rate
        </p>
      </div>

      {!selectedTool ? (
        /* Tool Selection Grid */
        <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
          {aiTools.map((tool) => (
            <ToolCard key={tool.id} tool={tool} onSelect={() => setSelectedTool(tool.id)} />
          ))}
        </div>
      ) : (
        /* Selected Tool Interface */
        <div>
          <Button variant="ghost" onClick={() => setSelectedTool(null)} className="mb-6">
            ← Back to Tools
          </Button>
          {selectedTool === "resume-analyzer" && <ResumeAnalyzer />}
          {selectedTool === "cover-letter" && <CoverLetterBuilder />}
          {selectedTool === "skill-gap" && <SkillGapAnalyzer />}
          {selectedTool === "job-match" && <JobMatchRecommendations />}
          {selectedTool === "career-feedback" && <CareerFeedbackTool />}
        </div>
      )}
    </div>
  )
}

function ToolCard({ tool, onSelect }) {
  const Icon = tool.icon
  const colorClasses = {
    blue:   "bg-blue-100 text-brand",
    purple: "bg-purple-100 text-purple-600",
    green:  "bg-green-100 text-green-600",
    orange: "bg-orange-100 text-orange-600",
    violet: "bg-violet-100 text-violet-600",
  }

  return (
    <Card className="hover:shadow-md transition-shadow cursor-pointer" onClick={onSelect}>
      <CardContent className="p-6">
        <div className="flex items-start gap-4">
          <div className={`h-12 w-12 rounded-lg flex items-center justify-center ${colorClasses[tool.color]}`}>
            <Icon className="h-6 w-6" />
          </div>
          <div className="flex-1">
            <h3 className="font-semibold text-lg text-slate-900 mb-1">{tool.name}</h3>
            <p className="text-sm text-slate-600 mb-4">{tool.description}</p>
            <Button size="sm">
              Try Now
              <Sparkles className="h-4 w-4 ml-2" />
            </Button>
          </div>
        </div>
      </CardContent>
    </Card>
  )
}

function ResumeAnalyzer() {
  const [isAnalyzing, setIsAnalyzing] = useState(false)
  const [analysis, setAnalysis] = useState(null)
  const [selectedFile, setSelectedFile] = useState(null)
  const dispatch = useDispatch()

  const handleAnalyze = async () => {
    if (!selectedFile) {
      toast.error("Please choose a PDF, DOCX, or TXT resume first")
      return
    }
    setIsAnalyzing(true)
    try {
      const result = await dispatch(analyzeResumeUpload(selectedFile)).unwrap()
      setAnalysis({
        score: result.profileStrength ?? 0,
        strengths: result.overallSummary ? [result.overallSummary] : [],
        improvements: (result.improvements ?? []).map(item => `${item.area}: ${item.action}`),
        keywords: (result.targetJobs ?? []).map(item => item.jobTitle).filter(Boolean),
      })
      toast.success("Resume analysis complete!")
    } catch (err) {
      toast.error(err || "Failed to analyze resume")
    } finally {
      setIsAnalyzing(false)
    }
  }

  return (
    <div className="space-y-6">
      <Card className="border-blue-200 bg-gradient-to-br from-blue-50 to-indigo-50">
        <CardContent className="p-6">
          <div className="flex items-start gap-4">
            <div className="h-12 w-12 rounded-lg bg-brand flex items-center justify-center">
              <FileText className="h-6 w-6 text-white" />
            </div>
            <div>
              <h2 className="text-xl font-bold text-slate-900 mb-2">Resume Analyzer</h2>
              <p className="text-slate-700 mb-4">
                Upload your resume and get instant AI-powered feedback to improve your job prospects
              </p>
            </div>
          </div>
        </CardContent>
      </Card>

      <Card>
        <CardContent className="p-6">
          <div className="border-2 border-dashed border-slate-300 rounded-lg p-8 text-center">
            <Upload className="h-12 w-12 text-slate-400 mx-auto mb-4" />
            <h3 className="font-semibold text-slate-900 mb-2">Upload Your Resume</h3>
            <p className="text-sm text-slate-600 mb-4">PDF, DOCX, or TXT (Max 5MB)</p>
            <input
              type="file"
              accept=".pdf,.docx,.txt,application/pdf,application/vnd.openxmlformats-officedocument.wordprocessingml.document,text/plain"
              className="block w-full max-w-sm mx-auto mb-4 text-sm"
              onChange={(event) => setSelectedFile(event.target.files?.[0] ?? null)}
            />
            {selectedFile && <p className="text-xs text-slate-500 mb-4">Selected: {selectedFile.name}</p>}
            <Button onClick={handleAnalyze} disabled={isAnalyzing || !selectedFile}>
              {isAnalyzing ? "Analyzing..." : "Analyze Resume"}
            </Button>
          </div>
        </CardContent>
      </Card>

      {analysis && (
        <Card>
          <CardHeader>
            <CardTitle>Analysis Results</CardTitle>
          </CardHeader>
          <CardContent className="space-y-6">
            <div className="text-center p-6 bg-blue-50 rounded-lg">
              <div className="text-4xl font-bold text-brand mb-2">{analysis.score}/100</div>
              <p className="text-slate-700">Resume Score</p>
            </div>

            <div>
              <h4 className="font-semibold text-green-900 mb-3">Strengths</h4>
              <ul className="space-y-2">
                {analysis.strengths.map((item, index) => (
                  <li key={index} className="flex items-start gap-2 text-sm text-slate-700">
                    <span className="text-green-600">✓</span>
                    {item}
                  </li>
                ))}
              </ul>
            </div>

            <Separator />

            <div>
              <h4 className="font-semibold text-orange-900 mb-3">Suggested Improvements</h4>
              <ul className="space-y-2">
                {analysis.improvements.map((item, index) => (
                  <li key={index} className="flex items-start gap-2 text-sm text-slate-700">
                    <span className="text-orange-600">→</span>
                    {item}
                  </li>
                ))}
              </ul>
            </div>

            <Separator />

            <div>
              <h4 className="font-semibold text-slate-900 mb-3">Key Skills Detected</h4>
              <div className="flex flex-wrap gap-2">
                {analysis.keywords.map((keyword) => (
                  <Badge key={keyword} variant="secondary">
                    {keyword}
                  </Badge>
                ))}
              </div>
            </div>
          </CardContent>
        </Card>
      )}
    </div>
  )
}

function CoverLetterBuilder() {
  const [jobTitle, setJobTitle] = useState("")
  const [company, setCompany] = useState("")
  const [generatedLetter, setGeneratedLetter] = useState("")
  const [isGenerating, setIsGenerating] = useState(false)

  const handleGenerate = () => {
    if (!jobTitle || !company) {
      toast.error("Please fill in all fields")
      return
    }

    setIsGenerating(true)
    setTimeout(() => {
      setGeneratedLetter(`Dear Hiring Manager,

I am writing to express my strong interest in the ${jobTitle} position at ${company}. With my extensive experience and passion for technology, I am confident I would be a valuable addition to your team.

Throughout my career, I have demonstrated expertise in modern web development and a commitment to delivering high-quality solutions. I am particularly excited about this opportunity at ${company} because of your innovative approach and commitment to excellence.

I would welcome the opportunity to discuss how my skills and experience align with your needs.

Thank you for considering my application.

Best regards,
[Your Name]`)
      setIsGenerating(false)
      toast.success("Cover letter generated!")
    }, 2000)
  }

  return (
    <div className="space-y-6">
      <Card className="border-purple-200 bg-gradient-to-br from-purple-50 to-pink-50">
        <CardContent className="p-6">
          <div className="flex items-start gap-4">
            <div className="h-12 w-12 rounded-lg bg-purple-600 flex items-center justify-center">
              <PenTool className="h-6 w-6 text-white" />
            </div>
            <div>
              <h2 className="text-xl font-bold text-slate-900 mb-2">Cover Letter Builder</h2>
              <p className="text-slate-700 mb-4">
                Generate a professional cover letter tailored to the specific job you're applying for
              </p>
            </div>
          </div>
        </CardContent>
      </Card>

      <Card>
        <CardHeader>
          <CardTitle>Job Details</CardTitle>
        </CardHeader>
        <CardContent className="space-y-4">
          <div className="space-y-2">
            <label className="text-sm font-medium">Job Title</label>
            <input
              type="text"
              className="w-full px-3 py-2 border rounded-lg"
              placeholder="e.g. Senior React Developer"
              value={jobTitle}
              onChange={(e) => setJobTitle(e.target.value)}
            />
          </div>
          <div className="space-y-2">
            <label className="text-sm font-medium">Company Name</label>
            <input
              type="text"
              className="w-full px-3 py-2 border rounded-lg"
              placeholder="e.g. TechCorp Inc."
              value={company}
              onChange={(e) => setCompany(e.target.value)}
            />
          </div>
          <Button onClick={handleGenerate} disabled={isGenerating} className="w-full">
            <Sparkles className="h-4 w-4 mr-2" />
            {isGenerating ? "Generating..." : "Generate Cover Letter"}
          </Button>
        </CardContent>
      </Card>

      {generatedLetter && (
        <Card>
          <CardHeader>
            <div className="flex items-center justify-between">
              <CardTitle>Your Cover Letter</CardTitle>
              <Button
                variant="outline"
                size="sm"
                onClick={() => {
                  navigator.clipboard.writeText(generatedLetter)
                  toast.success("Copied to clipboard!")
                }}
              >
                <Copy className="h-4 w-4 mr-2" />
                Copy
              </Button>
            </div>
          </CardHeader>
          <CardContent>
            <Textarea
              value={generatedLetter}
              onChange={(e) => setGeneratedLetter(e.target.value)}
              className="min-h-[400px] font-mono text-sm"
            />
          </CardContent>
        </Card>
      )}
    </div>
  )
}

function SkillGapAnalyzer() {
  const dispatch = useDispatch()
  const [jobTitle, setJobTitle] = useState("")
  const [candidateSkills, setCandidateSkills] = useState("")
  const [requiredSkills, setRequiredSkills] = useState("")
  const [result, setResult] = useState(null)
  const [isAnalyzing, setIsAnalyzing] = useState(false)

  const handleAnalyze = async () => {
    if (!jobTitle.trim() || !requiredSkills.trim()) {
      toast.error("Enter a job title and required skills")
      return
    }
    setIsAnalyzing(true)
    try {
      const response = await dispatch(analyzeSkillsGap({
        jobTitle: jobTitle.trim(),
        candidateSkills: candidateSkills.split(",").map(s => s.trim()).filter(Boolean),
        requiredSkills: requiredSkills.split(",").map(s => s.trim()).filter(Boolean),
      })).unwrap()
      setResult(response)
      toast.success("Skill gap analysis complete!")
    } catch (err) {
      toast.error(err || "Failed to analyze skill gap")
    } finally {
      setIsAnalyzing(false)
    }
  }

  return (
    <div className="space-y-6">
      <Card className="border-green-200 bg-gradient-to-br from-green-50 to-emerald-50">
        <CardContent className="p-6 flex items-start gap-4">
          <Target className="h-12 w-12 text-green-600 shrink-0" />
          <div>
            <h3 className="text-xl font-bold text-slate-900 mb-2">Skill Gap Analyzer</h3>
            <p className="text-slate-600">Compare your current skills with the skills required for a target role.</p>
          </div>
        </CardContent>
      </Card>

      <Card>
        <CardContent className="p-6 space-y-4">
          <div>
            <label className="text-sm font-medium text-slate-700">Target job title</label>
            <Input value={jobTitle} onChange={e => setJobTitle(e.target.value)} placeholder="e.g. Senior React Developer" />
          </div>
          <div>
            <label className="text-sm font-medium text-slate-700">Your current skills</label>
            <Textarea value={candidateSkills} onChange={e => setCandidateSkills(e.target.value)} placeholder="React, JavaScript, HTML, CSS" />
            <p className="text-xs text-slate-500 mt-1">Separate skills with commas.</p>
          </div>
          <div>
            <label className="text-sm font-medium text-slate-700">Required job skills</label>
            <Textarea value={requiredSkills} onChange={e => setRequiredSkills(e.target.value)} placeholder="React, TypeScript, Next.js, testing" />
          </div>
          <Button onClick={handleAnalyze} disabled={isAnalyzing} className="bg-green-600 hover:bg-green-700">
            {isAnalyzing ? "Analyzing..." : "Analyze Skill Gap"}
          </Button>
        </CardContent>
      </Card>

      {result && (
        <Card>
          <CardHeader><CardTitle>{result.overallReadiness || "Analysis Results"}</CardTitle></CardHeader>
          <CardContent className="space-y-5">
            {result.summary && <p className="text-slate-700">{result.summary}</p>}
            <div className="grid md:grid-cols-3 gap-4">
              {["matchedSkills", "missingSkills", "partialMatch"].map((key) => (
                <div key={key} className="rounded-lg bg-slate-50 p-4">
                  <h4 className="font-semibold text-slate-900 mb-2">{key === "matchedSkills" ? "Matched" : key === "missingSkills" ? "Missing" : "Partial match"}</h4>
                  <div className="flex flex-wrap gap-2">
                    {(result[key] || []).map((skill, index) => <Badge key={`${key}-${index}`} variant="outline">{skill}</Badge>)}
                  </div>
                </div>
              ))}
            </div>
            {result.prioritySkillsToLearn?.length > 0 && (
              <div><h4 className="font-semibold text-slate-900 mb-2">Priority skills to learn</h4><p className="text-slate-700">{result.prioritySkillsToLearn.join(", ")}</p></div>
            )}
            {result.learningRecommendations?.length > 0 && (
              <div><h4 className="font-semibold text-slate-900 mb-2">Learning recommendations</h4><ul className="list-disc pl-5 space-y-1 text-slate-700">{result.learningRecommendations.map((item, index) => <li key={index}><strong>{item.skill}:</strong> {item.howToLearn || item.why}</li>)}</ul></div>
            )}
          </CardContent>
        </Card>
      )}
    </div>
  )
}

function JobMatchRecommendations() {
  const dispatch = useDispatch()
  const [form, setForm] = useState({
    jobTitle: "", candidateSkills: "", preferredWorkModes: "", preferredJobTypes: "",
    candidateExperienceLevel: "", minSalary: "", workMode: "", jobType: "",
    salaryMin: "", salaryMax: "", industry: "", jobExperienceLevel: "", jobSkills: "",
  })
  const [result, setResult] = useState(null)
  const [isMatching, setIsMatching] = useState(false)
  const update = (key) => (event) => setForm(current => ({ ...current, [key]: event.target.value }))
  const list = (value) => value.split(",").map(item => item.trim()).filter(Boolean)

  const handleMatch = async () => {
    if (!form.jobTitle.trim()) {
      toast.error("Enter a job title to evaluate")
      return
    }
    setIsMatching(true)
    try {
      const response = await dispatch(calculateJobMatch({
        preferredWorkModes: list(form.preferredWorkModes),
        preferredJobTypes: list(form.preferredJobTypes),
        minSalary: form.minSalary ? Number(form.minSalary) : null,
        candidateExperienceLevel: form.candidateExperienceLevel || null,
        candidateSkills: list(form.candidateSkills),
        jobTitle: form.jobTitle.trim(),
        workMode: form.workMode || null,
        jobType: form.jobType || null,
        salaryMin: form.salaryMin ? Number(form.salaryMin) : null,
        salaryMax: form.salaryMax ? Number(form.salaryMax) : null,
        currency: "INR",
        industry: form.industry || null,
        jobExperienceLevel: form.jobExperienceLevel || null,
        jobSkills: list(form.jobSkills),
      })).unwrap()
      setResult(response)
      toast.success("Job match calculated!")
    } catch (err) {
      toast.error(err || "Failed to calculate job match")
    } finally {
      setIsMatching(false)
    }
  }

  return (
    <div className="space-y-6">
      <Card className="border-orange-200 bg-gradient-to-br from-orange-50 to-amber-50">
        <CardContent className="p-6 flex items-start gap-4">
          <TrendingUp className="h-12 w-12 text-orange-600 shrink-0" />
          <div><h3 className="text-xl font-bold text-slate-900 mb-2">Job Match Recommendations</h3><p className="text-slate-600">Compare a job with your skills, preferences, and salary expectations.</p></div>
        </CardContent>
      </Card>
      <Card><CardContent className="p-6 space-y-4">
        <div className="grid md:grid-cols-2 gap-4">
          <div><label className="text-sm font-medium">Job title</label><Input value={form.jobTitle} onChange={update("jobTitle")} placeholder="e.g. Backend Developer" /></div>
          <div><label className="text-sm font-medium">Industry</label><Input value={form.industry} onChange={update("industry")} placeholder="Technology" /></div>
          <div><label className="text-sm font-medium">Your skills</label><Input value={form.candidateSkills} onChange={update("candidateSkills")} placeholder="Python, FastAPI, SQL" /></div>
          <div><label className="text-sm font-medium">Job skills</label><Input value={form.jobSkills} onChange={update("jobSkills")} placeholder="Python, Django, PostgreSQL" /></div>
          <div><label className="text-sm font-medium">Preferred work modes</label><Input value={form.preferredWorkModes} onChange={update("preferredWorkModes")} placeholder="REMOTE, HYBRID" /></div>
          <div><label className="text-sm font-medium">Preferred job types</label><Input value={form.preferredJobTypes} onChange={update("preferredJobTypes")} placeholder="FULL_TIME" /></div>
          <div><label className="text-sm font-medium">Candidate experience level</label><Input value={form.candidateExperienceLevel} onChange={update("candidateExperienceLevel")} placeholder="MID" /></div>
          <div><label className="text-sm font-medium">Job experience level</label><Input value={form.jobExperienceLevel} onChange={update("jobExperienceLevel")} placeholder="MID" /></div>
          <div><label className="text-sm font-medium">Minimum salary (INR/year)</label><Input type="number" value={form.minSalary} onChange={update("minSalary")} /></div>
          <div><label className="text-sm font-medium">Job salary range (INR/year)</label><div className="flex gap-2"><Input type="number" placeholder="Min" value={form.salaryMin} onChange={update("salaryMin")} /><Input type="number" placeholder="Max" value={form.salaryMax} onChange={update("salaryMax")} /></div></div>
        </div>
        <Button onClick={handleMatch} disabled={isMatching} className="bg-orange-600 hover:bg-orange-700">{isMatching ? "Calculating..." : "Calculate Job Match"}</Button>
        <p className="text-xs text-slate-500">Separate list values with commas.</p>
      </CardContent></Card>
      {result && <Card><CardHeader><CardTitle>{result.recommendation || "Match Results"}</CardTitle></CardHeader><CardContent className="space-y-4"><div className="text-4xl font-bold text-orange-600">{result.matchScore ?? 0}/100</div>{result.summary && <p className="text-slate-700">{result.summary}</p>}<div className="grid md:grid-cols-2 gap-4"><div><h4 className="font-semibold mb-2">Matched criteria</h4><ul className="list-disc pl-5 text-slate-700">{(result.matchedCriteria || []).map((item, index) => <li key={index}>{item}</li>)}</ul></div><div><h4 className="font-semibold mb-2">Unmatched criteria</h4><ul className="list-disc pl-5 text-slate-700">{(result.unmatchedCriteria || []).map((item, index) => <li key={index}>{item}</li>)}</ul></div></div></CardContent></Card>}
    </div>
  )
}

function CareerFeedbackTool() {
  const dispatch = useDispatch()
  const { resumes, isLoading } = useSelector((s) => s.resume)
  const [feedbackResume, setFeedbackResume] = useState(null)
  const [showFeedback, setShowFeedback] = useState(false)

  useEffect(() => {
    dispatch(fetchMyResumes())
  }, [dispatch])

  const handleSelect = (resumeSummary) => {
    dispatch(fetchResumeById(resumeSummary.id)).then((action) => {
      if (action.meta.requestStatus === "fulfilled") {
        setFeedbackResume(action.payload.data ?? action.payload)
        setShowFeedback(true)
      }
    })
  }

  return (
    <>
      {/* Header card */}
      <Card className="border-violet-200 bg-gradient-to-br from-violet-50 to-purple-50 mb-6">
        <CardContent className="p-6">
          <div className="flex items-start gap-4">
            <div className="h-12 w-12 rounded-lg bg-violet-600 flex items-center justify-center shrink-0">
              <BrainCircuit className="h-6 w-6 text-white" />
            </div>
            <div>
              <h2 className="text-xl font-bold text-slate-900 mb-1">AI Career Feedback Engine</h2>
              <p className="text-slate-600 text-sm">
                Select one of your resumes below. The AI will tell you why you may not be getting
                shortlisted, what to improve, and which jobs match your current profile.
              </p>
            </div>
          </div>
        </CardContent>
      </Card>

      {/* Resume list */}
      <Card>
        <CardHeader>
          <CardTitle className="text-base">Choose a Resume to Analyze</CardTitle>
        </CardHeader>
        <CardContent className="space-y-3">
          {isLoading && (
            <>
              {[1, 2, 3].map((i) => (
                <div key={i} className="flex items-center gap-3 p-4 rounded-lg border border-slate-200">
                  <Skeleton className="h-10 w-10 rounded-lg" />
                  <div className="flex-1 space-y-1.5">
                    <Skeleton className="h-4 w-1/3" />
                    <Skeleton className="h-3 w-1/4" />
                  </div>
                  <Skeleton className="h-8 w-24" />
                </div>
              ))}
            </>
          )}

          {!isLoading && resumes.length === 0 && (
            <div className="text-center py-10 text-slate-500">
              <FileText className="h-10 w-10 mx-auto mb-3 text-slate-300" />
              <p className="text-sm font-medium">No resumes found</p>
              <p className="text-xs mt-1">Create a resume first from the Resumes page.</p>
            </div>
          )}

          {!isLoading && resumes.map((resume) => (
            <div
              key={resume.id}
              className="flex items-center gap-4 p-4 rounded-lg border border-slate-200 hover:border-violet-300 hover:bg-violet-50/50 transition-colors"
            >
              <div className="h-10 w-10 rounded-lg bg-violet-100 flex items-center justify-center shrink-0">
                <FileText className="h-5 w-5 text-violet-600" />
              </div>
              <div className="flex-1 min-w-0">
                <p className="text-sm font-semibold text-slate-900 truncate">{resume.title}</p>
                <p className="text-xs text-slate-500">{resume.completionScore ?? 0}% complete · {resume.template}</p>
              </div>
              <Button
                size="sm"
                className="bg-violet-600 hover:bg-violet-700 shrink-0"
                onClick={() => handleSelect(resume)}
              >
                <Sparkles className="h-3.5 w-3.5 mr-1.5" />
                Analyze
                <ChevronRight className="h-3.5 w-3.5 ml-1" />
              </Button>
            </div>
          ))}
        </CardContent>
      </Card>

      <CareerFeedbackDialog
        open={showFeedback}
        onClose={() => setShowFeedback(false)}
        resume={feedbackResume}
      />
    </>
  )
}
