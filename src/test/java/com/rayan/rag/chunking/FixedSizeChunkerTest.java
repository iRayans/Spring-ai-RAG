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
public class FixedSizeChunkerTest {

    @Autowired
    private FixedSizeChunker chunker;
    @Autowired
    private IngestionOrchestrator ingestionOrchestrator;

    @Test
    public void chunkerTest() throws Exception {
        List<IngestedDocument> docs = ingestionOrchestrator.ingestAll();

        IngestedDocument document = docs.get(0);

        log.info(" ================ NO OVERLAP ================");
        List<Chunk> chunks = chunker.chunk(document, 500);
        printChunks(document, chunks);

        log.info(" ================ WITH OVERLAP (100 chars ================");
        List<Chunk> overLapChunks = chunker.chunk(document, 500, 100);
        printChunks(document, overLapChunks);

    }

    private static void printChunks(IngestedDocument document, List<Chunk> chunks) {
        log.info("Source {}", document.getSource());
        log.info("length {}", document.getContent().length());
        log.info("Original length: {}", document.getContent().length());
        log.info("Total Chunks: {}", chunks.size());

        for (Chunk chunk : chunks) {
            log.info(" ---------- Chunk ---------- {}", chunk.getChunkIndex());
            log.info(chunk.getContent());
            log.info("Length {}", chunk.getContent().length());
        }
    }


}
