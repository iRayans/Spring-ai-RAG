package com.rayan.rag.prompt;

import com.rayan.rag.prompt.model.ChatPrompt;
import com.rayan.rag.prompt.model.PromptContext;
import com.rayan.rag.prompt.model.SystemInstructions;
import com.rayan.rag.retrieval.RetrievalService;
import com.rayan.rag.retrieval.model.RetrievalResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PromptOrchestrator {

    private final RetrievalService retrievalService;
    private final ContextBuilder contextBuilder = new ContextBuilder();
    private final SystemPromptLoader systemPromptLoader = new SystemPromptLoader();
    private final GroundingPolicy groundingPolicy = new GroundingPolicy();


    public ChatPrompt build(String question) {
        // retrieve knowledge from the vectorStore
        RetrievalResult retrievalResult = retrievalService.retrieve(question);
        // from the retrieve results, prepare the prompt context
        PromptContext promptContext = contextBuilder.build(retrievalResult);
        // define the grounding rule based on the prompt context
        String rule = groundingPolicy.groundingPolicyName(promptContext);
        // Load the system instructions
        SystemInstructions systemInstructions = systemPromptLoader.load();
        // Create the chat prompt which includes system instructions, prompt context and grounding rule
        return new ChatPrompt(systemInstructions, promptContext, rule);

    }
}
