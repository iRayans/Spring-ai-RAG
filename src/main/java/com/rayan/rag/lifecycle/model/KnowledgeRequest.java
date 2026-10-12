package com.rayan.rag.lifecycle.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class KnowledgeRequest {
    private final SourceType sourceType;
    private final String name;
}
