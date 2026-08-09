package com.project.referral.service;

import com.project.referral.common.exception.ResourceNotFoundException;
import com.project.referral.dto.request.AddProjectRequest;
import com.project.referral.common.dto.response.ProjectResponse;
import java.util.List;

public interface ProjectService {
    ProjectResponse add(Long resumeId, Long candidateId, AddProjectRequest req) throws ResourceNotFoundException;
    List<ProjectResponse> get(Long resumeId) throws ResourceNotFoundException;
    ProjectResponse update(Long projectId, Long resumeId, Long candidateId, AddProjectRequest req) throws ResourceNotFoundException;
    void delete(Long projectId, Long resumeId, Long candidateId) throws ResourceNotFoundException;
}
