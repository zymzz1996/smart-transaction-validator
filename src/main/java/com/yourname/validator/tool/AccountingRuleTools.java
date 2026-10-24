package com.yourname.validator.tool;

import com.yourname.validator.port.GraphRetrievalPort;
import com.yourname.validator.port.VectorSearchPort;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AccountingRuleTools {
    private final GraphRetrievalPort graphPort;
    private final VectorSearchPort vectorPort;

    @Tool(description = "Query allowed fee types for a specific event.")
    public List<String> queryValidFeeTypesByEvent(String event) {
        return graphPort.findValidFeeTypesByEvent(event);
    }

    @Tool(description = "Retrieve historical similar transactions for reference.")
    public List<String> queryHistoricalSimilarTransactions(String event, String feeType, String status) {
        return vectorPort.searchSimilar(event, feeType, status);
    }
}
