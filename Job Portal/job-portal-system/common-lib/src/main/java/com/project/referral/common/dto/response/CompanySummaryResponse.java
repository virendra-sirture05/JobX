package com.project.referral.common.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.referral.common.domain.CompanySize;
import com.project.referral.common.domain.IndustryType;
import lombok.*;

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
    private String city;
    private String country;
}
