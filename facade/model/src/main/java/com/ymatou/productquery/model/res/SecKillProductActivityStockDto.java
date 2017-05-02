package com.ymatou.productquery.model.res;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by zhujinfeng on 2017/5/2.
 */
public class SecKillProductActivityStockDto {

    @JsonProperty("ProductId")
    private String productId;

    @JsonProperty("ActivityId")
    private int activityId;

    @JsonProperty("ProductActivityId")
    private int productActivityId;

    @JsonProperty("CatalogId")
    private String catalogId;

    @JsonProperty("ActivityStock")
    private int activityStock;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public int getProductActivityId() {
        return productActivityId;
    }

    public void setProductActivityId(int productActivityId) {
        this.productActivityId = productActivityId;
    }

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
}
