package com.project.referral.service;

import com.project.referral.client.GeminiClient;
import com.project.referral.dto.request.ScreeningScoreRequest;
import com.project.referral.dto.response.CandidateEvaluation;
import com.project.referral.dto.response.GeminiExplanation;
import com.project.referral.dto.response.JobRequirements;
import com.project.referral.dto.response.ResumeChunk;
import com.project.referral.dto.response.ScreeningScoreResponse;
import com.project.referral.dto.response.ResumeParseResponse;
import com.project.referral.exception.GeminiParsingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@Service
@RequiredArgsConstructor
public class RagService {
    private final JobRequirementExtractor requirementExtractor;
    private final ResumeRetriever resumeRetriever;
    private final DeterministicScoringService scoringService;
    private final PromptBuilder promptBuilder;
    private final GeminiClient geminiClient;
    public ScreeningScoreResponse screenCandidate(ScreeningScoreRequest req) {        // 1. get JobRequirements (prefer structured input if provided)        JobRequirements requirements;        if (req.getRequiredSkills() != null && !req.getRequiredSkills().isEmpty()) {            requirements = new JobRequirements();            requirements.setJobTitle(req.getJobTitle());            requirements.setRequiredSkills(req.getRequiredSkills());            requirements.setResponsibilities(List.of(req.getResponsibilities()));        } else {            try {                requirements = requirementExtractor.extract(req.getJobTitle(), req.getResponsibilities());            } catch (Exception e) {                throw new GeminiParsingException("Failed to extract job requirements: " + e.getMessage(), e);            }        }        // 2. retrieve evidence from vector DB (if available)        List<ResumeChunk> evidence = resumeRetriever.search(requirements, 10);        // 3. build a minimal parsed resume from request fields for scoring (fallback if full parse not available)        ResumeParseResponse parsed = new ResumeParseResponse();        parsed.setSkills(req.getCandidateSkills());        // map candidateExperience to work experiences loosely not fully typed        // leave educations empty for now unless candidateEducation provided as strings        parsed.setSummary(req.getCandidateSummary());        // 4. deterministic scoring        CandidateEvaluation eval = scoringService.evaluate(requirements, parsed, evidence);        // 5. build final prompt and call Gemini for explanation (only)        String prompt = promptBuilder.buildFinalPrompt(requirements, evidence, eval);        long start = System.currentTimeMillis();        GeminiExplanation explanation;        try {            explanation = geminiClient.generateJson(null, prompt, GeminiExplanation.class);        } catch (Exception e) {            throw new GeminiParsingException("Failed to get explanation from Gemini: " + e.getMessage(), e);        }        long dur = System.currentTimeMillis() - start;        log.info("Gemini explanation time: {} ms", dur);        // 6. map to ScreeningScoreResponse (keep numeric scores from deterministic eval)        ScreeningScoreResponse resp = new ScreeningScoreResponse();        resp.setScore((int)Math.round(eval.getOverallScore()));        resp.setSkillsMatchScore((int)Math.round(eval.getTechnicalScore()));        resp.setExperienceMatchScore((int)Math.round(eval.getExperienceScore()));        resp.setEducationMatchScore((int)Math.round(eval.getEducationScore()));        resp.setMatchedSkills(eval.getMatchedSkills());        resp.setMissingSkills(eval.getMissingSkills());        // combine strengths from deterministic eval and Gemini explanation        List<String> strengths = eval.getStrengths() != null ? new java.util.ArrayList<>(eval.getStrengths()) : new java.util.ArrayList<>();        if (explanation.getStrengths() != null) {
            for (String s : explanation.getStrengths()) if (!strengths.contains(s)) strengths.add(s);
        }        resp.setStrengths(strengths);        // map weaknesses/concerns        List<String> concerns = eval.getWeaknesses() != null ? new java.util.ArrayList<>(eval.getWeaknesses()) : new java.util.ArrayList<>();        if (explanation.getWeaknesses() != null) {
            for (String s : explanation.getWeaknesses()) if (!concerns.contains(s)) concerns.add(s);
        }        resp.setConcerns(concerns);        resp.setSummary(explanation.getSummary() != null ? explanation.getSummary() : eval.getSummary());        return resp;    }}