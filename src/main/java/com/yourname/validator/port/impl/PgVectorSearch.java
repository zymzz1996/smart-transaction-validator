package com.yourname.validator.port.impl;

import com.yourname.validator.port.VectorSearchPort;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "app.vector.enabled", havingValue = "true")
public class PgVectorSearch implements VectorSearchPort {
    private final VectorStore vectorStore;

    @Override
    public List<String> searchSimilar(String event, String feeType, String status) {
        String query = String.format("Event: %s, FeeType: %s, Status: %s", event, feeType, status);
        return vectorStore.similaritySearch(
                SearchRequest.builder()
                        .query(query)
                        .topK(5)
                        .similarityThreshold(0.7)
                        .filterExpression("event == '" + event + "'")
                        .build()
        ).stream().map(Document::getText).toList();
    }
}
