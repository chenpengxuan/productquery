package com.ymatou.productquery.model.res;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by zhangyong on 2017/4/20.
 */
public class CatalogDto {

    // 规格编号
    @JsonProperty("CatalogId")
    private String catalogId;

    // sku
    @JsonProperty("SKU")
    private String sku;

    // 该规格是否报名活动
    private boolean isInActivity;

    // 预订商品
    private boolean isPreSale;

    // 原价
    @JsonProperty("Price")
    private double price;

    // 买手新客价
    @JsonProperty("NewGuestPrice")
    private double newGuestPrice;

    // 买手VIP价
    @JsonProperty("VipPrice")
    private double vipPrice;

    // 商品规格库存量
    @JsonProperty("CatalogStock")
    private int catalogStock;

    // 活动库存
    @JsonProperty("ActivityStock")
    private int activityStock;

    // 活动价格
    @JsonProperty("ActivityPrice")
    private double activityPrice;

    // 规格属性
    @JsonProperty("PropertyList")
    private List<CatalogPropertyDto> propertyList;

    // 是否支持多物流
    private boolean isExtraDelivery;

    public String getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(String catalogId) {
        this.catalogId = catalogId;
    }

    public String getSku() {
        return sku == null ? "" : sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    @JsonProperty("IsInActivity")
    public boolean isInActivity() {
        return isInActivity;
    }

    public void setInActivity(boolean inActivity) {
        isInActivity = inActivity;
    }

    @JsonProperty("IsPreSale")
    public boolean isPreSale() {
        return isPreSale;
    }

    public void setPreSale(boolean preSale) {
        isPreSale = preSale;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getNewGuestPrice() {
        return newGuestPrice;
    }

    public void setNewGuestPrice(double newGuestPrice) {
        this.newGuestPrice = newGuestPrice;
    }

    public double getVipPrice() {
        return vipPrice;
    }

    public void setVipPrice(double vipPrice) {
        this.vipPrice = vipPrice;
    }

    public int getCatalogStock() {
        return catalogStock;
    }

    public void setCatalogStock(int catalogStock) {
        this.catalogStock = catalogStock;
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

    public List<CatalogPropertyDto> getPropertyList() {
        return propertyList;
    }

    public void setPropertyList(List<CatalogPropertyDto> propertyList) {
        this.propertyList = propertyList;
    }

    @JsonProperty("IsExtraDelivery")
    public boolean isExtraDelivery() {
        return isExtraDelivery;
    }

    public void setExtraDelivery(boolean extraDelivery) {
        isExtraDelivery = extraDelivery;
    }
}
