package com.gentrit.aichatbot.controller;

import com.gentrit.aichatbot.service.AiChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("api/chat")
public class AiChatController {
    private AiChatService chatService;


    @Autowired
    public AiChatController(AiChatService chatService) {
        this.chatService = chatService;
    }


    @GetMapping("/message")
    Flux<String> chatWithAi(String question) {
        return chatService.aiChatMessage(question);
    }
}
