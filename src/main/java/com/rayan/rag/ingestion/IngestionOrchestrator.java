package com.rayan.rag.ingestion;

import com.rayan.rag.ingestion.db.DatabaseIngestionService;
import com.rayan.rag.ingestion.model.IngestedDocument;
import com.rayan.rag.ingestion.pdf.PdfIngestionService;
import com.rayan.rag.ingestion.wiki.WikiIngestionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class IngestionOrchestrator {
    // Each service focuses on its own extraction, Orchestrator focuses onn the flow.
    private final PdfIngestionService pdfIngestionService;
    private final WikiIngestionService wikiIngestionService;
    private final DatabaseIngestionService databaseIngestionService;

    public List<IngestedDocument> ingestAll() throws Exception {
        List<IngestedDocument> docs = new ArrayList<>();
        docs.addAll(pdfIngestionService.ingestPdfs());
        docs.addAll(wikiIngestionService.ingestWikiFiles());
        docs.addAll(databaseIngestionService.ingestDatabaseContent());
        return docs;
    }
}
