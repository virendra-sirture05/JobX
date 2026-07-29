package com.project.referral.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import java.util.List;
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GeminiExplanation {        private List<String> strengths;
    private List<String> weaknesses;
    private String recommendation;
    private String summary;
    private List<String> missingSkills;
}