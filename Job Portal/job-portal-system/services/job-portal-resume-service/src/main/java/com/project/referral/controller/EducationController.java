package com.project.referral.controller;

import com.project.referral.common.dto.response.ApiResponse;
import com.project.referral.common.dto.response.EducationResponse;
import com.project.referral.common.exception.ResourceNotFoundException;
import com.project.referral.dto.request.AddEducationRequest;
import com.project.referral.service.EducationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resumes/{resumeId}/educations")
@RequiredArgsConstructor
public class EducationController {
    private final EducationService educationService;

    @PostMapping
    public ResponseEntity<EducationResponse> add(
            @PathVariable Long resumeId,
            @RequestHeader("X-User-Id") Long candidateId,
            @RequestBody @Valid AddEducationRequest req) throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(educationService.addEducation(resumeId, candidateId, req));
    }

    @GetMapping
    public ResponseEntity<List<EducationResponse>> get(@PathVariable Long resumeId)
            throws ResourceNotFoundException {
        return ResponseEntity.ok(educationService.getEducations(resumeId));
    }

    @PutMapping("/{educationId}")
    public ResponseEntity<EducationResponse> update(
            @PathVariable Long resumeId,
            @PathVariable Long educationId,
            @RequestHeader("X-User-Id") Long candidateId,
            @RequestBody @Valid AddEducationRequest req) throws ResourceNotFoundException {
        return ResponseEntity.ok(educationService.updateEducation(
                educationId, resumeId, candidateId, req));
    }

    @DeleteMapping("/{educationId}")
    public ResponseEntity<ApiResponse> delete(
            @PathVariable Long resumeId,
            @PathVariable Long educationId,
            @RequestHeader("X-User-Id") Long candidateId) throws ResourceNotFoundException {
        educationService.deleteEducation(educationId, resumeId, candidateId);
        return ResponseEntity.ok(new ApiResponse("Education deleted successfully", true));
    }
}
