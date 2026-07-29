package com.project.referral.common.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.referral.common.domain.AiShortlistStatus;
import com.project.referral.common.domain.ApplicationSource;
import com.project.referral.common.domain.ApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Lightweight application card — used in application list views.
 * Employer sees: candidate + job + status + applied date.
 * Candidate sees: job + company + status + applied date.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApplicationSummaryResponse {

    private Long id;
    private Long candidateId;
    private Long jobId;
    private Long companyId;

    private ApplicationStatus status;
    private ApplicationSource source;

    private Boolean isRead;
    private Boolean isStarred;

    private LocalDateTime appliedAt;
    private LocalDateTime updatedAt;

    // AI screening summary — available once background scoring completes
    private Integer aiScore;
    private AiShortlistStatus aiShortlistStatus;
}
