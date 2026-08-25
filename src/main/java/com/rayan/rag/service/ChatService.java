package com.rayan.rag.service;

import com.rayan.rag.dto.ChatRequest;
import com.rayan.rag.dto.ChatResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatClient chatClient;

    public ChatResponse chat(ChatRequest request){
        String response = chatClient
                .prompt()
                .user(request.getMessage())
                .call()
                .content();
        return new ChatResponse(response);
    }


}
