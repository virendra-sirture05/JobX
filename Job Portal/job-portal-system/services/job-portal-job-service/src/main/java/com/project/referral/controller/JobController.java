package com.project.referral.controller;


import com.project.referral.common.dto.response.ApiResponse;
import com.project.referral.common.dto.response.JobResponse;
import com.project.referral.dto.JobRequest;
import com.project.referral.dto.JobSearchRequest;
import com.project.referral.service.JobService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.support.ResourceTransactionManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;
    //private final ResourceTransactionManager resourceTransactionManager;

    @PostMapping
    public ResponseEntity<JobResponse> createJob(
            @RequestHeader("X-User-Id") Long employerId,
            @RequestBody @Valid JobRequest req) throws Exception {
           return ResponseEntity.status(HttpStatus.CREATED)
                   .body(jobService.createJob(employerId,req));

    }


    @GetMapping("/{id}")
    public ResponseEntity<JobResponse> getJobById(
            @PathVariable Long id) throws Exception {
        return ResponseEntity.ok(jobService.getJobById(id));
    }

    @GetMapping
    public ResponseEntity<List<JobResponse>> getJobs(
            @ModelAttribute JobSearchRequest req) {
        return ResponseEntity.ok(jobService.getJobs(req));
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<JobResponse>> getJobsByCompany(
            @PathVariable Long companyId) {
        return ResponseEntity.ok(jobService.getJobsByCompany(companyId));
    }

    /*@GetMapping("/my")
    public ResponseEntity<List<JobResponse>> getMyJobs(
            @RequestHeader("X-User-Id") Long employerId) {
        return ResponseEntity.ok(jobService.getJobsByEmployer(employerId));
    }*/

    @GetMapping("/admin")
    public ResponseEntity<List<JobResponse>> getAllJobsAdmin(){
        return ResponseEntity.ok(jobService.getAllJobsAdmin());
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobResponse> updateJob(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long employerId,
            @RequestBody @Valid JobRequest req)
            throws Exception {
        return ResponseEntity.ok(jobService.updateJob(id, employerId, req));
    }

    // ── Status transitions ────────────────────────────────────────────────────

    @PatchMapping("/{id}/publish")
    public ResponseEntity<JobResponse> publishJob(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long employerId)
            throws Exception {
        return ResponseEntity.ok(jobService.publishJob(id, employerId));
    }

    // ── Delete ────────────────────────────────────────────────────────────────

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteJob(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long employerId)
            throws Exception {
        jobService.deleteJob(id, employerId);
        return ResponseEntity.ok(new ApiResponse("Job deleted successfully", true));
    }



}
