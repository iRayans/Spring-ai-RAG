package com.rayan.rag.embedding;

import com.rayan.rag.chunking.ChunkingOrchestrator;
import com.rayan.rag.chunking.model.Chunk;
import com.rayan.rag.embedding.model.EmbeddedChunk;
import com.rayan.rag.ingestion.IngestionOrchestrator;
import com.rayan.rag.ingestion.model.IngestedDocument;
import com.rayan.rag.service.EmbeddingService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
public class EmbeddingInspectionTest {

    @Autowired
    IngestionOrchestrator ingestionOrchestrator;
    @Autowired
    ChunkingOrchestrator chunkingOrchestrator;
    @Autowired
    EmbeddingService embeddingService;

    @Test
    public void testEmbedding() throws Exception {
        List<IngestedDocument> document = ingestionOrchestrator.ingestAll();
        IngestedDocument ingestedDocument = document.get(0);

        List<Chunk> chunks = chunkingOrchestrator.chunk(ingestedDocument);
        for (Chunk chunk : chunks) {
            EmbeddedChunk embedded = embeddingService.embed(chunk);

            log.info("Metadata: {}", chunk.getMetadata());
            log.info("Content: {}", chunk.getContent());
            log.info("Embedding length: {}", embedded.getVector().length);
        }

    }
}
