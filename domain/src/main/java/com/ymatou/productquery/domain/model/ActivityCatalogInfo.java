package com.ymatou.productquery.domain.model;

import org.mongodb.morphia.annotations.Property;

/**
 * Created by zhangyong on 2017/4/6.
 */
public class ActivityCatalogInfo {
    @Property("cid")
    private String catalogId;
    @Property("stock")
    private int activityStock;
    @Property("price")
    private double activityPrice;

    public String getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(String catalogId) {
        this.catalogId = catalogId;
    }

    public int getActivityStock() {
        return activityStock;
    }

    public void setActivityStock(int activityStock) {
        this.activityStock = activityStock;
    }

    public double getActivityPrice() {
        return activityPrice;
    }

    public void setActivityPrice(double activityPrice) {
        this.activityPrice = activityPrice;
    }
}
