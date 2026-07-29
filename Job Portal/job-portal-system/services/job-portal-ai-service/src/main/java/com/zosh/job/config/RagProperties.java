package com.project.referral.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "rag")
public class RagProperties {
    private Chunk chunk = new Chunk();
    private int topK = 10;
    private double similarityThreshold = 0.75;
    private ScoringWeights scoringWeights = new ScoringWeights();
    @Data
    public static class Chunk {
        private int size = 600;
        private int overlap = 100;
    }
    @Data
    public static class ScoringWeights {
        private double technical = 0.4;
        private double experience = 0.25;
        private double projects = 0.15;
        private double education = 0.1;
        private double soft = 0.1;
    }
}
