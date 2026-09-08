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
public class PdfPragmaticChunkerTest {

    @Autowired
    private PdfPragmaticChunker chunker;

    @Autowired
    IngestionOrchestrator orchestrator;
    @Autowired
    private IngestionOrchestrator ingestionOrchestrator;

    @Test
    void chunk_odf_with_pragmatic_strategy() throws Exception {
        List<IngestedDocument> documents = ingestionOrchestrator.ingestAll();

        IngestedDocument pdfDoc = documents.stream()
                .filter(d -> d.getSource().equals("PDF"))
                .findFirst()
                .orElseThrow();

        List<Chunk> chunks = chunker.chunk(pdfDoc);

        log.info("PDF Source: {}", pdfDoc.getSource());
        log.info("Chunk Count: {}", chunks.size());

        for (Chunk chunk : chunks) {
            log.info("------------- Chunk -------------: {}", chunk.getChunkIndex());
            log.info("Metadata: {}", chunk.getMetadata());
            log.info(chunk.getContent());
        }
    }
}
