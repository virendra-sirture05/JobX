package com.project.referral.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "gemini.api")
public class GeminiProperties {

    private String key;
    private String model;

    /** Embedding model name. Default targets Google's textembedding-gecko series. */
    private String embeddingModel = "textembedding-gecko-001";

    private int maxOutputTokens = 2048;
    private double temperature = 0.7;
}
