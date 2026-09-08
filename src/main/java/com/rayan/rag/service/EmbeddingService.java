package com.rayan.rag.service;

import com.rayan.rag.chunking.model.Chunk;
import com.rayan.rag.embedding.model.EmbeddedChunk;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmbeddingService {

    private final EmbeddingModel embeddingModel;

    public EmbeddedChunk embed(Chunk chunk) {
        float[] vector = embeddingModel.embed(chunk.getContent());
        return new EmbeddedChunk(
                chunk,
                vector
        );
    }

}
