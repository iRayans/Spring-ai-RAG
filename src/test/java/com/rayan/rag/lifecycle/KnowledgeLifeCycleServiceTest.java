package com.rayan.rag.lifecycle;

import com.rayan.rag.lifecycle.model.KnowledgeRequest;
import com.rayan.rag.lifecycle.model.SourceType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class KnowledgeLifeCycleServiceTest {

    @Autowired
    KnowledgeLifeCycleService knowledgeLifeCycleService;


    @Test
    void testIngestAll() throws Exception {
        knowledgeLifeCycleService.ingestAll();
    }

    @Test
    void testIngestPdf() throws Exception {
        KnowledgeRequest request = KnowledgeRequest.builder()
                .sourceType(SourceType.PDF)
                .name("HR_Leave_Policy.pdf")
                .build();
        knowledgeLifeCycleService.ingest(request);
    }

    @Test
    void testDeleteAll() {
        knowledgeLifeCycleService.deleteAll();
    }


    @Test
    void testDelete() {
        KnowledgeRequest request = KnowledgeRequest.builder()
                .sourceType(SourceType.PDF)
                .name("HR_Leave_Policy.pdf")
                .build();
        knowledgeLifeCycleService.delete(request);
    }

}
