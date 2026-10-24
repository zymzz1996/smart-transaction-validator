package com.yourname.validator.port;

import java.util.List;

public interface VectorSearchPort {
    List<String> searchSimilar(String event, String feeType, String status);
}
