package com.project.referral.service.impl;

import com.project.referral.Mapper.JobMapper;
import com.project.referral.common.domain.JobStatus;
import com.project.referral.common.dto.response.CompanyResponse;
import com.project.referral.common.dto.response.JobResponse;
import com.project.referral.dto.JobRequest;
import com.project.referral.dto.JobSearchRequest;
import com.project.referral.modal.Job;
import com.project.referral.modal.JobCategory;
import com.project.referral.modal.JobSkill;
import com.project.referral.modal.JobTag;
import com.project.referral.modal.embeddable.JobLocation;
import com.project.referral.modal.embeddable.SalaryRange;
import com.project.referral.repository.JobRepositary;
import com.project.referral.repository.JobSpecification;
import com.project.referral.service.JobCategorySevice;
import com.project.referral.service.JobService;
import com.project.referral.service.JobSkillService;
import com.project.referral.service.JobTagService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class JobServiceImpl implements JobService {

    private final JobRepositary jobRepositary;
    private final JobCategorySevice categorySevice;
    private final JobSkillService jobSkillService;
    private final JobTagService jobTagService;

    @Override
    @Transactional
    public JobResponse createJob(Long employerId, JobRequest req) throws Exception {

        JobCategory category = categorySevice.getCategoryEntityById(req.getCategoryId());

        Set<JobSkill> skills= req.getSkillIds()!=null?
                jobSkillService.getSkillByIds(req.getSkillIds())
                : Collections.emptySet();

        Set<JobTag> tags = req.getTagIds()!= null?
                jobTagService.getTagByIds(req.getTagIds())
                :Collections.emptySet();
    // todo :fetch company by employer id

        Long companyId = 1L;
        Job job = Job.builder()
                .title(req.getTitle())
                .description(req.getDescription())
                .requirements(req.getRequirements())
                .responsibilities(req.getResponsibilities())
                .benefits(req.getBenefits())
                .companyId(companyId)
                .employerId(employerId)
                .category(category)
                 .skills(skills)
                .tags(tags)
                .location(buildLocation(req))
                .salaryRange(buildSalaryRange(req))
                .jobType(req.getJobType())
                .workMode(req.getWorkMode())
                .experienceLevel(req.getExperienceLevel())
                .openings(req.getOpenings() != null ? req.getOpenings() : 1)
                .applicationDeadline(req.getApplicationDeadline())
                .expiresAt(req.getExpiresAt())
                .active(true)
                .build();

      Job savedJob = jobRepositary.save(job);
        return convertToResponse(savedJob);
    }



    @Override
    public JobResponse getJobById(Long id) throws Exception {
        Job job = jobRepositary.findById(id)
                .orElseThrow(() -> new Exception("Job not found"));

        return convertToResponse(job);
    }



    @Override
    public List<JobResponse> getJobs(JobSearchRequest req) {
        List<Job> jobs = jobRepositary.findAll(JobSpecification.build(req));
        return jobs.stream().map(
              this::convertToResponse
             ).collect(Collectors.toList());

    }

    @Override
    public List<JobResponse> getJobsByCompany(Long companyId) {
        List<Job> jobs = jobRepositary.findByCompanyId(companyId);
        return jobs.stream().map(
                this::convertToResponse
        ).collect(Collectors.toList());
    }

    @Override
    public JobResponse updateJob( Long jobId,Long employerId, JobRequest req)  throws Exception{
        Job job = jobRepositary.findById(jobId).orElseThrow(
                ()-> new Exception("Job not found")
        );
        assertEmployer(job,employerId);
        JobCategory category = categorySevice.getCategoryEntityById(req.getCategoryId());

        Set<JobSkill> skills= req.getSkillIds()!=null?
                jobSkillService.getSkillByIds(req.getSkillIds())
                : Collections.emptySet();

        Set<JobTag> tags = req.getTagIds()!= null?
                jobTagService.getTagByIds(req.getTagIds())
                :Collections.emptySet();


        job.setTitle(req.getTitle());
        job.setDescription(req.getDescription());
        job.setRequirements(req.getRequirements());
        job.setResponsibilities(req.getResponsibilities());
        job.setBenefits(req.getBenefits());
        // todo category not implemented yet
        job.setCategory(category);
        job.setSkills(skills);
        job.setTags(tags);
        job.setLocation(buildLocation(req));
        job.setSalaryRange(buildSalaryRange(req));
        job.setJobType(req.getJobType());
        job.setWorkMode(req.getWorkMode());
        job.setExperienceLevel(req.getExperienceLevel());
        job.setOpenings(req.getOpenings() != null ? req.getOpenings() : job.getOpenings());
        job.setApplicationDeadline(req.getApplicationDeadline());
        job.setExpiresAt(req.getExpiresAt());
        return convertToResponse(jobRepositary.save(job));
    }

    @Override
    public JobResponse publishJob(Long jobId, Long employerId) throws Exception {
      Job job = jobRepositary.findById(jobId).orElseThrow(
        ()-> new Exception("Job not found")
        );
      assertEmployer(job,employerId);
      if(job.getStatus()==JobStatus.CLOSED || job.getStatus()==JobStatus.EXPIRED){
           throw new Exception("Job is expired");
      }
        job.setStatus(JobStatus.OPEN);
        job.setPublishedAt(LocalDateTime.now());
        job.setActive(true);
        return convertToResponse(jobRepositary.save(job)) ;
    }



    @Override
    public JobResponse closeJob(Long jobId, Long employerId) throws Exception {
        Job job = jobRepositary.findById(jobId).orElseThrow(
                ()-> new Exception("Job not found")
        );
        assertEmployer(job,employerId);

        job.setStatus(JobStatus.CLOSED);
        job.setClosedAt(LocalDateTime.now());
        job.setActive(false);
        return convertToResponse(jobRepositary.save(job)) ;
    }

    @Override
    public void deleteJob(Long jobId, Long employerId) throws Exception {
        Job job = jobRepositary.findById(jobId).orElseThrow(
                ()-> new Exception("Job not found")
        );
        assertEmployer(job,employerId);
        jobRepositary.delete(job);
    }

    @Override
    public List<JobResponse> getAllJobsAdmin() {
        return jobRepositary.findAll().stream().map(
                this::convertToResponse
        ).collect(Collectors.toList());
    }

    //all methods
    private JobResponse convertToResponse(Job savedJob) {
        // todo : fetch company response
        CompanyResponse companyResponse = CompanyResponse.builder()
                .id(savedJob.getCompanyId())
                .build();

        return JobMapper.toResponse(savedJob,companyResponse);
    }

    private JobLocation buildLocation(JobRequest req) {
        return JobLocation.builder()
                .address(req.getAddress())
                .city(req.getCity())
                .state(req.getState())
                .country(req.getCountry())
                .zipCode(req.getZipCode())
                .build();
    }

    private SalaryRange buildSalaryRange(JobRequest req) {
        return SalaryRange.builder()
                .minSalary(req.getMinSalary())
                .maxSalary(req.getMaxSalary())
                .build();

    }
    private void assertEmployer(Job job, Long employerId) throws Exception {
        if (!job.getEmployerId().equals(employerId)) {
            throw new Exception("You are not the employer who posted this job");
        }
    }
}
