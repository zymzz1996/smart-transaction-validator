package com.yourname.validator.model;

import com.yourname.validator.model.enums.FeeType;
import com.yourname.validator.model.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderTransaction {
    private String transactionId;
    private String orderId;
    private String event;
    private BigDecimal amount;
    private FeeType feeType;
    private OrderStatus orderStatus;
    private String platformCode;
    private LocalDateTime createTime;
}
