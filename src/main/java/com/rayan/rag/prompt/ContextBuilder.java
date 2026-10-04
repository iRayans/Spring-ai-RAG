package com.rayan.rag.prompt;

import com.rayan.rag.chunking.model.Chunk;
import com.rayan.rag.prompt.model.PromptContext;
import com.rayan.rag.retrieval.model.RetrievalResult;

public class ContextBuilder {

    public PromptContext build(RetrievalResult result) {
        StringBuilder sb = new StringBuilder();

        int index = 1;

        for (Chunk chunk : result.getChunks()) {
            sb.append("####### Context ").append(index++).append(":\n");
            sb.append(chunk.getContent());
            sb.append("\n\n");
        }

        return new PromptContext(sb.toString().trim());
    }
}
