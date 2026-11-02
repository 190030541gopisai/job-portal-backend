package com.gopi.job.config;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "gemini.api")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GeminiProperties {
    private String key;
    private String model;
    private int maxOutputTokens;
    private double temperature;
}
