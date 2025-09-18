package com.gentrit.aichatbot.dto;

import lombok.Getter;
import org.springframework.ai.openai.OpenAiChatOptions;

public enum ExplanationLevel {
    BEGINNER("Beginner - Use simple explanations and basic terminology.", "gpt-4.1", 2000, 0.3),
    INTERMEDIATE("Intermediate - Focus on practical use cases and best practices.", "gpt-4.1-mini", 1250, 0.4),
    ADVANCED("Advanced - Explain deeper concepts, trade-offs, and optimizations", "gpt-4.1-mini", 750, 0.4);

    @Getter
    private final String description;
    private final String model;
    private final int maxTokens;
    private final double temperature;


    ExplanationLevel(String description, String model, int maxTokens, double temperature) {
        this.description = description;
        this.model = model;
        this.maxTokens = maxTokens;
        this.temperature = temperature;
    }

    public OpenAiChatOptions openAiChatOptions() {
        return OpenAiChatOptions.builder().model(model).maxCompletionTokens(maxTokens).temperature(temperature).build();
    }
}
