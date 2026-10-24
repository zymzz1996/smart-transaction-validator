package com.yourname.validator.controller;

import com.yourname.validator.graph.PreValidationGraph;
import com.yourname.validator.model.OrderTransaction;
import com.yourname.validator.model.ValidationResult;
import com.yourname.validator.model.enums.FeeType;
import com.yourname.validator.model.enums.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/validate")
@RequiredArgsConstructor
public class ValidatorController {
    private final PreValidationGraph graph;

    @PostMapping
    public ValidationResult validate(@RequestBody OrderTransaction tx) {
        return graph.execute(tx);
    }

    @GetMapping("/test-pass")
    public ValidationResult testPass() {
        return graph.execute(OrderTransaction.builder()
                .transactionId("TXN-001").event("ORDER_PAYMENT")
                .amount(new BigDecimal("100.00")).feeType(FeeType.ITEM_PRICE)
                .orderStatus(OrderStatus.PAID).platformCode("APP").createTime(LocalDateTime.now()).build());
    }

    @GetMapping("/test-fail")
    public ValidationResult testFail() {
        return graph.execute(OrderTransaction.builder()
                .transactionId("TXN-002").event("ORDER_PAYMENT")
                .amount(new BigDecimal("50.00")).feeType(FeeType.LATE_FEE)
                .orderStatus(OrderStatus.PAID).platformCode("APP").createTime(LocalDateTime.now()).build());
    }
}
