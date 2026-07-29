package com.project.referral.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.referral.dto.response.CandidateEvaluation;
import com.project.referral.dto.response.JobRequirements;
import com.project.referral.dto.response.ResumeChunk;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PromptBuilder {
    private final ObjectMapper objectMapper;
    public String buildFinalPrompt(JobRequirements requirements, List<ResumeChunk> evidence, CandidateEvaluation eval) {        StringBuilder sb = new StringBuilder();        sb.append("You are a senior technical recruiter. You will be given structured job requirements, deterministic numeric scores, and resume evidence. Do NOT alter numeric scores. Provide explanations only: strengths, missing skills, interview recommendation, and a 2-sentence summary. Return ONLY valid JSON matching the fields: strengths (array), weaknesses (array), recommendation (string), summary (string).\n\n");        try {            sb.append("StructuredJobRequirements:\n");            sb.append(objectMapper.writeValueAsString(requirements)).append("\n\n");        } catch (JsonProcessingException e) {            // fallback simple representation            sb.append("JobTitle: ").append(requirements.getJobTitle()).append("\n");        }        sb.append("DeterministicScores:\n");        sb.append(objectMapper.valueToTree(eval)).append("\n\n");        sb.append("ResumeEvidence:\n");        String evidenceText = evidence.stream().map(ResumeChunk::getText).collect(Collectors.joining("\n---\n"));        sb.append(evidenceText).append("\n\n");        sb.append("Rules:\n");        sb.append("- Do NOT modify the provided numeric scores.\n");        sb.append("- Explain strengths and weaknesses based only on the evidence and structured requirements.\n");        sb.append("- Provide concrete interview recommendations (questions or focus areas).\n");        sb.append("- Return only JSON, no extra commentary or markdown fences.\n");        sb.append("\nRespond now with JSON.");        return sb.toString();    }}