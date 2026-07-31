package com.project.referral.service;

import com.project.referral.common.exception.ResourceNotFoundException;
import com.project.referral.dto.request.AddEducationRequest;
import com.project.referral.common.dto.response.EducationResponse;

import java.util.List;

public interface EducationService {


    EducationResponse addEducation(Long resumeId, Long candidateId, AddEducationRequest req)
            throws ResourceNotFoundException;

    List<EducationResponse> getEducations(Long resumeId) throws ResourceNotFoundException;

    EducationResponse updateEducation(Long educationId, Long resumeId, Long candidateId,
                                      AddEducationRequest req) throws ResourceNotFoundException;

    void deleteEducation(Long educationId, Long resumeId, Long candidateId)
            throws ResourceNotFoundException;
}



