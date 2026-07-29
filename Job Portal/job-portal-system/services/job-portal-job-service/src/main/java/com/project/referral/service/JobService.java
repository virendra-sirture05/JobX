package com.project.referral.service;


import com.project.referral.common.dto.response.JobResponse;
import com.project.referral.dto.JobRequest;
import com.project.referral.dto.JobSearchRequest;

import java.util.List;

public interface JobService {
    JobResponse createJob(Long employerId, JobRequest req) throws Exception;

    JobResponse getJobById(Long id) throws Exception;
    List<JobResponse> getJobs(JobSearchRequest req);

    List<JobResponse> getJobsByCompany(Long companyId);

    JobResponse updateJob( Long jobid,Long employerId,JobRequest req) throws Exception;


    JobResponse publishJob(Long jobId, Long employerId)throws Exception;


    JobResponse closeJob(Long jobId, Long employerId) throws Exception;

    void deleteJob(Long jobId, Long employerId) throws Exception;

    List<JobResponse> getAllJobsAdmin();


}

