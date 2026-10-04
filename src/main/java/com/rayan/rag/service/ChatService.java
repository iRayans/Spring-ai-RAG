package com.rayan.rag.service;

import com.rayan.rag.chunking.model.Chunk;
import com.rayan.rag.dto.ChatRequest;
import com.rayan.rag.dto.ChatResponse;
import com.rayan.rag.retrieval.RetrievalService;
import com.rayan.rag.retrieval.model.RetrievalResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatService {
    private final ChatClient chatClient;
    private final RetrievalService retrievalService;


    public ChatResponse chat(ChatRequest request) {
        log.info("Sending chat request: {}", request.getMessage());
        String userMessage = request.getMessage();
        // Retrieve relevant chunks.
        RetrievalResult retrievalResult = retrievalService.retrieve(userMessage);
        String context = buildContext(retrievalResult);
        log.info("Context - " + context);
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
