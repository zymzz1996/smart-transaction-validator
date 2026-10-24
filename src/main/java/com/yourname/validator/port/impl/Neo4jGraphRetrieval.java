package com.yourname.validator.port.impl;

import com.yourname.validator.graph.repo.EventRepository;
import com.yourname.validator.port.GraphRetrievalPort;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "app.graph.enabled", havingValue = "true")
public class Neo4jGraphRetrieval implements GraphRetrievalPort {
    private final EventRepository eventRepository;

    @Override
    public List<String> findValidFeeTypesByEvent(String event) {
        return eventRepository.findAllowedFeeTypes(event);
    }
}
