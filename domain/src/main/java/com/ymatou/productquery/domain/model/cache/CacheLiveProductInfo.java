package com.ymatou.productquery.domain.model.cache;

import com.ymatou.productquery.domain.model.LiveProducts;
import com.ymatou.productquery.infrastructure.util.Utils;

import java.util.Date;

/**
 * 缓存直播信息
 * Created by chenpengxuan on 2017/4/26.
 */
public class CacheLiveProductInfo extends BaseCacheInfo{
    /**
     * 直播id
     */
    private int liveId;

    /**
     * 买手id
     */
    private int sellerId;

    /**
     * 直播商品开始时间
     */
    private Date startTime;

    /**
     * 直播商品结束时间
     */
    private Date endTime;

    /**
     * 直播商品添加时间
     */
    private Date addTime;

    /**
     * 直播商品品牌名称
     */
    private String brandName;

    /**
     * 直播商品英文名称
     */
    private String brandEnName;

    /**
     * 直播商品品牌id
     */
    private int brandId;

    /**
     * 一级类目id
     */
    private int masterCategoryId;

    /**
     * 一级类目名称
     */
    private String masterCategoryName;

    /**
     * 二级类目id
     */
    private int categoryId;

    /**
     * 二级类目名称
     */
    private String categoryName;

    /**
     * 三级类目id
     */
    private int thirdCategoryId;

    /**
     * 三级类目名称
     */
    private String thirdCategoryName;

    /**
     * 售卖状态
     */
    private int sellStatus;

    /**
     * 是否设置置顶
     */
    private boolean istop;

    /**
     * 直播内商品排序
     */
    private int readyPutawayProductSort;

    public int getLiveId() {
        return liveId;
    }

    public void setLiveId(int liveId) {
        this.liveId = liveId;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
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
        return masterCategoryName;
    }

    public void setMasterCategoryName(String masterCategoryName) {
        this.masterCategoryName = masterCategoryName;
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

    public boolean istop() {
        return istop;
    }

    public void setIstop(boolean istop) {
        this.istop = istop;
    }

    public int getReadyPutawayProductSort() {
        return readyPutawayProductSort;
    }

    public void setReadyPutawayProductSort(int readyPutawayProductSort) {
        this.readyPutawayProductSort = readyPutawayProductSort;
    }

    /**
     * 数据转换器
     * @return
     */
    public LiveProducts convertCacheDataToDto(){
        LiveProducts liveProducts = new LiveProducts();
        Utils.copyProperties(liveProducts,this);
        return liveProducts;
    }
}
