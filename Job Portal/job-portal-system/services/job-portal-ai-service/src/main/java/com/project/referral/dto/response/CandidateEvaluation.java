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
public class CandidateEvaluation {
    private double overallScore;
    private double technicalScore;
    private double experienceScore;
    private double projectScore;
    private double educationScore;
    private double softSkillScore;
    private List<String> matchedSkills;
    private List<String> missingSkills;
    private List<String> strengths;
    private List<String> weaknesses;
    private String recommendation;
    private String summary;
}
