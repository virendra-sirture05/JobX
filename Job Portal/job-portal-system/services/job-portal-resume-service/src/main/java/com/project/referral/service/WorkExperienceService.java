package com.project.referral.service;

import com.project.referral.common.dto.response.WorkExperienceResponse;
import com.project.referral.common.exception.ResourceNotFoundException;
import com.project.referral.dto.request.AddWorkExperienceRequest;

import java.util.List;

public interface WorkExperienceService {

    WorkExperienceResponse addWorkExperience(Long resumeId,
                                             Long candidateId,
                                             AddWorkExperienceRequest req)
            throws ResourceNotFoundException;

    List<WorkExperienceResponse> getWorkExperiences(Long resumeId) throws ResourceNotFoundException;

    WorkExperienceResponse updateWorkExperience(
            Long experienceId, Long resumeId, Long candidateId,
            AddWorkExperienceRequest req) throws ResourceNotFoundException;

    void deleteWorkExperience(Long experienceId, Long resumeId,
                              Long candidateId)
            throws ResourceNotFoundException;
}
