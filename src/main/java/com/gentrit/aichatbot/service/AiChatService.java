package com.gentrit.aichatbot.service;

import reactor.core.publisher.Flux;

public interface AiChatService {
    Flux<String> aiChatMessage(String question);
}
