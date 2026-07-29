package com.project.referral.client;

import com.project.referral.common.dto.response.CompanyResponse;
import com.project.referral.common.dto.response.CompanySummaryResponse;
import com.project.referral.common.exception.ResourceNotFoundException;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

//@FeignClient(name="job-portal-company-service",
//        url = "${job.service.url:http://localhost:8082}")
////TODO ADD WORKING SERVICES

public interface CompanyClient {

  //  @GetMapping("/api/companies/my")
    CompanyResponse getMyCompany(
            @RequestHeader("X-User-Id") Long ownerId) throws ResourceNotFoundException;

   // @GetMapping("/api/companies/summary/{id}")
    CompanySummaryResponse getCompanySummaryById(@PathVariable("id") Long id);
}
