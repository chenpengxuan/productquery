package com.ymatou.productquery.domain.model;

/**
 * Created by zhangyong on 2017/4/6.
 */
public class ActivityCatalogInfo {
    private String cid;
    private int stock;
    private double price;

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
