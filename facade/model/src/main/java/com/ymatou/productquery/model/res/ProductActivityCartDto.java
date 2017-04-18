package com.ymatou.productquery.model.res;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;

/**
 * Created by zhangyong on 2017/4/10.
 */
/// <summary>
/// 商品活动
/// </summary>
public class ProductActivityCartDto
{
    /// <summary>
    /// 活动编号
    /// </summary>
    @JsonProperty("ActivityId")
    private int activityId;

    /// <summary>
    /// 活动名称
    /// </summary>
    @JsonProperty("ActivityName")
    private String activityName;

    /// <summary>
    /// 活动商品编号
    /// </summary>
    @JsonProperty("ProductInActivityId")
    private int productInActivityId;

    /// <summary>
    /// 商品在活动中的开始时间
    /// </summary>
    @JsonProperty("ProductActivityStartTime")
    private Date productActivityStartTime;

    /// <summary>
    /// 商品在活动中的结束时间
    /// </summary>
    @JsonProperty("ProductActivityEndTime")
    private Date productActivityEndTime;

    /// <summary>
    /// 活动的限购数量
    /// </summary>
    @JsonProperty("ActivityLimitNumber")
    private int activityLimitNumber;

    /// <summary>
    /// 活动商品的限购数量
    /// </summary>
    @JsonProperty("ProductActivityLimitNumber")
    private int productActivityLimitNumber;

    /// <summary>
    /// 促销类型
    /// </summary>
    @JsonProperty("PromotionType")
    private int promotionType;

    /// <summary>
    /// 活动规格列表
    /// </summary>
    @JsonProperty("ActivityCatalogList")
    private List<String> activityCatalogList;

    /// <summary>
    /// 是否仅限码头新人
    /// </summary>
    @JsonProperty("IsNewBuyer")
    private boolean newBuyer;

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

    public Date getProductActivityStartTime() {
        return productActivityStartTime;
    }

    public void setProductActivityStartTime(Date productActivityStartTime) {
        this.productActivityStartTime = productActivityStartTime;
    }

    public Date getProductActivityEndTime() {
        return productActivityEndTime;
    }

    public void setProductActivityEndTime(Date productActivityEndTime) {
        this.productActivityEndTime = productActivityEndTime;
    }

    public int getActivityLimitNumber() {
        return activityLimitNumber;
    }

    public void setActivityLimitNumber(int activityLimitNumber) {
        this.activityLimitNumber = activityLimitNumber;
    }

    public int getProductActivityLimitNumber() {
        return productActivityLimitNumber;
    }

    public void setProductActivityLimitNumber(int productActivityLimitNumber) {
        this.productActivityLimitNumber = productActivityLimitNumber;
    }

    public int getPromotionType() {
        return promotionType;
    }

    public void setPromotionType(int promotionType) {
        this.promotionType = promotionType;
    }

    public List<String> getActivityCatalogList() {
        return activityCatalogList;
    }

    public void setActivityCatalogList(List<String> activityCatalogList) {
        this.activityCatalogList = activityCatalogList;
    }

    public boolean isNewBuyer() {
        return newBuyer;
    }

    public void setNewBuyer(boolean newBuyer) {
        this.newBuyer = newBuyer;
    }
}
