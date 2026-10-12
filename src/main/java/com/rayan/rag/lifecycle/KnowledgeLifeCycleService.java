package com.rayan.rag.lifecycle;

import com.rayan.rag.chunking.ChunkingOrchestrator;
import com.rayan.rag.chunking.model.Chunk;
import com.rayan.rag.ingestion.IngestionOrchestrator;
import com.rayan.rag.ingestion.model.IngestedDocument;
import com.rayan.rag.lifecycle.model.KnowledgeRequest;
import com.rayan.rag.vectorestore.ChunkVectorStoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class KnowledgeLifeCycleService {

    private final ChunkVectorStoreService vectorStoreService;
    private final IngestionOrchestrator ingestionOrchestrator;
    private final ChunkingOrchestrator chunkingOrchestrator;

    public void ingest(KnowledgeRequest request) throws Exception {
        String identity = KnowledgeIdentity.from(request);
        vectorStoreService.deleteByIdentity(identity);

        List<IngestedDocument> documents = ingestionOrchestrator.ingest(request);
        chunkAndStore(documents);
    }

    public void ingestAll() throws Exception {
        deleteAll();
        List<IngestedDocument> documents = ingestionOrchestrator.ingestAll();
        chunkAndStore(documents);
    }

    public void delete(KnowledgeRequest request) {
        String identity = KnowledgeIdentity.from(request);
        vectorStoreService.deleteByIdentity(identity);
    }

    public void deleteAll() {
        log.info("Delete all knowledge vector stores...");
        vectorStoreService.deleteAll();
    }

    private void chunkAndStore(List<IngestedDocument> documents) {
        List<Chunk> chunksToStore = new ArrayList<>();
        for (IngestedDocument document : documents) {
            List<Chunk> chunks = chunkingOrchestrator.chunk(document);
            chunksToStore.addAll(chunks);
        }
        log.info("Ingested documents: {}", documents.size());
        log.info("Chunks to store: {}", chunksToStore.size());
        vectorStoreService.store(chunksToStore);
    }
}
