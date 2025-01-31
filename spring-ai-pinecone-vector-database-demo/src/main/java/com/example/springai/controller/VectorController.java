package com.example.springai.controller;

import com.example.springai.service.VectorService;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/vectors")
public class VectorController {

    private final VectorService vectorService;

    public VectorController(VectorService vectorService) {
        this.vectorService = vectorService;
    }

    @PostMapping("/store")
    public String store(@RequestBody Map<String, Object> payload) {
        String id = (String) payload.get("id");
        String text = (String) payload.get("text");
        Map<String, Object> metadata = (Map<String, Object>) payload.get("metadata");

        if (id == null || text == null) {
            return "Error: 'id' and 'text' are required fields.";
        }

        vectorService.storeText(id, text, metadata);
        return "Document stored successfully!";
    }

    @PostMapping("/search")
    public List<VectorStore.SearchResult> search(@RequestBody Map<String, Object> payload) {
        String query = (String) payload.get("query");
        Map<String, Object> filters = (Map<String, Object>) payload.get("filters");

        if (query == null) {
            throw new IllegalArgumentException("Query parameter is required");
        }

        return vectorService.search(query, filters);
    }
}
