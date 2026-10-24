package com.yourname.validator.rule;

import com.yourname.validator.model.OrderTransaction;
import com.yourname.validator.model.enums.FeeType;
import com.yourname.validator.model.enums.OrderStatus;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Component
public class DeterministicRuleEngine {

    private static final List<FeeType> OVERDUE_TYPES = Arrays.asList(
            FeeType.LATE_FEE, FeeType.PENALTY
    );

    public boolean checkAmount(OrderTransaction tx) {
        if (tx.getAmount() == null) return false;
        return tx.getAmount().compareTo(BigDecimal.ZERO) >= 0 && tx.getAmount().scale() <= 2;
    }

    public boolean checkBasicFields(OrderTransaction tx) {
        return tx.getTransactionId() != null && tx.getEvent() != null
                && tx.getPlatformCode() != null && tx.getCreateTime() != null;
    }

    public boolean checkEnumValidity(OrderTransaction tx) {
        return tx.getFeeType() != null && tx.getOrderStatus() != null;
    }

    public boolean checkStatusFeeTypeCombination(OrderTransaction tx) {
        if (tx.getOrderStatus() == OrderStatus.PAID) {
            return !OVERDUE_TYPES.contains(tx.getFeeType());
        }
        if (tx.getOrderStatus() == OrderStatus.REFUNDED) {
            return tx.getFeeType() == FeeType.ITEM_PRICE;
        }
        return true;
    }

    public boolean verifyAll(OrderTransaction tx) {
        return checkBasicFields(tx) && checkAmount(tx)
                && checkEnumValidity(tx) && checkStatusFeeTypeCombination(tx);
    }

    public String firstFailure(OrderTransaction tx) {
        if (!checkBasicFields(tx)) return "Missing basic fields";
        if (!checkAmount(tx)) return "Invalid amount or precision";
        if (!checkEnumValidity(tx)) return "Invalid enum values";
        if (!checkStatusFeeTypeCombination(tx)) return "Conflict between order status and fee type";
        return null;
    }
}
