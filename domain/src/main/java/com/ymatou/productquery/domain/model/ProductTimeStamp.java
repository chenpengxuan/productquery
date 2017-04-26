package com.ymatou.productquery.domain.model;

import org.mongodb.morphia.annotations.Property;

import java.util.Date;

/**
 * Created by zhangyong on 2017/4/21.
 */
public class ProductTimeStamp {
    /**
     * 商品id
     */
    @Property("spid")
    private String productId;

    @Property("cut")
    private Date catalogUpdateTime;

    @Property("sut")
    private Date productUpdateTime;

    @Property("aut")
    private Date activityUpdateTime;

    @Property("lut")
    private Date liveUpdateTime;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Date getCatalogUpdateTime() {
        return catalogUpdateTime;
    }

    public void setCatalogUpdateTime(Date catalogUpdateTime) {
        this.catalogUpdateTime = catalogUpdateTime;
    }

    public Date getProductUpdateTime() {
        return productUpdateTime;
    }

    public void setProductUpdateTime(Date productUpdateTime) {
        this.productUpdateTime = productUpdateTime;
    }

    public Date getActivityUpdateTime() {
        return activityUpdateTime;
    }

    public void setActivityUpdateTime(Date activityUpdateTime) {
        this.activityUpdateTime = activityUpdateTime;
    }

    public Date getLiveUpdateTime() {
        return liveUpdateTime;
    }

    public void setLiveUpdateTime(Date liveUpdateTime) {
        this.liveUpdateTime = liveUpdateTime;
    }
}
