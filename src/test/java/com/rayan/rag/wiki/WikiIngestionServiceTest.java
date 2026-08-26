package com.rayan.rag.wiki;

import com.rayan.rag.ingestion.wiki.WikiIngestionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
public class WikiIngestionServiceTest {

    @Autowired
    private WikiIngestionService wikiIngestionService;

    @Test
    public void ingestWikiFiles_forLearning() throws IOException {
        wikiIngestionService.ingestWikiFiles();
    }
}
