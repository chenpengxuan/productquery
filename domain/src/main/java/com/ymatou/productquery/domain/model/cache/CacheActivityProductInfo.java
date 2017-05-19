package com.ymatou.productquery.domain.model.cache;

import com.ymatou.productquery.domain.model.ActivityCatalogInfo;
import com.ymatou.productquery.domain.model.ActivityProducts;
import com.ymatou.productquery.infrastructure.util.Utils;

import java.util.Date;
import java.util.List;

/**
 * Created by chenpengxuan on 2017/4/26.
 */
public class CacheActivityProductInfo extends BaseCacheInfo{
    /**
     * 活动id
     */
    private int activityId;

    /**
     * 活动名称
     */
    private String activityName;

    /**
     * 商品活动关联id
     */
    private int productInActivityId;

    /**
     * 活动商品开始时间
     */
    private Date startTime;

    /**
     * 活动商品结束时间
     */
    private Date endTime;

    /**
     * 活动商品市场价
     */
    private Double marketPrice;

    /**
     * 活动限购数
     */
    private int activityLimit;

    /**
     * 商品限购数
     */
    private int productLimit;

    /**
     * 是否交易隔离活动商品
     */
    private boolean isTradeIsolation;

    /**
     * 是否新客活动商品
     */
    private boolean isNewBuyer;

    /**
     *
     */
    private boolean isPartCatalogs;

    /**
     *
     */
    private List<ActivityCatalogInfo> catalogs;

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public int getProductInActivityId() {
        return productInActivityId;
    }

    public void setProductInActivityId(int productInActivityId) {
        this.productInActivityId = productInActivityId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Double getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(Double marketPrice) {
        this.marketPrice = marketPrice;
    }

    public int getActivityLimit() {
        return activityLimit;
    }

    public void setActivityLimit(int activityLimit) {
        this.activityLimit = activityLimit;
    }

    public int getProductLimit() {
        return productLimit;
    }

    public void setProductLimit(int productLimit) {
        this.productLimit = productLimit;
    }

    public boolean isTradeIsolation() {
        return isTradeIsolation;
    }

    public void setTradeIsolation(boolean tradeIsolation) {
        isTradeIsolation = tradeIsolation;
    }

    public boolean isNewBuyer() {
        return isNewBuyer;
    }

    public void setNewBuyer(boolean newBuyer) {
        isNewBuyer = newBuyer;
    }

    public boolean isPartCatalogs() {
        return isPartCatalogs;
    }

    public void setPartCatalogs(boolean partCatalogs) {
        isPartCatalogs = partCatalogs;
    }

    public List<ActivityCatalogInfo> getCatalogs() {
        return catalogs;
    }

    public void setCatalogs(List<ActivityCatalogInfo> catalogs) {
        this.catalogs = catalogs;
    }

    /**
     * 数据转换器
     * @return
     */
    public ActivityProducts convertCacheDataToDto(){
        ActivityProducts activityProducts = new ActivityProducts();
        Utils.copyProperties(activityProducts,this);
        return activityProducts;
    }
}
