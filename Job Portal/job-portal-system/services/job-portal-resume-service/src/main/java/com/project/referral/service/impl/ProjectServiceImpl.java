package com.project.referral.service.impl;

import com.project.referral.common.exception.ResourceNotFoundException;
import com.project.referral.common.dto.response.ProjectResponse;
import com.project.referral.dto.request.AddProjectRequest;
import com.project.referral.entity.Project;
import com.project.referral.entity.Resume;
import com.project.referral.mapper.ResumeMapper;
import com.project.referral.repository.ProjectRepository;
import com.project.referral.service.ProjectService;
import com.project.referral.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service @RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final ResumeService resumeService;

    @Override @Transactional
    public ProjectResponse add(Long resumeId, Long candidateId, AddProjectRequest req) throws ResourceNotFoundException {
        Resume resume = resumeService.getResumeEntity(resumeId); assertOwner(resume, candidateId, resumeId);
        return ResumeMapper.toProjectResponse(save(new Project(), resume, req));
    }
    @Override @Transactional(readOnly = true)
    public List<ProjectResponse> get(Long resumeId) throws ResourceNotFoundException {
        resumeService.getResumeEntity(resumeId);
        return projectRepository.findByResume_IdOrderByDisplayOrderAsc(resumeId).stream().map(ResumeMapper::toProjectResponse).toList();
    }
    @Override @Transactional
    public ProjectResponse update(Long projectId, Long resumeId, Long candidateId, AddProjectRequest req) throws ResourceNotFoundException {
        Project project = getProject(projectId, resumeId); assertOwner(project.getResume(), candidateId, resumeId);
        return ResumeMapper.toProjectResponse(save(project, project.getResume(), req));
    }
    @Override @Transactional
    public void delete(Long projectId, Long resumeId, Long candidateId) throws ResourceNotFoundException {
        Project project = getProject(projectId, resumeId); assertOwner(project.getResume(), candidateId, resumeId);
        projectRepository.delete(project);
    }
    private Project save(Project p, Resume resume, AddProjectRequest r) {
        p.setResume(resume); p.setTitle(r.getTitle()); p.setDescription(r.getDescription());
        p.setTechnologies(r.getTechnologies() == null ? List.of() : r.getTechnologies());
        p.setProjectUrl(r.getProjectUrl()); p.setSourceCodeUrl(r.getSourceCodeUrl());
        p.setStartDate(r.getStartDate()); p.setEndDate(Boolean.TRUE.equals(r.getIsOngoing()) ? null : r.getEndDate());
        p.setIsOngoing(Boolean.TRUE.equals(r.getIsOngoing())); p.setDisplayOrder(r.getDisplayOrder() == null ? 0 : r.getDisplayOrder());
        return projectRepository.save(p);
    }
    private Project getProject(Long id, Long resumeId) throws ResourceNotFoundException {
        Project p = projectRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + id));
        if (!p.getResume().getId().equals(resumeId)) throw new ResourceNotFoundException("Project not found with id: " + id);
        return p;
    }
    private void assertOwner(Resume r, Long candidateId, Long resumeId) throws ResourceNotFoundException {
        if (!r.getCandidateId().equals(candidateId)) throw new ResourceNotFoundException("Resume not found with id: " + resumeId);
    }
}
