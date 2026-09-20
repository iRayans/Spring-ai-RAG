package com.rayan.rag.vectorstore;

import com.rayan.rag.chunking.ChunkingOrchestrator;
import com.rayan.rag.chunking.model.Chunk;
import com.rayan.rag.ingestion.IngestionOrchestrator;
import com.rayan.rag.ingestion.model.IngestedDocument;
import com.rayan.rag.vectorestore.ChunkVectorStoreService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class ChunkVectorStoreServiceTest {

    @Autowired
    private IngestionOrchestrator ingestionOrchestrator;
    @Autowired
    private ChunkingOrchestrator chunkingOrchestrator;
    @Autowired
    private ChunkVectorStoreService chunkVectorStoreService;

    @Test
    public void testVectorStore() throws Exception {
        List<IngestedDocument> documents = ingestionOrchestrator.ingestAll();

        List<Chunk> chunkToStore = new ArrayList<>();
        for (IngestedDocument document : documents) {
            List<Chunk> chunks = chunkingOrchestrator.chunk(document);
            chunkToStore.addAll(chunks);
        }
        chunkVectorStoreService.store(chunkToStore);
    }
}
