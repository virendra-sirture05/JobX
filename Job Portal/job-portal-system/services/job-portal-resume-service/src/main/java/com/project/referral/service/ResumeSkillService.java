package com.project.referral.service;

import com.project.referral.common.dto.response.ResumeSkillResponse;
import com.project.referral.common.exception.ResourceNotFoundException;
import com.project.referral.dto.request.AddResumeSkillRequest;

import java.util.List;

public interface ResumeSkillService {

    ResumeSkillResponse addSkill(Long resumeId, Long candidateId, AddResumeSkillRequest req)
            throws ResourceNotFoundException;

    List<ResumeSkillResponse> getSkills(Long resumeId) throws ResourceNotFoundException;

    ResumeSkillResponse updateSkill(Long skillId, Long resumeId, Long candidateId,
            AddResumeSkillRequest req) throws ResourceNotFoundException;

    void deleteSkill(Long skillId, Long resumeId, Long candidateId) throws ResourceNotFoundException;
}
