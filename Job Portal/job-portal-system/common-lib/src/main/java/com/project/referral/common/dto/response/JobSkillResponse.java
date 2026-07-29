package com.project.referral.common.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.referral.common.domain.SkillCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JobSkillResponse {

    private Long id;
    private String name;
    private String slug;
    private SkillCategory category;
    private Boolean active;
}
