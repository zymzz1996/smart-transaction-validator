package com.yourname.validator.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

@Configuration
@ConditionalOnProperty(name = "app.graph.enabled", havingValue = "true")
@EnableNeo4jRepositories(basePackages = "com.yourname.validator.graph.repo")
public class Neo4jConfig {
}
