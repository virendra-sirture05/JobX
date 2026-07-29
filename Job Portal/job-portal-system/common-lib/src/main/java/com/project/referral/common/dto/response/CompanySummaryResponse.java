package com.project.referral.common.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.referral.common.domain.CompanySize;
import com.project.referral.common.domain.IndustryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Lightweight company card response.
 * Embedded inside JobResponse/JobSummaryResponse so other services
 * can show company info without a separate Feign call.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompanySummaryResponse {

    private Long id;
    private String name;
    private String slug;
    private String logoUrl;
    private String tagline;
    private IndustryType industryType;
    private CompanySize companySize;
    private Boolean verified;

    /** HQ city and country for display on job cards. */
    private String city;
    private String country;
}
