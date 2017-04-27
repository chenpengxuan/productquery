package com.ymatou.productquery.domain.model;

/**
 * Created by zhangyong on 2017/4/11.
 */
public enum PriceTypeEnum {
    QuotePrice(0),
    NewCustomerPrice(1),
    VipPrice(2),
    YmtActivityPrice(3),
    YmtNewCustomerPrice(4),
    EarnestPrice(5);

    private int code;

    PriceTypeEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
