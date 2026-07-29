package com.project.referral.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JobRequirements {
    private String jobTitle;
    private List<String> requiredSkills;
    private List<String> preferredSkills;
    private List<String> frameworks;
    private List<String> tools;
    private List<String> databases;
    private List<String> cloudPlatforms;
    private List<String> certifications;
    private Integer minimumExperience;
    private String education;
    private List<String> responsibilities;
    private List<String> softSkills;
    private List<String> keywords;
}
