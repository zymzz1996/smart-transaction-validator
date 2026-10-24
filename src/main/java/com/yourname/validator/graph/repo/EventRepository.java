package com.yourname.validator.graph.repo;

import com.yourname.validator.graph.node.EventNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;

public interface EventRepository extends Neo4jRepository<EventNode, String> {
    @Query("""
        MATCH (e:OrderEvent {code: $eventCode})-[:ALLOWS]->(f:FeeType)
        RETURN f.code AS code
        """)
    List<String> findAllowedFeeTypes(String eventCode);
}
