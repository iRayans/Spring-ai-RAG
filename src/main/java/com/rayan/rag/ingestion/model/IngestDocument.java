package com.rayan.rag.ingestion.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor
public class IngestDocument {
    private String source; // PDF, WIKI, DB
    private String content;
    private Map<String, Object> metadata;
}
