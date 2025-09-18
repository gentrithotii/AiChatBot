package com.gentrit.aichatbot.controller;

import com.gentrit.aichatbot.dto.ChatRequestDto;
import com.gentrit.aichatbot.service.AiChatService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@Validated
@RestController
@RequestMapping("api/chat")
public class AiChatController {
    private AiChatService chatService;


    @Autowired
    public AiChatController(AiChatService chatService) {
        this.chatService = chatService;
    }


    @PostMapping("/message")
    Flux<String> chatWithAi(@RequestBody @Valid ChatRequestDto chatRequestDto) {
        return chatService.aiChatMessage(chatRequestDto.getQuestion(), chatRequestDto.getExplanationLevel());
    }
}
