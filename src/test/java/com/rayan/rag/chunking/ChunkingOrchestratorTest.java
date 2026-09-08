package com.rayan.rag.chunking;

import com.rayan.rag.chunking.model.Chunk;
import com.rayan.rag.ingestion.IngestionOrchestrator;
import com.rayan.rag.ingestion.model.IngestedDocument;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
public class ChunkingOrchestratorTest {
    @Autowired
    private ChunkingOrchestrator chunkingOrchestrator;
    @Autowired
    private IngestionOrchestrator ingestionOrchestrator;

    @Test
    public void testAllChunks() throws Exception {
        List<IngestedDocument> documents = ingestionOrchestrator.ingestAll();
        for (IngestedDocument document : documents) {
            List<Chunk> chunks = chunkingOrchestrator.chunk(document);

            log.info("-------------------------------------------");
            log.info("SORUCE {}", document.getSource());
            log.info("CHUNKS {}", chunks.size());

            for (Chunk chunk : chunks) {
                log.info("Chunks index {}", chunk.getChunkIndex());
                log.info("Metadata {}", chunk.getMetadata());
                log.info("Content {}", chunk.getContent());
            }

        }
    }
}
