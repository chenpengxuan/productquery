package com.ymatou.productquery.domain.model;


import com.ymatou.productquery.domain.model.cache.CacheProductInfo;
import com.ymatou.productquery.infrastructure.util.Utils;
import org.mongodb.morphia.annotations.Property;

import java.util.Date;
import java.util.List;

/**
 * Created by zhangyong on 2017/4/6.
 */
public class Products {
    @Property("spid")
    private String productId;
    @Property("updatetime")
    private Date updateTime;
    @Property("ipid")
    private int prodId;//	商品数字编号
    @Property("title")
    private String title;//	商品标题
    @Property("intro")
    private String introduction;//	商品简介
    @Property("pics")
    private List<String> picList;//	商品主图列表
    @Property("start")
    private Date validStart;//	商品开始有效期
    @Property("end")
    private Date validEnd;//	商品结束有效期
    @Property("minp")
    private String minCatalogPrice;//	最低规格价(包括原价、新客价、vip价，如果为0也要填)数据示例：30.00,28.00,29.00
    @Property("maxp")
    private String maxCatalogPrice;//	最高规格价(包括原价、新客价、vip价，如果为0也要填)数据示例：30.00,28.00,29.00
    @Property("sid")
    private int sellerId;//	买手编号
    @Property("sname")
    private String sellerName;//	买手账号
    @Property("bid")
    private int brandId;//	品牌编号
    @Property("brand")
    private String brandName;//	品牌中文名称
    @Property("ebrand")
    private String brandEnName;//	品牌英文名称
    @Property("mcatid")
    private int masterCategoryId;//	一级分类编号
    @Property("mcatname")
    private String masterCategoryName;//	一级分类名称
    @Property("scatid")
    private int secondCategoryId;//	二级分类编号
    @Property("scatname")
    private String secondCategoryName;//	二级分类名称
    @Property("tcatid")
    private int thirdCategoryId;//	三级分类编号
    @Property("tcatname")
    private String thirdCategoryName;//	三级分类名称
    @Property("ctype")
    private int catalogType;//	备货方式
    @Property("deliv")
    private int deliveryMethod;//	发货方式
    @Property("bonded")
    private int bondedArea;//	保税区
    @Property("tariffy")
    private int tariffType;//	是否包税
    @Property("shipping")
    private int isFreeShipping;//	是否包邮
    @Property("country")
    private int countryId;//	国家编号
    @Property("newdesc")
    private boolean isNewDesc;//	是否新图文描述
    @Property("localr")
    private int localReturn;//	本土退货
    @Property("noreason")
    private boolean noReasonReturn;//	7天无理由退货
    @Property("action")
    private int action;//	商品状态
    @Property("addtime")
    private Date addTime;//	发布时间
    @Property("risk")
    private boolean noticeRisk;//	砍单风险提醒
    @Property("pcode")
    private String productCode;//	商品备案号（保税商品）
    @Property("isnew")
    private boolean isNewProduct;//	是否新品
    @Property("newstart")
    private Date newStartTime;//	新品有效开始时间
    @Property("newend")
    private Date newEndTime;//	新品有效结束时间
    @Property("istop")
    private boolean isTopProduct;//	是否买手热推商品
    @Property("ver")
    private String version;//	最新快照版本号
    @Property("ispsp")
    private boolean isPspProduct;//	是否psp商品 0不是 1是
    @Property("verupdate")
    private Date versionUpdateTime;//	快照更新时间
    @Property("grade")
    private String grade;//	商品等级
    @Property("sizepics")
    private List<String> sizePicList;//	尺码表
    @Property("own")
    private int ownProduct;//自营
    @Property("mdeliv")
    private int extraDeliveryType;//是否支持多物流
    @Property("mflight")
    private double extraDeliveryFee;

    /**
     * 规格列表
     */
    private List<Catalogs> catalogsList;

    public int getProdId() {
        return prodId;
    }

    public void setProdId(int prodId) {
        this.prodId = prodId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public List<String> getPicList() {
        return picList;
    }

    public void setPicList(List<String> picList) {
        this.picList = picList;
    }

    public Date getValidStart() {
        return validStart;
    }

    public void setValidStart(Date validStart) {
        this.validStart = validStart;
    }

    public Date getValidEnd() {
        return validEnd;
    }

    public void setValidEnd(Date validEnd) {
        this.validEnd = validEnd;
    }

    public String getMinCatalogPrice() {
        return minCatalogPrice;
    }

    public void setMinCatalogPrice(String minCatalogPrice) {
        this.minCatalogPrice = minCatalogPrice;
    }

    public String getMaxCatalogPrice() {
        return maxCatalogPrice;
    }

    public void setMaxCatalogPrice(String maxCatalogPrice) {
        this.maxCatalogPrice = maxCatalogPrice;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
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

    public int getSecondCategoryId() {
        return secondCategoryId;
    }

    public void setSecondCategoryId(int secondCategoryId) {
        this.secondCategoryId = secondCategoryId;
    }

    public String getSecondCategoryName() {
        return secondCategoryName;
    }

    public void setSecondCategoryName(String secondCategoryName) {
        this.secondCategoryName = secondCategoryName;
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

    public int getCatalogType() {
        return catalogType;
    }

    public void setCatalogType(int catalogType) {
        this.catalogType = catalogType;
    }

    public int getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(int deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public int getBondedArea() {
        return bondedArea;
    }

    public void setBondedArea(int bondedArea) {
        this.bondedArea = bondedArea;
    }

    public int getTariffType() {
        return tariffType;
    }

    public void setTariffType(int tariffType) {
        this.tariffType = tariffType;
    }


    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public boolean isNewDesc() {
        return isNewDesc;
    }

    public void setNewDesc(boolean newDesc) {
        isNewDesc = newDesc;
    }

    public int getLocalReturn() {
        return localReturn;
    }

    public void setLocalReturn(int localReturn) {
        this.localReturn = localReturn;
    }

    public boolean isNoReasonReturn() {
        return noReasonReturn;
    }

    public void setNoReasonReturn(boolean noReasonReturn) {
        this.noReasonReturn = noReasonReturn;
    }

    public int getIsFreeShipping() {
        return isFreeShipping;
    }

    public void setIsFreeShipping(int isFreeShipping) {
        this.isFreeShipping = isFreeShipping;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public boolean isNoticeRisk() {
        return noticeRisk;
    }

    public void setNoticeRisk(boolean noticeRisk) {
        this.noticeRisk = noticeRisk;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public boolean isNewProduct() {
        return isNewProduct;
    }

    public void setNewProduct(boolean newProduct) {
        isNewProduct = newProduct;
    }

    public Date getNewStartTime() {
        return newStartTime;
    }

    public void setNewStartTime(Date newStartTime) {
        this.newStartTime = newStartTime;
    }

    public Date getNewEndTime() {
        return newEndTime;
    }

    public void setNewEndTime(Date newEndTime) {
        this.newEndTime = newEndTime;
    }

    public boolean isTopProduct() {
        return isTopProduct;
    }

    public void setTopProduct(boolean topProduct) {
        isTopProduct = topProduct;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public boolean isPspProduct() {
        return isPspProduct;
    }

    public void setPspProduct(boolean pspProduct) {
        isPspProduct = pspProduct;
    }

    public Date getVersionUpdateTime() {
        return versionUpdateTime;
    }

    public void setVersionUpdateTime(Date versionUpdateTime) {
        this.versionUpdateTime = versionUpdateTime;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public List<String> getSizePicList() {
        return sizePicList;
    }

    public void setSizePicList(List<String> sizePicList) {
        this.sizePicList = sizePicList;
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

    public int getExtraDeliveryType() {
        return extraDeliveryType;
    }

    public void setExtraDeliveryType(int extraDeliveryType) {
        this.extraDeliveryType = extraDeliveryType;
    }

    public double getExtraDeliveryFee() {
        return extraDeliveryFee;
    }

    public void setExtraDeliveryFee(double extraDeliveryFee) {
        this.extraDeliveryFee = extraDeliveryFee;
    }

    public int getOwnProduct() {
        return ownProduct;
    }

    public void setOwnProduct(int ownProduct) {
        this.ownProduct = ownProduct;
    }

    public List<Catalogs> getCatalogsList() {
        return catalogsList;
    }

    public void setCatalogsList(List<Catalogs> catalogsList) {
        this.catalogsList = catalogsList;
    }

    /**
     * 数据转换器
     *
     * @return
     */
    public CacheProductInfo convertDtoToCacheData() {
        CacheProductInfo cacheProductInfo = new CacheProductInfo();
        Utils.copyProperties(cacheProductInfo, this);
        return cacheProductInfo;
    }
}
