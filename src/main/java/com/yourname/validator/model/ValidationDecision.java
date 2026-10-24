package com.yourname.validator.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidationDecision {
    private ValidationResult result;
    private String reason;
    private List<String> ruleIds;
    private double riskScore;
}
