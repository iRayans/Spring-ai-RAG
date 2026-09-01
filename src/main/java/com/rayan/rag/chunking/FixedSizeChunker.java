package com.rayan.rag.chunking;

import com.rayan.rag.chunking.model.Chunk;
import com.rayan.rag.ingestion.model.IngestedDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FixedSizeChunker {

    public List<Chunk> chunk(IngestedDocument document, int chunkSize) {
        return chunk(document, chunkSize, 0);
    }

    public List<Chunk> chunk(IngestedDocument document, int chunkSize, int overlap) {
        List<Chunk> chunks = new ArrayList<>();

        String content = document.getContent();
        int start = 0, chunkIndex = 0;

        while (start < content.length()) {
            int end = Math.min(start + chunkSize, content.length());
            String chunkText = content.substring(start, end);

            Map<String, Object> chunkMetadata = new HashMap<>(document.getMetadata());

            chunks.add(new Chunk(
                    document.getSource(),
                    chunkText,
                    chunkMetadata,
                    chunkIndex
            ));
            chunkIndex += 1;
            if (end < content.length()) {
                start = end - overlap;
            } else {
                break;
            }
        }
        return chunks;
    }
}
