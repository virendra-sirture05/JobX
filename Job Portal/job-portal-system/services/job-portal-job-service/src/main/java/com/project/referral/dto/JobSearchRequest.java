package com.project.referral.dto;

import com.project.referral.common.domain.ExperienceLevel;
import com.project.referral.common.domain.JobStatus;
import com.project.referral.common.domain.JobType;
import com.project.referral.common.domain.WorkMode;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobSearchRequest {
    // ── Full-text search ──────────────────────────────────────────────────────

    /** Searches across: title, description, skill names, tag names. */
    private String keyword;

    // ── Filters ───────────────────────────────────────────────────────────────

    private Long categoryId;

    /** Filter jobs that have ANY of these skill IDs. */
    private List<Long> skillIds;

    /** Filter jobs that have ANY of these tag IDs. */
    private List<Long> tagIds;

    private Long companyId;

    /** Matches city, state, or country (case-insensitive LIKE). */
    private String location;

    /** Salary overlap — job's max salary must be >= minSalary. */
    private BigDecimal minSalary;

    /** Salary overlap — job's min salary must be <= maxSalary. */
    private BigDecimal maxSalary;

    private JobType jobType;

    private WorkMode workMode;

    private ExperienceLevel experienceLevel;

    /** Defaults to OPEN in the service when null. */
    private JobStatus status;

    private Integer minOpenings;
    private Integer maxOpenings;
}


