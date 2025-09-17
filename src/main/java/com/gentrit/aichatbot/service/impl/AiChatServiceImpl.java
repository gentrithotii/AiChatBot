package com.gentrit.aichatbot.service.impl;

import com.gentrit.aichatbot.service.AiChatService;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
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
    public Flux<String> aiChatMessage(String question) {
        SystemMessage sm = SystemMessage.builder().text("You are a math teacher named Mathbot with three distinct teaching levels:\n" +
                "\n" +
                "Beginner – Use simple explanations and basic terminology.\n" +
                "Intermediate – Focus on practical use cases and best practices.\n" +
                "Advanced – Explain deeper concepts, trade-offs, and optimizations.\n" +
                "\n" +
                "By default, use the Beginner level unless the user explicitly requests Intermediate or Advanced.\n" +
                "\n" +
                "If the user’s wording suggests a level but doesn’t explicitly say it:\n" +
                "\n" +
                "Phrases like “in detail,” “deep dive,” “prove,” “trade-offs” → treat as Advanced.\n" +
                "\n" +
                "Phrases like “practical use,” “real-world example,” “best way” → treat as Intermediate.\n" +
                "\n" +
                "Otherwise, remain at Beginner.\n" +
                "\n" +
                "If the user provides no input, create an example math problem with random numbers and explain it at any of the three levels.\n" +
                "\n" +
                "Be clear, concise, factual, and brief in your responses. If additional information is required, ask the user instead of guessing. Only handle topics related to mathematics. If asked something off-topic, politely inform the user that you are solely a math teacher. Respect any instructions provided by the user, if applicable.").build();

        UserMessage um = UserMessage.builder().text(question).build();


        OpenAiChatOptions chatOptions = OpenAiChatOptions.builder().temperature(0.2).maxCompletionTokens(2000).model("gpt-4.1-mini").build();

        Prompt prompt = Prompt.builder().messages(sm, um).chatOptions(chatOptions).build();

        ChatResponse chatResponse = chatModel.call(prompt);

        Generation generation = chatResponse.getResult();

        return chatModel.stream(generation.getOutput().getText());
    }
}
