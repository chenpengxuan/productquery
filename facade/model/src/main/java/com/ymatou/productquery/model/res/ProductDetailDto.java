package com.ymatou.productquery.model.res;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;

/**
 * Created by zhangyong on 2017/4/20.
 */
public class ProductDetailDto {

    // 商品编号（int）
    @JsonProperty("ProdId")
    private int prodId;

    // 商品编号
    @JsonProperty("ProductId")
    private String productId;

    // 快照版本
    @JsonProperty("Version")
    private String version;

    // 商品名称
    @JsonProperty("Title")
    private String title;

    // 简介
    @JsonProperty("Introduction")
    private String introduction;

    // 介绍图片列表
    @JsonProperty("PicList")
    private List<String> picList;

    // 有效开始时间（年月日时分秒）
    @JsonProperty("ValidStart")
    private Date validStart;

    // 有效结束时间（年月日时分秒）
    @JsonProperty("ValidEnd")
    private Date validEnd;

    // 限购数量(0 为不限购)
    @JsonProperty("LimitNum")
    private int limitNum;

    // 限购开始时间
    @JsonProperty("LimitNumStartTime")
    private Date limitNumStartTime;

    // 卖家用户编号
    @JsonProperty("SellerId")
    private int sellerId;

    // 卖家用户账号
    @JsonProperty("SellerName")
    private String sellerName;

    // 商品品牌编号
    @JsonProperty("BrandId")
    private int brandId;


    // 商品品牌名称（中）
    @JsonProperty("BrandName")
    private String brandName;


    // 商品品牌名称（英）
    @JsonProperty("BrandEnName")
    private String brandEnName;


    // 一级分类编号
    @JsonProperty("MasterCategoryId")
    private int masterCategoryId;


    // 一级分类名称
    @JsonProperty("MasterCategoryName")
    private String masterCategoryName;


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


    // 备货状态（0-现货，1-代购，2-FBX商品）
    @JsonProperty("CatalogType")
    private int catalogType;


    // 发货类型（1.国内、2.海外直邮、3.贝海直邮、4.商家保税、5.贝海保税、6.海外认证直邮、7.海外拼邮）
    @JsonProperty("DeliveryMethod")
    private int deliveryMethod;


    // 保税区（1.广州，2.宁波，3.杭州，4.郑州，5.天津，6.重庆，7.深圳，8.上海）
    @JsonProperty("BondedArea")
    private int bondedArea;


    // 重量
    @JsonProperty("Weight")
    private double weight;


    // 交税方（0. 卖家交税，1. 买家交税）
    @JsonProperty("TariffType")
    private int tariffType;


    // 是否包邮
    @JsonProperty("IsFreeShipping")
    private boolean isFreeShipping;


    // 商品所属国家
    @JsonProperty("CountryId")
    private int countryId;


    // 商品创建时间
    @JsonProperty("AddTime")
    private Date addTime;


    // 售卖状态
    @JsonProperty("Status")
    private ProductStatusEnum status;


    // 是否新结构化描述
    @JsonProperty("HasTextDescription")
    private boolean hasTextDescription;


    // 本土退货 1，官方本土退货 2
    @JsonProperty("LocalReturn")
    private int localReturn;


    // 砍单风险
    @JsonProperty("NoticeRisk")
    private boolean noticeRisk;


    // FBX商品的商品备案号
    @JsonProperty("ProductCode")
    private String productCode;


    // 七天无理由
    @JsonProperty("NoReasonReturn")
    private boolean noReasonReturn;


    // 是否新品
    @JsonProperty("IsNewProduct")
    private boolean isNewProduct;


    // 新品开始时间
    @JsonProperty("NewStartTime")
    private Date newStartTime;


    // 新品结束时间
    @JsonProperty("NewEndTime")
    private Date newEndTime;


    // 是否买手热推
    @JsonProperty("IsHotRecmd")
    private boolean isHotRecmd;


    // 是否PSP商品
    @JsonProperty("IsPspProduct")
    private boolean isPspProduct;


    // 商品等级
    @JsonProperty("Grade")
    private String grade;


    // 尺码对照表
    @JsonProperty("SizePicList")
    private List<String> sizePicList;


    // 商品规格
    @JsonProperty("CatalogList")
    private List<CatalogDto> catalogList;


    // 活动商品
    @JsonProperty("ProductActivity")
    private ProductActivityDto productActivity;


    // 下一场活动
    @JsonProperty("NextActivity")
    private ProductActivityDto nextActivity;


    // 直播商品
    @JsonProperty("LiveProduct")
    private ProductLiveDto liveProduct;

    public int getProdId() {
        return prodId;
    }

    public void setProdId(int prodId) {
        this.prodId = prodId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
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

    public int getLimitNum() {
        return limitNum;
    }

    public void setLimitNum(int limitNum) {
        this.limitNum = limitNum;
    }

    public Date getLimitNumStartTime() {
        return limitNumStartTime;
    }

    public void setLimitNumStartTime(Date limitNumStartTime) {
        this.limitNumStartTime = limitNumStartTime;
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

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getTariffType() {
        return tariffType;
    }

    public void setTariffType(int tariffType) {
        this.tariffType = tariffType;
    }

    public boolean isFreeShipping() {
        return isFreeShipping;
    }

    public void setFreeShipping(boolean freeShipping) {
        isFreeShipping = freeShipping;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public ProductStatusEnum getStatus() {
        return status;
    }

    public void setStatus(ProductStatusEnum status) {
        this.status = status;
    }

    public boolean isHasTextDescription() {
        return hasTextDescription;
    }

    public void setHasTextDescription(boolean hasTextDescription) {
        this.hasTextDescription = hasTextDescription;
    }

    public int getLocalReturn() {
        return localReturn;
    }

    public void setLocalReturn(int localReturn) {
        this.localReturn = localReturn;
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

    public boolean isNoReasonReturn() {
        return noReasonReturn;
    }

    public void setNoReasonReturn(boolean noReasonReturn) {
        this.noReasonReturn = noReasonReturn;
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

    public boolean isHotRecmd() {
        return isHotRecmd;
    }

    public void setHotRecmd(boolean hotRecmd) {
        isHotRecmd = hotRecmd;
    }

    public boolean isPspProduct() {
        return isPspProduct;
    }

    public void setPspProduct(boolean pspProduct) {
        isPspProduct = pspProduct;
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

    public List<CatalogDto> getCatalogList() {
        return catalogList;
    }

    public void setCatalogList(List<CatalogDto> catalogList) {
        this.catalogList = catalogList;
    }

    public ProductActivityDto getProductActivity() {
        return productActivity;
    }

    public void setProductActivity(ProductActivityDto productActivity) {
        this.productActivity = productActivity;
    }

    public ProductActivityDto getNextActivity() {
        return nextActivity;
    }

    public void setNextActivity(ProductActivityDto nextActivity) {
        this.nextActivity = nextActivity;
    }

    public ProductLiveDto getLiveProduct() {
        return liveProduct;
    }

    public void setLiveProduct(ProductLiveDto liveProduct) {
        this.liveProduct = liveProduct;
    }
}
