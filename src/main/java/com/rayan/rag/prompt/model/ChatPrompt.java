package com.rayan.rag.prompt.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatPrompt {
    // How model must behave
    private final SystemInstructions systemInstructions;
    // What knowledge the model is allowed to use
    private final PromptContext context;
    // How model should work with the context
    private final String groundingRule;
}
