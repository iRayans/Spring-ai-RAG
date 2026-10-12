package com.rayan.rag.controller;

import com.rayan.rag.lifecycle.KnowledgeLifeCycleService;
import com.rayan.rag.lifecycle.model.KnowledgeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/lifecycle")
@RequiredArgsConstructor
public class LifeCycleController {

    private final KnowledgeLifeCycleService lifeCycleService;

    @PostMapping("/ingest")
    public ResponseEntity<Object> ingest(@RequestBody KnowledgeRequest request) throws Exception {
        lifeCycleService.ingest(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/ingest-all")
    public ResponseEntity<Object> ingestAll() throws Exception {
        lifeCycleService.ingestAll();
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete-all")
    public ResponseEntity<Object> deleteAll() {
        lifeCycleService.deleteAll();
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> delete(@RequestBody KnowledgeRequest request) throws Exception {
        lifeCycleService.delete(request);
        return ResponseEntity.ok().build();
    }

}
