package com.project.referral.config;

import com.google.genai.Client;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestClientConfig {
    @Bean
    public Client genAiClient(GeminiProperties props) {
        return Client.builder()
                .apiKey(props.getKey())
                .build();
    }
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
