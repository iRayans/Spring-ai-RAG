package com.rayan.rag.lifecycle;

import com.rayan.rag.lifecycle.model.KnowledgeRequest;
import com.rayan.rag.lifecycle.model.SourceType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class KnowledgeIdentityTest {

    @Test
    void testSourceIdentity() {
        KnowledgeRequest pdfReq = KnowledgeRequest.builder()
                .sourceType(SourceType.PDF)
                .name("HR_Leave_Policy.pdf")
                .build();
        log.info("Identity: {}", KnowledgeIdentity.from(pdfReq));

        KnowledgeRequest dbReq = KnowledgeRequest.builder()
                .sourceType(SourceType.DATABASE)
                .name("faqs")
                .build();
        log.info("Identity: {}", KnowledgeIdentity.from(dbReq));

    }
}
