package com.gentrit.aichatbot.service;

import com.gentrit.aichatbot.dto.ExplanationLevel;
import reactor.core.publisher.Flux;

public interface AiChatService {
    Flux<String> aiChatMessage(String question, ExplanationLevel level);
}
