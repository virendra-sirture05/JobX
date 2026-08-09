package com.project.referral.controller;

import com.project.referral.common.dto.response.ApiResponse;
import com.project.referral.common.exception.ResourceNotFoundException;
import com.project.referral.common.dto.response.ProjectResponse;
import com.project.referral.dto.request.AddProjectRequest;
import com.project.referral.entity.Project;
import com.project.referral.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/resumes/{resumeId}/projects") @RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;
    @PostMapping public ResponseEntity<ProjectResponse> add(@PathVariable Long resumeId, @RequestHeader("X-User-Id") Long candidateId, @RequestBody @Valid AddProjectRequest req) throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(projectService.add(resumeId, candidateId, req));
    }
    @GetMapping public ResponseEntity<List<ProjectResponse>> get(@PathVariable Long resumeId) throws ResourceNotFoundException { return ResponseEntity.ok(projectService.get(resumeId)); }
    @PutMapping("/{projectId}") public ResponseEntity<ProjectResponse> update(@PathVariable Long resumeId, @PathVariable Long projectId, @RequestHeader("X-User-Id") Long candidateId, @RequestBody @Valid AddProjectRequest req) throws ResourceNotFoundException { return ResponseEntity.ok(projectService.update(projectId, resumeId, candidateId, req)); }
    @DeleteMapping("/{projectId}") public ResponseEntity<ApiResponse> delete(@PathVariable Long resumeId, @PathVariable Long projectId, @RequestHeader("X-User-Id") Long candidateId) throws ResourceNotFoundException { projectService.delete(projectId, resumeId, candidateId); return ResponseEntity.ok(new ApiResponse("Project deleted successfully", true)); }
}
