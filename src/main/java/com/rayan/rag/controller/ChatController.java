package com.rayan.rag.controller;

import com.rayan.rag.dto.ChatRequest;
import com.rayan.rag.dto.ChatResponse;
import com.rayan.rag.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/chat")
    public ResponseEntity<ChatResponse> chat(@RequestBody ChatRequest chatRequest) {
        ChatResponse response = chatService.chat(chatRequest);

        return  ResponseEntity.ok(response);
    }
}
