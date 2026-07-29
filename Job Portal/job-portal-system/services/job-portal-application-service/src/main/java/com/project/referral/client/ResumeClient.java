package com.project.referral.client;

import com.project.referral.common.dto.response.ResumeResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

//@FeignClient(name = "job-portal-resume-service",
//        url = "${job.service.url:http://localhost:8082}")
//TODO ADD WORKING SERVICES

@Component
public interface ResumeClient {

   // @GetMapping("/api/resumes/{id}")
    ResumeResponse getResumeById(@PathVariable("id") Long id);

  //  @GetMapping("/api/resumes/{resumeId}")
    ResumeResponse getResumeById(
            @PathVariable Long resumeId,
            @RequestHeader("X-User-Id") Long candidateId);
}
