package com.ymatou.productquery.domain.model;

/**
 * Created by zhangyong on 2017/4/11.
 */
public class StockInfo {
    private int ProductInActivityId;
    private String Reason;
    private int Stock;
    private int StockType;

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

    public int getStock() {
        return Stock;
    }

    public void setStock(int stock) {
        Stock = stock;
    }

    public int getStockType() {
        return StockType;
    }

    public void setStockType(int stockType) {
        StockType = stockType;
    }
}
