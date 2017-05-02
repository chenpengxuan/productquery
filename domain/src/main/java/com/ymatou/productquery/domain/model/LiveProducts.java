package com.ymatou.productquery.domain.model;

import com.ymatou.productquery.domain.model.cache.CacheLiveProductInfo;
import com.ymatou.productquery.infrastructure.util.Utils;
import org.mongodb.morphia.annotations.Property;

import java.util.Date;

/**
 * Created by zhangyong on 2017/4/6.
 */
public class LiveProducts{
    @Property("spid")
    private String productId;
    @Property("updatetime")
    private Date updateTime;
    @Property("lid")
    private int liveId;
    @Property("sid")
    private int sellerId;
    @Property("start")
    private Date startTime;
    @Property("end")
    private Date endTime;
    @Property("add")
    private Date addTime;
    @Property("brand")
    private String brandName;
    @Property("ebrand")
    private String brandEnName;
    @Property("bid")
    private int brandId;
    @Property("mcatid")
    private int masterCategoryId;
    @Property("mcatname")
    private String masterCategoryName;
    @Property("scatid")
    private int categoryId;
    @Property("scatname")
    private String categoryName;
    @Property("tcatid")
    private int thirdCategoryId;
    @Property("tcatname")
    private String thirdCategoryName;
    @Property("status")
    private int sellStatus;
    @Property("istop")
    private boolean istop;
    @Property("sort")
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

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 数据转换器
     * @return
     */
    public CacheLiveProductInfo convertDtoToCacheData(){
        CacheLiveProductInfo cacheLiveProductInfo = new CacheLiveProductInfo();
        Utils.copyProperties(cacheLiveProductInfo,this);
        return cacheLiveProductInfo;
    }
}
