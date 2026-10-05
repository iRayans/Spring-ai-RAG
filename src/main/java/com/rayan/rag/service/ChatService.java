package com.rayan.rag.service;

import com.rayan.rag.dto.ChatRequest;
import com.rayan.rag.dto.ChatResponse;
import com.rayan.rag.prompt.PromptOrchestrator;
import com.rayan.rag.prompt.model.ChatPrompt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatService {
    private final ChatClient chatClient;
    private final PromptOrchestrator promptOrchestrator;


    public ChatResponse chat(ChatRequest request) {
        log.info("Sending chat request: {}", request.getMessage());
        String userMessage = request.getMessage();

        ChatPrompt chatPrompt = promptOrchestrator.build(userMessage);
        // Retrieve relevant chunks.

        String llmInput = chatPrompt.getGroundingRule()
                + "\n\n"
                + chatPrompt.getContext().getContextText()
                + "\nUser Question:\n"
                + userMessage;

        String aiResponse = chatClient.prompt()
                .system(chatPrompt.getSystemInstructions().getInstructions())
                .user(llmInput)
                .call()
                .content();
        return new ChatResponse(aiResponse);
    }

//    private String buildContext(RetrievalResult retrievalResult) {
//        StringBuilder contextBuilder = new StringBuilder();
//        for (Chunk chunk : retrievalResult.getChunks()) {
//            contextBuilder.append(chunk.getContent()).append("\n\n");
//        }
//        return contextBuilder.toString();
//    }

}
