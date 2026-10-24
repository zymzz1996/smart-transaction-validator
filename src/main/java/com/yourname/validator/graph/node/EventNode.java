package com.yourname.validator.graph.node;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.HashSet;
import java.util.Set;

@Node("OrderEvent")
@Data
public class EventNode {
    @Id
    private String code;
    private String name;

    @Relationship(type = "ALLOWS", direction = Relationship.Direction.OUTGOING)
    private Set<FeeTypeNode> allowedFeeTypes = new HashSet<>();
}
