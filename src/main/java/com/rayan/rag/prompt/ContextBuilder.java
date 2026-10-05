package com.rayan.rag.prompt;

import com.rayan.rag.chunking.model.Chunk;
import com.rayan.rag.prompt.model.PromptContext;
import com.rayan.rag.retrieval.model.RetrievalResult;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class ContextBuilder {

    public PromptContext build(RetrievalResult result) {
        StringBuilder sb = new StringBuilder();

        int index = 1;

        for (Chunk chunk : result.getChunks()) {
            sb.append("####### Context ").append(index++).append(":\n");
            appendCitation(sb, chunk);
            sb.append(chunk.getContent());
            sb.append("\n\n");
        }

        return new PromptContext(sb.toString().trim());
    }

    private void appendCitation(StringBuilder sb, Chunk chunk) {
        Map<String, Object> metadata = chunk.getMetadata();
        String source = metadata.get("source").toString();

        log.info("filename: {}", metadata.get("filename"));

        switch (source) {
            case "PDF":
            case "WIKI":
                sb.append("[")
                        .append(source)
                        .append(": ").append(metadata.get("filename"))
                        .append("]\n");
                break;

            case "DB":
                sb.append("[DB: ")
                        .append(metadata.get("table")).append("#")
                        .append(metadata.get("id"))
                        .append("]\n");
                break;
        }
    }
}
