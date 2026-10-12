package com.rayan.rag.ingestion;

import com.rayan.rag.ingestion.db.DatabaseIngestionService;
import com.rayan.rag.ingestion.model.IngestedDocument;
import com.rayan.rag.ingestion.pdf.PdfIngestionService;
import com.rayan.rag.ingestion.wiki.WikiIngestionService;
import com.rayan.rag.lifecycle.model.KnowledgeRequest;
import com.rayan.rag.lifecycle.model.SourceType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class IngestionOrchestrator {
    // Each service focuses on its own extraction, Orchestrator focuses onn the flow.
    private final PdfIngestionService pdfIngestionService;
    private final WikiIngestionService wikiIngestionService;
    private final DatabaseIngestionService databaseIngestionService;

    public List<IngestedDocument> ingest(KnowledgeRequest request) throws Exception {
        SourceType source = request.getSourceType();
        if (source.equals(SourceType.PDF)) {
            return pdfIngestionService.ingest(request.getName());
        }

        if (source.equals(SourceType.WIKI)) {
            return wikiIngestionService.ingest(request.getName());
        }

        if (source.equals(SourceType.DATABASE)) {
            return databaseIngestionService.ingest(request.getName());
        }
        return Collections.emptyList();
    }


    public List<IngestedDocument> ingestAll() throws Exception {
        List<IngestedDocument> docs = new ArrayList<>();
        docs.addAll(pdfIngestionService.ingestPdfs());
        docs.addAll(wikiIngestionService.ingestWikiFiles());
        docs.addAll(databaseIngestionService.ingestDatabaseContent());
        return docs;
    }
}
