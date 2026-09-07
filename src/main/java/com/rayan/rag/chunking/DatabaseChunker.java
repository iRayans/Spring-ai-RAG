package com.rayan.rag.chunking;

import com.rayan.rag.chunking.model.Chunk;
import com.rayan.rag.ingestion.model.IngestedDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DatabaseChunker {

    public List<Chunk> chunks(IngestedDocument document) {
        return List.of(
                new Chunk(
                        document.getSource(),
                        document.getContent(),
                        document.getMetadata(),
                        0
                ));
    }
}
