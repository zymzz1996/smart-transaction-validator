package com.yourname.validator.port.impl;

import com.yourname.validator.port.VectorSearchPort;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ConditionalOnProperty(name = "app.vector.enabled", havingValue = "false", matchIfMissing = true)
public class InMemoryVectorSearch implements VectorSearchPort {
    @Override
    public List<String> searchSimilar(String event, String feeType, String status) {
        List<String> history = new ArrayList<>();
        if ("ORDER_PAYMENT".equals(event) && "ITEM_PRICE".equals(feeType)) {
            history.add("History: ORDER_PAYMENT with ITEM_PRICE was successful.");
        }
        return history;
    }
}
