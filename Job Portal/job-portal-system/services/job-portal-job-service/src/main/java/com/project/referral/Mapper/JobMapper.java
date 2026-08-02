package com.project.referral.Mapper;

import com.project.referral.common.dto.response.CompanyResponse;
import com.project.referral.common.dto.response.JobResponse;
import com.project.referral.common.dto.response.JobSkillResponse;
import com.project.referral.common.dto.response.JobTagResponse;
import com.project.referral.modal.Job;
import com.project.referral.modal.JobSkill;
import com.project.referral.modal.JobTag;
import com.project.referral.modal.JobTagMapper;
import com.project.referral.modal.embeddable.JobLocation;
import com.project.referral.modal.embeddable.SalaryRange;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class JobMapper {
    public static JobResponse toResponse(Job job, CompanyResponse companyResponse){

        JobLocation loc = job.getLocation();
        SalaryRange sal = job.getSalaryRange();
        Set<JobSkillResponse> skills = job.getSkills()==null?
                Collections.emptySet()
                :job.getSkills().stream().map(JobSkillMapper::toJobSkillResponse)
                .collect(Collectors.toSet());

        Set<JobTagResponse> tags = job.getTags() == null
                ? Collections.emptySet()
                : job.getTags().stream()
                .map(JobTagMapper::toTagResponse)
                .collect(Collectors.toSet());

        return JobResponse.builder()
                .id(job.getId())
                .title(job.getTitle())
                .description(job.getDescription())
                .requirements(job.getRequirements())
                .responsibilities(job.getResponsibilities())
                .benefits(job.getBenefits())
//                .company(companyResponse)
                .employerId(job.getEmployerId())
                .category(JobCategoryMapper.toJobCategoryResponse(job.getCategory(),false))
                .skills(skills)
                .tags(tags)
                // location
                .address(loc != null ? loc.getAddress() : null)
                .city(loc != null ? loc.getCity() : null)
                .state(loc != null ? loc.getState() : null)
                .country(loc != null ? loc.getCountry() : null)
                .zipCode(loc != null ? loc.getZipCode() : null)
                // salary
                .minSalary(sal != null ? sal.getMinSalary() : null)
                .maxSalary(sal != null ? sal.getMaxSalary() : null)

                // classification
                .jobType(job.getJobType())
                .workMode(job.getWorkMode())
                .experienceLevel(job.getExperienceLevel())
                .status(job.getStatus())
                // posting
                .openings(job.getOpenings())
                .applicationDeadline(job.getApplicationDeadline())
                .expiresAt(job.getExpiresAt())
                .active(job.getActive())

                // timestamps
                .createdAt(job.getCreatedAt())
                .updatedAt(job.getUpdatedAt())
                .publishedAt(job.getPublishedAt())
                .closedAt(job.getClosedAt())
                .build();

    }

}
