package com.project.referral.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
@Data
@Component
@ConfigurationProperties(prefix = "chroma")
public class ChromaProperties {
    /** Base URL for Chromadb HTTP server, e.g., http://localhost:8000 */
    private String url = "http://localhost:8000";
    private String collection = "resumes";
}
