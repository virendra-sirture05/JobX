package com.project.referral.dummyclient;

import com.project.referral.client.ResumeClient;
import com.project.referral.common.dto.response.ResumeResponse;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Component
@Primary
public class DummyResumeClient implements ResumeClient {

    @Override
    public ResumeResponse getResumeById(Long id) {

        ResumeResponse resume = new ResumeResponse();

        resume.setId(id);
        resume.setCandidateId(1L);
        resume.setTitle("Java Backend Resume");
        resume.setSummary("Experienced Java Developer");
        resume.setCompletionScore(90);
        resume.setUploadedFileName("resume.pdf");
        resume.setUploadedFileUrl("dummy-url");
        resume.setCreatedAt(LocalDateTime.now());

        resume.setSkills(new ArrayList<>());
        resume.setEducations(new ArrayList<>());
        resume.setWorkExperiences(new ArrayList<>());

        return resume;
    }

    @Override
    public ResumeResponse getResumeById(Long resumeId, Long candidateId) {

        ResumeResponse resume = getResumeById(resumeId);
        resume.setCandidateId(candidateId);

        return resume;
    }
}