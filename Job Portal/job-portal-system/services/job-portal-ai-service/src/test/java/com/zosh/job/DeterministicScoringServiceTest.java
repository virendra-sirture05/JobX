package com.project.referral;

import com.project.referral.config.RagProperties;
import com.project.referral.dto.response.CandidateEvaluation;
import com.project.referral.dto.response.JobRequirements;
import com.project.referral.dto.response.ResumeChunk;
import com.project.referral.dto.response.ResumeParseResponse;
import com.project.referral.service.DeterministicScoringServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

public class DeterministicScoringServiceTest {
    private DeterministicScoringServiceImpl svc;

    @BeforeEach
    void setup() {
        RagProperties props = new RagProperties();
        svc = new DeterministicScoringServiceImpl(props);
    }

    @Test
    void evaluates_scores_correctly() {
        JobRequirements req = JobRequirements.builder()
                .jobTitle("Backend Developer")
                .requiredSkills(List.of("Java", "Spring Boot", "PostgreSQL"))
                .minimumExperience(3)
                .education("Bachelor")
                .softSkills(List.of("communication"))
                .build();

        ResumeParseResponse parsed = new ResumeParseResponse();
        parsed.setSkills(List.of("Java", "Spring"));
        ResumeParseResponse.WorkExperience we = new ResumeParseResponse.WorkExperience();
        we.setStartDate("2019-01");
        we.setEndDate("2022-01");
        parsed.setWorkExperiences(List.of(we));
        parsed.setEducations(List.of());

        ResumeChunk c1 = ResumeChunk.builder().text("Implemented a project using Java and Spring Boot").build();
        ResumeChunk c2 = ResumeChunk.builder().text("Worked with PostgreSQL for database optimizations").build();

        CandidateEvaluation eval = svc.evaluate(req, parsed, List.of(c1, c2));
        assertThat(eval.getTechnicalScore()).isGreaterThan(0);
        assertThat(eval.getOverallScore()).isBetween(0.0, 100.0);
        assertThat(eval.getMatchedSkills()).contains("java");
    }
}
