package com.ymatou.productquery.model.res;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;

/**
 * Created by zhangyong on 2017/4/20.
 */
public class ProductActivityDto {

    // 商品编号
    @JsonProperty("ProductId")
    private String productId;


    // 活动编号
    @JsonProperty("ActivityId")
    private int activityId;


    // 商品活动编号
    @JsonProperty("ProductInActivityId")
    private int productInActivityId;


    // 活动名称
    @JsonProperty("ActivityName")
    private String activityName;


    // 活动开始时间
    @JsonProperty("ActivityStartTime")
    private Date activityStartTime;


    // 活动结束时间
    @JsonProperty("ActivityEndTime")
    private Date activityEndTime;


    // 活动中商品的开始时间
    @JsonProperty("BeginTimeOfProductInActivity")
    private Date beginTimeOfProductInActivity;


    // 活动中商品的结束时间
    @JsonProperty("EndTimeOfProductInActivity")
    private Date endTimeOfProductInActivity;


    // 市场价
    @JsonProperty("MarketPrice")
    private String marketPrice;


    // 活动限购
    @JsonProperty("ActivityLimit")
    private int activityLimit;


    // 活动商品限购
    @JsonProperty("ActivityProductLimit")
    private int activityProductLimit;


    // 活动促销类型
    @JsonProperty("PromotionType")
    private PromotionTypeEnum promotionType;


    // 活动促销值
    @JsonProperty("Promotion")
    private double promotion;


    // 活动商品规格
    @JsonProperty("CatalogList")
    private List<String> catalogList;


    // 是否交易隔离（活动策略）
    @JsonProperty("IsTradeIsolation")
    private boolean isTradeIsolation;


    // 是否支持部分规格报名（活动策略）
    @JsonProperty("IsPartCatalogs")
    private boolean isPartCatalogs;


    // 是否仅限码头新人（活动策略）
    @JsonProperty("IsNewBuyer")
    private boolean isNewBuyer;


    // 最低活动价
    @JsonProperty("MinActivityPrice")
    private double minActivityPrice;


    // 最高活动价
    @JsonProperty("MaxActivityPrice")
    private double maxActivityPrice;

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

    public int getProductInActivityId() {
        return productInActivityId;
    }

    public void setProductInActivityId(int productInActivityId) {
        this.productInActivityId = productInActivityId;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public Date getActivityStartTime() {
        return activityStartTime;
    }

    public void setActivityStartTime(Date activityStartTime) {
        this.activityStartTime = activityStartTime;
    }

    public Date getActivityEndTime() {
        return activityEndTime;
    }

    public void setActivityEndTime(Date activityEndTime) {
        this.activityEndTime = activityEndTime;
    }

    public Date getBeginTimeOfProductInActivity() {
        return beginTimeOfProductInActivity;
    }

    public void setBeginTimeOfProductInActivity(Date beginTimeOfProductInActivity) {
        this.beginTimeOfProductInActivity = beginTimeOfProductInActivity;
    }

    public Date getEndTimeOfProductInActivity() {
        return endTimeOfProductInActivity;
    }

    public void setEndTimeOfProductInActivity(Date endTimeOfProductInActivity) {
        this.endTimeOfProductInActivity = endTimeOfProductInActivity;
    }

    public String getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(String marketPrice) {
        this.marketPrice = marketPrice;
    }

    public int getActivityLimit() {
        return activityLimit;
    }

    public void setActivityLimit(int activityLimit) {
        this.activityLimit = activityLimit;
    }

    public int getActivityProductLimit() {
        return activityProductLimit;
    }

    public void setActivityProductLimit(int activityProductLimit) {
        this.activityProductLimit = activityProductLimit;
    }

    public PromotionTypeEnum getPromotionType() {
        return promotionType;
    }

    public void setPromotionType(PromotionTypeEnum promotionType) {
        this.promotionType = promotionType;
    }

    public double getPromotion() {
        return promotion;
    }

    public void setPromotion(double promotion) {
        this.promotion = promotion;
    }

    public List<String> getCatalogList() {
        return catalogList;
    }

    public void setCatalogList(List<String> catalogList) {
        this.catalogList = catalogList;
    }

    public boolean isTradeIsolation() {
        return isTradeIsolation;
    }

    public void setTradeIsolation(boolean tradeIsolation) {
        isTradeIsolation = tradeIsolation;
    }

    public boolean isPartCatalogs() {
        return isPartCatalogs;
    }

    public void setPartCatalogs(boolean partCatalogs) {
        isPartCatalogs = partCatalogs;
    }

    public boolean isNewBuyer() {
        return isNewBuyer;
    }

    public void setNewBuyer(boolean newBuyer) {
        isNewBuyer = newBuyer;
    }

    public double getMinActivityPrice() {
        return minActivityPrice;
    }

    public void setMinActivityPrice(double minActivityPrice) {
        this.minActivityPrice = minActivityPrice;
    }

    public double getMaxActivityPrice() {
        return maxActivityPrice;
    }

    public void setMaxActivityPrice(double maxActivityPrice) {
        this.maxActivityPrice = maxActivityPrice;
    }
}
