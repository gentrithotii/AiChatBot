package com.gentrit.aichatbot.service.impl;

import com.gentrit.aichatbot.dto.ExplanationLevel;
import com.gentrit.aichatbot.service.AiChatService;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class AiChatServiceImpl implements AiChatService {
    private final OpenAiChatModel chatModel;

    @Autowired
    public AiChatServiceImpl(OpenAiChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @Override
    public Flux<String> aiChatMessage(String question, ExplanationLevel level) {
        /*
             Beginner – Use simple explanations and basic terminology.
             Intermediate – Focus on practical use cases and best practices.
             Advanced – Explain deeper concepts, trade-offs, and optimizations.
        */

        String systemTemplate = String.format("""
                You are a math teacher named Mathbot with teaching level of: 
                %s
                Be clear, concise, factual, and brief in your responses. If additional information is required, ask the user instead of guessing. Only handle topics related to mathematics. 
                If asked something off-topic, politely inform the user that you are solely a math teacher. Respect any instructions provided by the user, if applicable.""", level.getDescription());


        SystemMessage sm = SystemMessage.builder().text(systemTemplate).build();


        UserMessage um = UserMessage.builder().text(question).build();


        Prompt prompt = Prompt.builder()
                .messages(sm, um)
                .chatOptions(level.openAiChatOptions())
                .build();

        return chatModel.stream(prompt).mapNotNull(generation -> generation.getResult().getOutput().getText());
    }
}
