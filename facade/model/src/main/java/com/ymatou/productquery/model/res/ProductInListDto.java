package com.ymatou.productquery.model.res;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Created by zhujinfeng on 2017/4/27.
 */
public class ProductInListDto {

    // 商品数字编号
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

    // 商品首图
    @JsonProperty("MainPic")
    private String mainPic;

    // 有效开始时间（年月日时分秒）
    @JsonProperty("ValidStart")
    private Date validStart;

    // 有效结束时间（年月日时分秒）
    @JsonProperty("ValidEnd")
    private Date validEnd;

    // 最低价（原价）
    @JsonProperty("MinPrice")
    private double minPrice;

    // 最高价（原价）
    @JsonProperty("MaxPrice")
    private double maxPrice;

    // 关联的直播编号
    @JsonProperty("LiveId")
    private int liveId;

    // 关联的活动编号
    @JsonProperty("ActivityId")
    private int activityId;

    // 卖家用户编号
    @JsonProperty("SellerId")
    private int sellerId;

    // 卖家用户账号
    @JsonProperty("SellerName")
    private String sellerName;

    // 商品所属国家
    @JsonProperty("CountryId")
    private int countryId;

    // 库存（活动库存 & 规格库存）
    @JsonProperty("Stock")
    private int stock;

    // 交税方（0. 卖家交税，1. 买家交税）
    @JsonProperty("TariffType")
    private int tariffType;

    // 是否包邮
    @JsonProperty("IsFreeShipping")
    private boolean isFreeShipping;

    // 发货类型（1.国内、2.海外直邮、3.贝海直邮、4.商家保税、5.贝海保税、6.海外认证直邮、7.海外拼邮）
    @JsonProperty("DeliveryMethod")
    private int deliveryMethod;

    // 售卖状态
    @JsonProperty("Status")
    private int status;

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

    // 是否有预订规格
    @JsonProperty("IsAnyPreSale")
    private boolean isAnyPreSale;

    // 是否全部为预订规格
    @JsonProperty("IsAllPreSale")
    private boolean isAllPreSale;

    // 是否PSP商品
    @JsonProperty("IsPspProduct")
    private boolean isPspProduct;

    // 是否直营商品
    @JsonProperty("OwnProduct")
    private boolean ownProduct;

    // 活动市场价
    @JsonProperty("ActivityMarketPrice")
    private double marketPrice;


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

    public String getMainPic() {
        return mainPic;
    }

    public void setMainPic(String mainPic) {
        this.mainPic = mainPic;
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

    public double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public int getLiveId() {
        return liveId;
    }

    public void setLiveId(int liveId) {
        this.liveId = liveId;
    }

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
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

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getTariffType() {
        return tariffType;
    }

    public void setTariffType(int tariffType) {
        this.tariffType = tariffType;
    }

    public boolean getIsFreeShipping() {
        return isFreeShipping;
    }

    public void isFreeShipping(boolean isFreeShipping) {
        this.isFreeShipping = isFreeShipping;
    }

    public int getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(int deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean getIsNewProduct() {
        return isNewProduct;
    }

    public void isNewProduct(boolean isNewProduct) {
        this.isNewProduct = isNewProduct;
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

    public boolean getIsHotRecmd() {
        return isHotRecmd;
    }

    public void setIsHotRecmd(boolean isHotRecmd) {
        this.isHotRecmd = isHotRecmd;
    }

    public boolean getIsAnyPreSale() {
        return isAnyPreSale;
    }

    public void isAnyPreSale(boolean isAnyPreSale) {
        this.isAnyPreSale = isAnyPreSale;
    }

    public boolean getIsAllPreSale() {
        return isAllPreSale;
    }

    public void isAllPreSale(boolean isAllPreSale) {
        this.isAllPreSale = isAllPreSale;
    }

    public boolean getIsPspProduct() {
        return isPspProduct;
    }

    public void isPspProduct(boolean isPspProduct) {
        this.isPspProduct = isPspProduct;
    }

    public boolean getOwnProduct() {
        return ownProduct;
    }

    public void setOwnProduct(boolean ownProduct) {
        this.ownProduct = ownProduct;
    }

    public double getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(double marketPrice) {
        this.marketPrice = marketPrice;
    }
}
