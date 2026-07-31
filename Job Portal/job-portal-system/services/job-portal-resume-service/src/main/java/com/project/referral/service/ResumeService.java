package com.project.referral.service;

import com.project.referral.common.exception.ResourceNotFoundException;
import com.project.referral.dto.request.CreateResumeRequest;
import com.project.referral.dto.request.UpdatePersonalInfoRequest;
import com.project.referral.dto.request.UpdateResumeRequest;
import com.project.referral.dto.response.ResumeResponse;
import com.project.referral.entity.Resume;

import java.util.List;

public interface ResumeService {

    ResumeResponse createResume(Long candidateId, CreateResumeRequest req);

    ResumeResponse getResumeById(Long resumeId, Long candidateId) throws ResourceNotFoundException;

    List<ResumeResponse> getMyResumes(Long candidateId);

    ResumeResponse updatePersonalInfo(
            Long resumeId, Long candidateId,
            UpdatePersonalInfoRequest req)
            throws ResourceNotFoundException;

    ResumeResponse updateSummary(Long resumeId, Long candidateId,
                                 String summary)
            throws ResourceNotFoundException;

    ResumeResponse updateResume(Long resumeId, Long candidateId, UpdateResumeRequest req)
            throws ResourceNotFoundException;

    ResumeResponse setDefaultResume(Long resumeId, Long candidateId) throws ResourceNotFoundException;

    void deleteResume(Long resumeId, Long candidateId) throws ResourceNotFoundException;

    Resume getResumeEntity(Long resumeId) throws ResourceNotFoundException;
}
