# Smart Transaction Validator (Agentic RAG)

[![Java Version](https://img.shields.io/badge/Java-17-blue.svg)](https://java.com)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.0-green.svg)](https://spring.io/projects/spring-boot)
[![Spring AI](https://img.shields.io/badge/Spring%20AI-1.0.0--M6-orange.svg)](https://spring.io/projects/spring-ai)

An innovative demonstration project showcasing how to securely integrate Large Language Models (LLMs) into strict business validation pipelines. 

Traditional rule engines often struggle with "long-tail" scenarios and complex combinatorial logic, leading to massive `if-else` spaghetti code. This project introduces an **Agentic RAG** architecture, allowing the LLM to autonomously retrieve rules, while maintaining a **Deterministic Fallback** to guarantee zero-hallucination in critical operations.

## 🌝 Key Features

- **Agentic Tool Calling**: Using Spring AI's `@Tool` annotation, the LLM autonomously decides which tools to call (Graph DB vs. Vector DB) based on the transaction context.
- **GraphRAG & Vector Search**:
  - **Neo4j**: Acts as the knowledge graph for strict relational constraints (e.g., "An Event allows specific Fee Types").
  - **PGVector**: Used as an experience memory to retrieve historically similar transactions and their outcomes.
- **Deterministic Fallback (Hallucination Defense)**: LLM provides semantic suggestions, but the final release authority belongs to the `DeterministicRuleEngine`. If the LLM suggests PASS but the hard rules fail, the transaction is strictly blocked.
- **High Availability (HA)**: Built with an in-memory fallback strategy. If Neo4j, PGVector, or the LLM service goes down, the system seamlessly switches to an in-memory rule mode, ensuring the core pipeline is never blocked.

## 🏠️ Architecture Overview

```text
OrderTransaction
      │
      ▲
ParseNode (Deterministic Parsing)
      │
      ▲
RetrievalAgent (LLM Autonomous Decision)
      ├── Tool 1: Query Neo4j (Graph constraints)
      └── Tool 2: Query PGVector (Historical cases)
      │
      ▼
ValidatorAgent (LLM Semantic Judgment)
      │
      ▲
DeterministicRuleEngine (Hard Rules Veto)
      │
      ▲
PASS / FAIL / REVIEW
```

## 🚀 Quick Start

### 1. Memory Mode (No Middleware Required)

You can run the core logic completely in-memory. This is great for a quick preview.

```bash
mvn spring-boot:run
```

Test the endpoints:
- **PASS Scenario**: [http://localhost:8080/api/validate/test-pass](http://localhost:8080/api/validate/test-pass)
- **FAIL Scenario**: [http://localhost:8080/api/validate/test-fail](http://localhost:8080/api/validate/test-fail)

### 2. Full RAG Mode (Requires Docker & OpenAI Key)

To experience the GraphRAG and Vector Search capabilities, start the middleware and configure your API key.

**Step 1: Start Middleware**
```bash
docker-compose up -d
```

**Step 2: Set Environment Variables**
```bash
export APP_GRAPH_ENABLED=true
export APP_VECTOR_ENABLED=true
export OPENAI_API_KEY=your_openai_api_key_here
```

**Step 3: Run Application**
```bash
mvn spring-boot:run
```

## 🧠 Core Design Principles

1. **Probabilistic Reasoning must be constrained by Deterministic Validation.**
   LLMs are great at semantics but terrible at precise arithmetic and strict logic. We never let the LLM write data directly. It only provides `PASS/FAIL/REVIEW` suggestions, and the final execution is governed by `DeterministicRuleEngine`.
2. **Structuring the "Long-Tail" Problem.**
   By integrating Graph databases, we map out the strict business rules dynamically. For edge cases where rules don't exist, the Vector database kicks in to retrieve historical human decisions.

## 📄 Disclaimer

This project is a purely fictional technical demonstration. The business logic and data models (e.g., `OrderTransaction`, `FeeType`) are simulated for the purpose of showcasing Agentic RAG architecture. It contains no proprietary or confidential information from any financial institution.