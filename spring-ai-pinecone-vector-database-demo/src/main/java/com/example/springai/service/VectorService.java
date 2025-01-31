package com.example.springai.service;

import org.springframework.ai.embedding.EmbeddingClient;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.VectorStore.SearchResult;
import org.springframework.ai.vectorstore.metadata.FilterExpression;
import org.springframework.ai.vectorstore.metadata.Metadata;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class VectorService {

    private final VectorStore vectorStore;
    private final EmbeddingClient embeddingClient;

    public VectorService(VectorStore vectorStore, EmbeddingClient embeddingClient) {
        this.vectorStore = vectorStore;
        this.embeddingClient = embeddingClient;
    }

    public void storeText(String id, String text, Map<String, Object> metadata) {
        List<Double> embedding = embeddingClient.embed(text).get(0);
        Metadata meta = Metadata.from(metadata);
        vectorStore.add(id, embedding, text, meta);
    }

    public List<SearchResult> search(String query, Map<String, Object> filters) {
        List<Double> queryEmbedding = embeddingClient.embed(query).get(0);
        FilterExpression filterExpression = FilterExpression.from(filters);
        return vectorStore.similaritySearch(queryEmbedding, 5, filterExpression);
    }
}
