package com.rayan.rag.retrieval;

import com.rayan.rag.chunking.model.Chunk;
import com.rayan.rag.retrieval.model.RetrievalResult;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class RetrieveServiceTest {
    @Autowired
    private RetrievalService retrievalService;

    @Test
    void retrieve_test() {
        RetrievalResult retrievalResult = retrievalService.retrieve("What is the leave carry forward policy");
        log.info("Retrieval results - chunks found {}", retrievalResult.getChunks().size());

        for (Chunk chunk : retrievalResult.getChunks()) {
            log.info("Metadata: {}", chunk.getMetadata());
            log.info("Content: {}", chunk.getContent());

        }
    }
}
