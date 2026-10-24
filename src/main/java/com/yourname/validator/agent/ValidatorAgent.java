package com.yourname.validator.agent;

import com.yourname.validator.model.OrderTransaction;
import com.yourname.validator.model.ValidationDecision;
import com.yourname.validator.model.ValidationResult;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ValidatorAgent {
    private final ChatClient chatClient;

    public ValidatorAgent(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    public ValidationDecision validate(OrderTransaction tx, String retrievedRules) {
        String prompt = """
            You are a transaction validation expert. Decide PASS / FAIL / REVIEW based on rules.
            
            Event: %s
            Amount: %s
            FeeType: %s
            Status: %s
            
            Rules:
            %s
            
            Output JSON: {"result": "PASS/FAIL/REVIEW", "reason": "...", "ruleIds": [], "riskScore": 0.5}
            """.formatted(tx.getEvent(), tx.getAmount(), tx.getFeeType(), tx.getOrderStatus(), retrievedRules);

        try {
            return chatClient.prompt().user(prompt).call().entity(ValidationDecision.class);
        } catch (Exception e) {
            return new ValidationDecision(ValidationResult.REVIEW, "LLM error, fallback to manual review", List.of(), 0.5);
        }
    }
}
