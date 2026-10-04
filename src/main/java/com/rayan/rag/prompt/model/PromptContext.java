package com.rayan.rag.prompt.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PromptContext {
    private final String contextText;
}
