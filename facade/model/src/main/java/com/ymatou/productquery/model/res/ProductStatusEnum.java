package com.ymatou.productquery.model.res;

/**
 * Created by zhangyong on 2017/4/10.
 */
public enum ProductStatusEnum {

    /**
     * 已删除
     */
    Deleted (-1),

    /**
     * 可用的
     */
    Available(0),

    /**
     * 不可用
     */
    Disable(2);

    private int code;
    ProductStatusEnum(int code) {
        this.code=code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
