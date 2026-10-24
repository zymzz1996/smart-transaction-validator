package com.yourname.validator.service;

import com.yourname.validator.model.OrderTransaction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class VectorIndexService {
    private final ObjectProvider<VectorStore> vectorStoreProvider;

    public void indexTransaction(OrderTransaction tx, String result, String reason) {
        VectorStore vectorStore = vectorStoreProvider.getIfAvailable();
        if (vectorStore == null) return;

        try {
            String content = "Event: " + tx.getEvent() + ", FeeType: " + tx.getFeeType() + ", Result: " + result;
            Map<String, Object> metadata = new HashMap<>();
            metadata.put("event", tx.getEvent());
            metadata.put("feeType", tx.getFeeType().name());
            metadata.put("result", result);

            vectorStore.add(List.of(new Document(content, metadata)));
            log.info("Saved historical transaction to Vector DB");
        } catch (Exception e) {
            log.error("Failed to write to Vector DB", e);
        }
    }
}
