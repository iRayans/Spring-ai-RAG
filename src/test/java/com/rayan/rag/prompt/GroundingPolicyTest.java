package com.rayan.rag.prompt;

import com.rayan.rag.prompt.model.PromptContext;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class GroundingPolicyTest {

    @Test
    void emptyContextTest() {
        GroundingPolicy groundingPolicy = new GroundingPolicy();
        String rule = groundingPolicy.groundingPolicyName(new PromptContext(""));
        log.info("=== EMPTY CONTEXT RULES ===");
        log.info("\n{}", rule);
    }

    @Test
    void nonEmptyContextTest() {
        GroundingPolicy groundingPolicy = new GroundingPolicy();
        String rule = groundingPolicy.groundingPolicyName(new PromptContext("Some internal policy text!"));
        log.info("=== GROUND CONTEXT RULES ===");
        log.info("\n{}", rule);
    }
}
