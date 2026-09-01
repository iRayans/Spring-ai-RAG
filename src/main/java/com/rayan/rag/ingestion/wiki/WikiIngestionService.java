package com.rayan.rag.ingestion.wiki;

import com.rayan.rag.ingestion.model.IngestedDocument;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class WikiIngestionService {

    private static final String WIKI_DIR = "data/wiki";


    public List<IngestedDocument> ingestWikiFiles() throws IOException {
        File[] markdownFiles = new File(WIKI_DIR).listFiles();

        List<IngestedDocument> docs = new ArrayList<>();

        for (File file : markdownFiles) {
            docs.add(ingestSingleFile(file));
        }
        return docs;
    }

    private IngestedDocument ingestSingleFile(File file) throws IOException {
        log.info("Ingesting a wiki file: {}", file.getName());

        String content = Files.readString(file.toPath());

        return new IngestedDocument(
                "WIKI",
                content,
                Map.of("filename", file.getName())
        );
    }
}
