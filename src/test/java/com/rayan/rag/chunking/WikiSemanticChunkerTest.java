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
public class WikiSemanticChunkerTest {

    @Autowired
    private WikiSemanticChunker wikiSemanticChunker;
    @Autowired
    private IngestionOrchestrator ingestionOrchestrator;
    @Autowired
    FixedSizeChunker fixedSizeChunker;

    @Test
    public void testChunker() throws Exception {
        List<IngestedDocument> documents = ingestionOrchestrator.ingestAll();

        // Pick a wiki documnt
        IngestedDocument wikiDoc = documents.stream()
                .filter(doc -> doc.getSource().contains("WIKI"))
                .findFirst()
                .orElseThrow();

        log.info("============== FIXED SIZE CHUNKING =============");
        List<Chunk> fixedChunks = fixedSizeChunker.chunk(wikiDoc, 500, 100);
        printChunks(wikiDoc, fixedChunks);

        log.info("============== SEMANTIC (WIKI) CHUNKING =============");
        List<Chunk> semanticChunks = wikiSemanticChunker.chunk(wikiDoc);
        printChunks(wikiDoc, semanticChunks);
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
