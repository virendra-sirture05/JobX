package com.project.referral.dummyclient;

import com.project.referral.client.AiClient;
import com.project.referral.common.dto.response.ApiResponse;
import com.project.referral.dto.request.ScreeningScoreRequest;
import com.project.referral.dto.response.ScreeningScoreResponse;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Primary
public class DummyAiClient implements AiClient {

    @Override
    public ApiResponse<ScreeningScoreResponse> scoreCandidate(ScreeningScoreRequest request) {

        ScreeningScoreResponse response = new ScreeningScoreResponse();

        response.setScore(82);
        response.setSkillsMatchScore(80);
        response.setExperienceMatchScore(85);
        response.setEducationMatchScore(78);

        response.setMatchedSkills(List.of(
                "Java",
                "Spring Boot",
                "MySQL"
        ));

        response.setMissingSkills(List.of(
                "Kafka",
                "Redis"
        ));

        response.setStrengths(List.of(
                "Strong backend knowledge",
                "Good Java fundamentals"
        ));

        response.setConcerns(List.of(
                "No production microservice experience"
        ));

        response.setSummary("Dummy AI Screening Response");

        return ApiResponse.success("data fetched",response);
    }
}