package com.yourname.validator.port;

import java.util.List;

public interface GraphRetrievalPort {
    List<String> findValidFeeTypesByEvent(String event);
}
