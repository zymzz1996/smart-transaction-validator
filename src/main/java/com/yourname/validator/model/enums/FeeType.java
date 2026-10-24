package com.yourname.validator.model.enums;

import lombok.Getter;

@Getter
public enum FeeType {
    ITEM_PRICE("商品价格"),
    SHIPPING_FEE("运费"),
    TAX_FEE("税费"),
    DISCOUNT("折扣"),
    LATE_FEE("滞纳金"),
    PENALTY("违约金");

    private final String desc;

    FeeType(String desc) {
        this.desc = desc;
    }
}
