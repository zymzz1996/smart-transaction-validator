package com.yourname.validator.port.impl;

import com.yourname.validator.port.GraphRetrievalPort;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ConditionalOnProperty(name = "app.graph.enabled", havingValue = "false", matchIfMissing = true)
public class InMemoryGraphRetrieval implements GraphRetrievalPort {

    private final Map<String, List<String>> eventRules = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        eventRules.put("ORDER_PAYMENT", Arrays.asList("ITEM_PRICE", "SHIPPING_FEE", "TAX_FEE", "DISCOUNT"));
        eventRules.put("ORDER_CREATION", Arrays.asList("ITEM_PRICE", "DISCOUNT"));
        eventRules.put("LATE_FEE_CHARGE", Arrays.asList("LATE_FEE", "PENALTY"));
    }

    @Override
    public List<String> findValidFeeTypesByEvent(String event) {
        return eventRules.getOrDefault(event, List.of());
    }
}
