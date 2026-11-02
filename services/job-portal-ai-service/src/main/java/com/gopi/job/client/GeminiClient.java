package com.gopi.job.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.genai.Client;
import com.google.genai.types.Content;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.GenerateContentResponse;
import com.gopi.job.config.GeminiProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GeminiClient {

    private final GeminiProperties geminiProperties;
    private final ObjectMapper objectMapper = new ObjectMapper();

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

    public String generateText(String systemInstruction,
                            String prompt) throws Exception {
        Client client = Client.builder()
                .apiKey(geminiProperties.getKey())
                .build();

        GenerateContentConfig config = GenerateContentConfig.builder()
                .systemInstruction(Content.fromParts(com.google.genai.types.Part.fromText(systemInstruction)))
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
    public <T> T generateJson(String systemPrompt, String prompt, Class<T> responseClass) throws Exception {
        String json = generateText(systemPrompt, prompt);

        // Strip markdown code fences if present
        json = json.replaceAll("(?s)```json\\s*(.*?)\\s*```", "$1").trim();

        return objectMapper.readValue(json, responseClass);
    }
}
