package com.rayan.rag.service;

import com.rayan.rag.chunking.model.Chunk;
import com.rayan.rag.dto.ChatRequest;
import com.rayan.rag.dto.ChatResponse;
import com.rayan.rag.retrieval.RetrievalService;
import com.rayan.rag.retrieval.model.RetrievalResult;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatClient chatClient;
    private final RetrievalService retrievalService;


    public ChatResponse chat(ChatRequest request) {
        String userMessage = request.getMessage();
        RetrievalResult retrievalResult = retrievalService.retrieve(userMessage);
        String context = buildContext(retrievalResult);


        String response = chatClient
                .prompt()
                .system(context)
                .user(request.getMessage())
                .call()
                .content();
        return new ChatResponse(response);
    }

    private String buildContext(RetrievalResult retrievalResult) {
        StringBuilder contextBuilder = new StringBuilder();
        for (Chunk chunk : retrievalResult.getChunks()) {
            contextBuilder.append(chunk.getContent()).append("\n\n");
        }
        return contextBuilder.toString();
    }

}
