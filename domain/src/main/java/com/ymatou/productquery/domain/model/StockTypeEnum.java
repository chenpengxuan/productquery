package com.ymatou.productquery.domain.model;

/**
 * Created by zhangyong on 2017/4/11.
 */
public enum StockTypeEnum {
    CatalogStock(0),
    ActivityStock(1);

    private int code;

    StockTypeEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
