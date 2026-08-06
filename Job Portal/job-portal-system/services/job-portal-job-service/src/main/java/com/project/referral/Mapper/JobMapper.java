package com.project.referral.Mapper;

import com.project.referral.common.dto.response.*;
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
                .company(companyResponse)
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


    public static JobSummaryResponse toSummaryResponse(Job job) {
        JobLocation loc = job.getLocation();
        SalaryRange sal = job.getSalaryRange();

        Set<String> skillNames = job.getSkills() == null ? Collections.emptySet()
                : job.getSkills().stream().map(JobSkill::getName).collect(Collectors.toSet());

        Set<String> tagNames = job.getTags() == null ? Collections.emptySet()
                : job.getTags().stream().map(JobTag::getName).collect(Collectors.toSet());

        return JobSummaryResponse.builder()
                .id(job.getId())
                .title(job.getTitle())
                .companyId(job.getCompanyId())
                .city(loc != null ? loc.getCity() : null)
                .country(loc != null ? loc.getCountry() : null)
                .minSalary(sal != null ? sal.getMinSalary() : null)
                .maxSalary(sal != null ? sal.getMaxSalary() : null)
                .currency(sal != null ? sal.getCurrency() : null)
                .salaryDisclosed(sal != null ? sal.getDisclosed() : null)
                .jobType(job.getJobType())
                .workMode(job.getWorkMode())
                .experienceLevel(job.getExperienceLevel())
                .status(job.getStatus())
                .categoryName(job.getCategory() != null ? job.getCategory().getName() : null)
                .skillNames(skillNames)
                .tagNames(tagNames)
                .openings(job.getOpenings())
//                .applicationCount(job.getApplicationCount())
                .applicationDeadline(job.getApplicationDeadline())
                .createdAt(job.getCreatedAt())
                .build();
    }

}
