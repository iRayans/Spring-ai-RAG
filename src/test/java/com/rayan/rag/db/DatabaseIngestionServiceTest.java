package com.rayan.rag.db;

import com.rayan.rag.ingestion.db.DatabaseIngestionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DatabaseIngestionServiceTest {
    @Autowired
    DatabaseIngestionService databaseIngestionService;

    @Test
    void testDatabaseIngestionService() {
        databaseIngestionService.ingestFaqs();
        databaseIngestionService.ingestReleaseNotes();
        databaseIngestionService.ingestAnnouncements();
    }
}
