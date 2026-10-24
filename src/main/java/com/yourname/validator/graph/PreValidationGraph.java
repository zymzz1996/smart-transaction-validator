package com.yourname.validator.graph;

import com.yourname.validator.agent.RetrievalAgent;
import com.yourname.validator.agent.ValidatorAgent;
import com.yourname.validator.model.OrderTransaction;
import com.yourname.validator.model.ValidationDecision;
import com.yourname.validator.model.ValidationResult;
import com.yourname.validator.rule.DeterministicRuleEngine;
import com.yourname.validator.service.VectorIndexService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PreValidationGraph {
    private final RetrievalAgent retrievalAgent;
    private final ValidatorAgent validatorAgent;
    private final DeterministicRuleEngine ruleEngine;
    private final VectorIndexService indexService;

    public ValidationResult execute(OrderTransaction tx) {
        String rules = retrievalAgent.retrieve(tx);
        ValidationDecision decision = validatorAgent.validate(tx, rules);
        boolean deterministicPassed = ruleEngine.verifyAll(tx);

        ValidationResult finalResult = ValidationResult.PASS;
        String reason = "Passed all checks";

        if (decision.getResult() == ValidationResult.FAIL) {
            finalResult = ValidationResult.FAIL;
            reason = decision.getReason();
        } else if (!deterministicPassed) {
            finalResult = ValidationResult.FAIL;
            reason = ruleEngine.firstFailure(tx);
        } else if (decision.getResult() == ValidationResult.REVIEW) {
            finalResult = ValidationResult.REVIEW;
            reason = decision.getReason();
        }

        indexService.indexTransaction(tx, finalResult.name(), reason);
        return finalResult;
    }
}
