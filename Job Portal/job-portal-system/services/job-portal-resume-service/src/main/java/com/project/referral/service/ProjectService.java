package com.project.referral.service;

import com.project.referral.common.dto.response.ProjectResponse;
import com.project.referral.common.exception.ResourceNotFoundException;
import com.project.referral.dto.request.AddProjectRequest;

import java.util.List;

public interface ProjectService {

    ProjectResponse addProject(Long resumeId, Long candidateId, AddProjectRequest req)
            throws ResourceNotFoundException;

    List<ProjectResponse> getProjects(Long resumeId) throws ResourceNotFoundException;

    ProjectResponse updateProject(Long projectId, Long resumeId, Long candidateId,
                                  AddProjectRequest req) throws ResourceNotFoundException;

    void deleteProject(Long projectId, Long resumeId, Long candidateId)
            throws ResourceNotFoundException;

}
