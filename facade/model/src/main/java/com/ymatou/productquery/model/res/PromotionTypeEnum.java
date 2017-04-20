package com.ymatou.productquery.model.res;

/**
 * Created by zhangyong on 2017/4/20.
 */
public enum PromotionTypeEnum {
    // 折扣
    DiscountRate(1),
    // 直降
    DiscountPrice(2),
    // 折后价
    AfterDiscountPrice(3);

    private int code;

    PromotionTypeEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
