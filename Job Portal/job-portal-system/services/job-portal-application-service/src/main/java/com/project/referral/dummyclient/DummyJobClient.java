package com.project.referral.dummyclient;

import com.project.referral.client.JobClient;
import com.project.referral.common.dto.response.CompanySummaryResponse;
import com.project.referral.common.dto.response.JobResponse;
import com.project.referral.common.dto.response.JobSummaryResponse;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
@Primary
public class DummyJobClient implements JobClient {

    @Override
    public JobResponse getJobById(Long id) {

        JobResponse job = new JobResponse();
        CompanySummaryResponse companySummaryResponse = new CompanySummaryResponse();

        companySummaryResponse.setId(id);
        companySummaryResponse.setName("Dummy Technologies");
        companySummaryResponse.setSlug("dummy-technologies");
        companySummaryResponse.setTagline("Dummy Tagline");
        companySummaryResponse.setCity("Pune");
        companySummaryResponse.setCountry("India");
        companySummaryResponse.setVerified(true);
        job.setId(id);
        job.setCompany(companySummaryResponse);
        job.setTitle("Java Backend Developer");
        job.setDescription("Dummy Description");
        job.setRequirements("Java, Spring Boot");
        job.setResponsibilities("Develop APIs");
        job.setBenefits("WFH");
        job.setEmployerId(id);
        job.setCity("Pune");
        job.setCountry("India");

        job.setMinSalary(BigDecimal.valueOf(800000));
        job.setMaxSalary(BigDecimal.valueOf(1200000));
        job.setCurrency("INR");

        job.setApplicationDeadline(LocalDate.now().plusDays(30));
        job.setActive(true);

        return job;
    }

    @Override
    public JobSummaryResponse getJobSummaryById(Long id) {

        JobSummaryResponse summary = new JobSummaryResponse();

        summary.setId(id);
        summary.setTitle("Java Backend Developer");
        summary.setCity("Pune");
        summary.setCountry("India");
        summary.setCurrency("INR");
        summary.setApplicationDeadline(LocalDate.now().plusDays(30));

        return summary;
    }
}