package com.rayan.rag.embedding.model;

import com.rayan.rag.chunking.model.Chunk;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EmbeddedChunk {

    private final Chunk chunk;
    private final float[] vector;
}
