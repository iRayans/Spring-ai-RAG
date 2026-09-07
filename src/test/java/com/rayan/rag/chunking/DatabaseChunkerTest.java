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
public class DatabaseChunkerTest {

    @Autowired
    DatabaseChunker databaseChunker;
    @Autowired
    private IngestionOrchestrator ingestionOrchestrator;

    @Test
    void testDatabaseChunker() throws Exception {
        List<IngestedDocument> documents = ingestionOrchestrator.ingestAll();

        List<IngestedDocument> dbDocuments = documents.stream()
                .filter(doc -> "DB".equals(doc.getSource()))
                .toList();


        for (IngestedDocument document : dbDocuments) {
            List<Chunk> chunks = databaseChunker.chunks(document);
            Chunk chunk = chunks.get(0);

            log.info("--------- DB Chunk ---------");
            log.info("Source {}", chunk.getSource());
            log.info("Chunk Index {}", chunk.getChunkIndex());
            log.info("Metadata {}", chunk.getMetadata());
            log.info("Content {}", chunk.getContent());
        }
    }

}
