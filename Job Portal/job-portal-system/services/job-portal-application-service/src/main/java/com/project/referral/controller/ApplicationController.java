package com.project.referral.controller;

import com.project.referral.common.dto.response.ApiResponse;
import com.project.referral.common.dto.response.ApplicationResponse;
import com.project.referral.common.exception.ApplicationException;
import com.project.referral.common.exception.ResourceNotFoundException;
import com.project.referral.dto.request.CompanyApplicationFilterRequest;
import com.project.referral.dto.request.CreateApplicationRequest;
import com.project.referral.dto.request.UpdateApplicationStatusRequest;
import com.project.referral.dto.request.WithdrawApplicationRequest;
import com.project.referral.service.ApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    // ── Create ────────────────────────────────────────────────────────────────

    @PostMapping
    public ResponseEntity<ApplicationResponse> createApplication(
            @RequestHeader("X-User-Id") Long candidateId,
            @RequestBody @Valid CreateApplicationRequest req)
            throws ApplicationException {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(applicationService.createApplication(candidateId, req));
    }

    // ── Read ──────────────────────────────────────────────────────────────────

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationResponse> getApplicationById(
            @PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(applicationService.getApplicationById(id));
    }

    @GetMapping("/my")
    public ResponseEntity<List<ApplicationResponse>> getMyApplications(
            @RequestHeader("X-User-Id") Long candidateId) {
        return ResponseEntity.ok(applicationService.getMyApplications(candidateId));
    }

    @GetMapping("/job/{jobId}")
    public ResponseEntity<List<ApplicationResponse>> getApplicationsForJob(
            @PathVariable Long jobId) {
        return ResponseEntity.ok(applicationService.getApplicationsForJob(jobId));
    }

    /**
     * Returns all applications for a company with optional filters.
     * Results are always ordered by appliedAt DESC (newest first).
     *
     * Filters: ?jobId=5 &status=REVIEWING &source=DIRECT
     *          &isRead=false &isStarred=true
     *          &appliedFrom=2025-01-01 &appliedTo=2025-03-31
     */
    @GetMapping("/company")
    public ResponseEntity<List<ApplicationResponse>> getApplicationsForCompany(
            @RequestHeader("X-User-Id") Long userId,
            @ModelAttribute CompanyApplicationFilterRequest filter) throws ResourceNotFoundException {
        return ResponseEntity.ok(applicationService.getApplicationsForCompany(
                userId, filter));
    }

    // ── Status transitions ────────────────────────────────────────────────────

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApplicationResponse> updateStatus(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long employerId,
            @RequestBody @Valid UpdateApplicationStatusRequest req)
            throws ResourceNotFoundException, ApplicationException {
        return ResponseEntity.ok(applicationService.updateStatus(id, employerId, req));
    }

    @PatchMapping("/{id}/withdraw")
    public ResponseEntity<ApplicationResponse> withdraw(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long candidateId,
            @RequestBody WithdrawApplicationRequest req)
            throws ResourceNotFoundException, ApplicationException {
        return ResponseEntity.ok(applicationService.withdraw(id, candidateId, req));
    }

    // ── Tracking flags ────────────────────────────────────────────────────────

    @PatchMapping("/{id}/read")
    public ResponseEntity<ApplicationResponse> markAsRead(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long employerId)
            throws ResourceNotFoundException, ApplicationException {
        return ResponseEntity.ok(applicationService.markAsRead(id, employerId));
    }

    @PatchMapping("/{id}/star")
    public ResponseEntity<ApplicationResponse> toggleStar(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long employerId)
            throws ResourceNotFoundException, ApplicationException {
        return ResponseEntity.ok(applicationService.toggleStar(id, employerId));
    }

    // ── Internal (called by job-service after a job is edited) ───────────────

    @PostMapping("/internal/jobs/{jobId}/mark-stale")
    public ResponseEntity<Void> markScreeningsStale(@PathVariable Long jobId) {
        applicationService.markScreeningsStaleForJob(jobId);
        return ResponseEntity.noContent().build();
    }

    // ── Delete ────────────────────────────────────────────────────────────────

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteApplication(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long candidateId)
            throws ResourceNotFoundException, ApplicationException {
        applicationService.deleteApplication(id, candidateId);
        return ResponseEntity.ok(new ApiResponse("Application deleted successfully", true));
    }
}
