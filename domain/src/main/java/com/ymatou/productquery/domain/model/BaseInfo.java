package com.ymatou.productquery.domain.model;

import org.mongodb.morphia.annotations.Property;

import java.util.Date;

/**
 * Created by chenpengxuan on 2017/4/26.
 */
public class BaseInfo {
    @Property("spid")
    protected String productId;//	商品编号

    @Property("updatetime")
    protected Date updateTime;

    public Date getUpdateTime() {
        return updateTime != null ? (Date)updateTime.clone():null;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime != null ? (Date)updateTime.clone() : null;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
