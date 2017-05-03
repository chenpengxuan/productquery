package com.ymatou.productquery.model.res;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Created by zhangyong on 2017/4/20.
 */
public class ProductLiveDto {

    // 商品编号
    @JsonProperty("ProductId")
    private String productId;


    // 直播编号
    @JsonProperty("LiveId")
    private int liveId;


    // 直播名称
    @JsonProperty("LiveName")
    private String liveName;


    // 买手编号
    @JsonProperty("SellerId")
    private int sellerId;


    // 评论数量
    @JsonProperty("CommentCount")
    private int commentCount;


    // 直播商品开始时间
    @JsonProperty("StartTime")
    private Date startTime;


    // 直播商品结束时间
    @JsonProperty("EndTime")
    private Date endTime;


    // 发布时间
    @JsonProperty("AddTime")
    private Date addTime;


    // 品牌名称
    @JsonProperty("BrandName")
    private String brandName;


    // 品牌英文名称
    @JsonProperty("BrandEnName")
    private String brandEnName;


    // 品牌编号
    @JsonProperty("BrandId")
    private int brandId;


    // 一级分类编号
    @JsonProperty("MasterCategoryId")
    private int masterCategoryId;


    // 一级分类名称
    @JsonProperty("masterCategoryName")
    private String MasterCategoryName;


    // 二级分类编号
    @JsonProperty("CategoryId")
    private int categoryId;


    // 二级分类名称
    @JsonProperty("CategoryName")
    private String categoryName;


    // 三级分类编号
    @JsonProperty("ThirdCategoryId")
    private int thirdCategoryId;


    // 三级分类名称
    @JsonProperty("ThirdCategoryName")
    private String thirdCategoryName;


    // 售卖状态
    @JsonProperty("SellStatus")
    private int sellStatus;


    // 是否置顶商品
    private boolean isTop;


    // 商品在直播中的排序
    @JsonProperty("ReadyPutawayProductSort")
    private int readyPutawayProductSort;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getLiveId() {
        return liveId;
    }

    public void setLiveId(int liveId) {
        this.liveId = liveId;
    }

    public String getLiveName() {
        return liveName;
    }

    public void setLiveName(String liveName) {
        this.liveName = liveName;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
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

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getBrandEnName() {
        return brandEnName;
    }

    public void setBrandEnName(String brandEnName) {
        this.brandEnName = brandEnName;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public int getMasterCategoryId() {
        return masterCategoryId;
    }

    public void setMasterCategoryId(int masterCategoryId) {
        this.masterCategoryId = masterCategoryId;
    }

    public String getMasterCategoryName() {
        return MasterCategoryName;
    }

    public void setMasterCategoryName(String masterCategoryName) {
        MasterCategoryName = masterCategoryName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getThirdCategoryId() {
        return thirdCategoryId;
    }

    public void setThirdCategoryId(int thirdCategoryId) {
        this.thirdCategoryId = thirdCategoryId;
    }

    public String getThirdCategoryName() {
        return thirdCategoryName;
    }

    public void setThirdCategoryName(String thirdCategoryName) {
        this.thirdCategoryName = thirdCategoryName;
    }

    public int getSellStatus() {
        return sellStatus;
    }

    public void setSellStatus(int sellStatus) {
        this.sellStatus = sellStatus;
    }

    @JsonProperty("IsTop")
    public boolean isTop() {
        return isTop;
    }

    public void setTop(boolean top) {
        isTop = top;
    }

    public int getReadyPutawayProductSort() {
        return readyPutawayProductSort;
    }

    public void setReadyPutawayProductSort(int readyPutawayProductSort) {
        this.readyPutawayProductSort = readyPutawayProductSort;
    }
}
