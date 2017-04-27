package com.ymatou.productquery.domain.model.cache;

import java.util.Date;

/**
 * Created by chenpengxuan on 2017/4/26.
 */
public class BaseCacheInfo {
    /**
     * 商品编号
     */
    protected String productId;

    /**
     * 更新日期
     */
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
