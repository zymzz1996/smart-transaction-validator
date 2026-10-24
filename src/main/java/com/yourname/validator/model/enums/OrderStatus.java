package com.yourname.validator.model.enums;

import lombok.Getter;

@Getter
public enum OrderStatus {
    PAID("已支付"),
    OVERDUE("超时未支付"),
    DISPUTED("争议中"),
    REFUNDED("已退款");

    private final String desc;

    OrderStatus(String desc) {
        this.desc = desc;
    }
}
