package com.ymatou.productquery.domain.model;

/**
 * Created by zhangyong on 2017/4/11.
 */
public class PriceInfo {
    private double Price;
    private int PriceType;
    private int ProductInActivityId;
    private String Reason;

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public int getPriceType() {
        return PriceType;
    }

    public void setPriceType(int priceType) {
        PriceType = priceType;
    }

    public int getProductInActivityId() {
        return ProductInActivityId;
    }

    public void setProductInActivityId(int productInActivityId) {
        ProductInActivityId = productInActivityId;
    }

    public String getReason() {
        return Reason;
    }

    public void setReason(String reason) {
        Reason = reason;
    }
}
