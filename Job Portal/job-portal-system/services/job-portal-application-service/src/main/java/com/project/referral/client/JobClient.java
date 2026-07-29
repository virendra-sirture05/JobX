package com.project.referral.client;

import com.project.referral.common.dto.response.JobResponse;
import com.project.referral.common.dto.response.JobSummaryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient(name = "job-portal-job-service",
//        url = "${job.service.url:http://localhost:8082}"
//)
//TODO ADD WORKING SERVICES

public interface JobClient {

   // @GetMapping("/api/jobs/{id}")
    JobResponse getJobById(@PathVariable("id") Long id);

  //  @GetMapping("/api/jobs/{id}/summary")
    JobSummaryResponse getJobSummaryById(@PathVariable("id") Long id);
}
