package com.rayan.rag.prompt;

import com.rayan.rag.prompt.model.SystemInstructions;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class SystemPromptLoaderTest {

    @Test
    public void loadSystemPromptFromResources() {
        SystemInstructions instructions = new SystemPromptLoader().load();

        log.info("======= Load System Prompt: {}", instructions);
        log.info("\n{}", instructions.getInstructions());
    }
}
