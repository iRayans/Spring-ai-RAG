package com.rayan.rag.prompt;

import com.rayan.rag.prompt.model.PromptContext;
import com.rayan.rag.retrieval.RetrievalService;
import com.rayan.rag.retrieval.model.RetrievalResult;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class ContextBuilderTest {

    @Autowired
    private RetrievalService retrievalService;

    @Test
    void testContextBuilder() {
        RetrievalResult retrievalResult = retrievalService.retrieve("What is the work from home policy?");

        PromptContext promptContext = new ContextBuilder().build(retrievalResult);

        log.info("======= Load Context =======");
        log.info("\n{}", promptContext.getContextText());

    }
}
