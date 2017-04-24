package com.ymatou.productquery.domain.model;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Property;

import java.util.Date;
import java.util.List;

/**
 * Created by zhangyong on 2017/4/6.
 */
public class Catalogs {
    @Property("spid")
    private String productId;//	商品编号

    @Property("cid")
    private String catalogId;//	规格编号

    @Property("sid")
    private int sellerId;//	买手编号

    @Property("sku")
    private String sku;//	sku

    @Property("presale")
    private boolean isPriceSale;//	是否预售规格

    @Property("earnest")
    private Double earnest;//	定金

    @Property("price")
    private Double price;//	原价

    @Property("newp")
    private Double newPrice;//	新客价

    @Property("vip")
    private Double vipPrice;//	VIP价

    @Property("stock")
    private int stock;//	规格库存

    private List<PropertyInfo> props;//	规格属性列表

    @Property("updatetime")
    private Date updatetime;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(String catalogId) {
        this.catalogId = catalogId;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public boolean isPriceSale() {
        return isPriceSale;
    }

    public void setPriceSale(boolean priceSale) {
        this.isPriceSale = priceSale;
    }

    public Double getEarnest() {
        return earnest;
    }

    public void setEarnest(Double earnest) {
        this.earnest = earnest;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(Double newPrice) {
        this.newPrice = newPrice;
    }

    public Double getVipPrice() {
        return vipPrice;
    }

    public void setVipPrice(Double vipPrice) {
        this.vipPrice = vipPrice;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public List<PropertyInfo> getProps() {
        return props;
    }

    public void setProps(List<PropertyInfo> props) {
        this.props = props;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }
}
