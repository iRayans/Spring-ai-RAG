package com.rayan.rag.ingestion.wiki;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Service
@Slf4j
public class WikiIngestionService {

    private static final String WIKI_DIR = "data/wiki";


    public void ingestWikiFiles() throws IOException {
        File[] markdownFiles = new File(WIKI_DIR).listFiles();

        for (File file : markdownFiles) {
            ingestSingleFile(file);
        }
    }

    private void ingestSingleFile(File file) throws IOException {
        log.info("Ingesting a wiki file: {}", file.getName());

        String content = Files.readString(file.toPath());

        log.info("------- Wiki Content ({}) -------", file.getName());
        log.info(content);
    }
}
