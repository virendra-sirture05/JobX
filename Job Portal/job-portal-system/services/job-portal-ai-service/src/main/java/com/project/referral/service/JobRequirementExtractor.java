package com.project.referral.service;

import com.project.referral.client.GeminiClient;
import com.project.referral.dto.response.JobRequirements;
import com.project.referral.exception.GeminiParsingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JobRequirementExtractor {
    private final GeminiClient geminiClient;
    private static final String SYSTEM = "You are a structured job requirements extractor. When asked to return JSON, respond ONLY with valid JSON and no other text.";
    public JobRequirements extract(String jobTitle, String jobDescription) {        try {            String prompt = "Extract structured job requirements from the following job description. Return only JSON matching the JobRequirements fields: jobTitle, requiredSkills, preferredSkills, frameworks, tools, databases, cloudPlatforms, certifications, minimumExperience, education, responsibilities, softSkills, keywords.\n\nJob Title: %s\nJob Description:\n%s".formatted(                    jobTitle != null ? jobTitle : "", jobDescription != null ? jobDescription : "");            return geminiClient.generateJson(SYSTEM, prompt, JobRequirements.class);        } catch (Exception e) {            throw new GeminiParsingException("Failed to extract job requirements: " + e.getMessage(), e);        }    }}