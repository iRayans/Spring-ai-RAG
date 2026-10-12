package com.rayan.rag.vectorestore;

import com.rayan.rag.chunking.model.Chunk;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.Filter;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ChunkVectorStoreService {

    private final VectorStore vectorStore;

    public ChunkVectorStoreService(@Qualifier("customVectorStore") VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    public void store(List<Chunk> chunks) {
        List<Document> documents = chunks.stream().map(chunk -> {
            Map<String, Object> metadata = new HashMap<>(chunk.getMetadata());
            metadata.put("source", chunk.getSource());
            metadata.put("chunkIndex", chunk.getChunkIndex());
            return new Document(
                    chunk.getContent(),
                    metadata
            );
        }).collect(Collectors.toList());

        vectorStore.add(documents);
    }

    public void deleteAll() {
        FilterExpressionBuilder filterBuilder = new FilterExpressionBuilder();
        // fetch all docs whose chunkIndex is greater than or equal to -1, All chunks are greater than -1. so delete all chunk.
        Filter.Expression filter = filterBuilder.gte("chunkIndex", -1).build();
        SearchRequest searchRequest = SearchRequest.builder().query(" ")
                .filterExpression(filter)
                .topK(10)
                .build();
        while (!vectorStore.similaritySearch(searchRequest).isEmpty()) {
            vectorStore.delete(filter);

        }
    }

    public void deleteByIdentity(String identity) {
        log.info("deleteByIdentity {}", identity);
        FilterExpressionBuilder filterBuilder = new FilterExpressionBuilder();
        vectorStore.delete(filterBuilder.eq("identity", identity).build());
    }
}
