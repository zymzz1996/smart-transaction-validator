package com.yourname.validator.graph.node;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node("FeeType")
@Data
public class FeeTypeNode {
    @Id
    private String code;
    private String desc;
}
