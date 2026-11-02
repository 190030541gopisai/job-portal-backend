package com.gopi.job.client;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.GenerateContentResponse;
import com.gopi.job.config.GeminiProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GeminiClient {

    private final GeminiProperties geminiProperties;

    public String generateText(String prompt) {
        Client client = Client.builder()
                .apiKey(geminiProperties.getKey())
                .build();

        GenerateContentConfig config = GenerateContentConfig.builder()
                .temperature((float) geminiProperties.getTemperature())
                .maxOutputTokens(geminiProperties.getMaxOutputTokens())
                .build();

        GenerateContentResponse response = client.models.generateContent(
                geminiProperties.getModel(),
                prompt,
                config
        );

        return response.text();
    }
}
