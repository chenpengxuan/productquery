package com.ymatou.productquery.model.res;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;

/**
 * Created by zhangyong on 2017/4/10.
 */
public class ProductInCartDto {

    // 商品编号
    @JsonProperty("ProductId")
    private String productId;

    // 快照版本
    @JsonProperty("Version")
    private String version;

    // 规格编号
    @JsonProperty("CatalogId")
    private String catalogId;

    // 商品名称
    @JsonProperty("ProductName")
    private String productName;

    // 商品主要图片
    @JsonProperty("MainPicUrl")
    private String mainPicUrl;

    // 有效开始时间（年月日时分秒）
    @JsonProperty("ValidStart")
    private Date validStart;


    // 有效结束时间（年月日时分秒）
    @JsonProperty("ValidEnd")
    private Date validEnd;

    // 品牌编号
    @JsonProperty("BrandId")
    private int brandId;

    // 一级分类
    @JsonProperty("MasterCategoryId")
    private int masterCategoryId;

    // 二级分类
    @JsonProperty("CategoryId")
    private int categoryId;

    // 三级分类
    @JsonProperty("ThirdCategoryId")
    private int thirdCategoryId;

    // 买手用户编号
    @JsonProperty("SellerId")
    private int sellerId;

    // 买手用户账号
    @JsonProperty("SellerName")
    private String sellerName;

    // 发货类型
    // 1.国内
    // 2.海外直邮
    // 3.贝海直邮
    // 4.商家保税
    // 5.贝海保税
    // 6.海外认证直邮
    // 7.海外拼邮
    @JsonProperty("DeliveryMethod")
    private int deliveryMethod;

    // 保税区
    // 1.广州
    // 2.宁波
    // 3.杭州
    // 4.郑州
    // 5.天津
    // 6.重庆
    // 7.深圳
    // 8.上海
    @JsonProperty("BondedArea")
    private int bondedArea;

    // 重量
    @JsonProperty("Weight")
    private Double weight;

    // 是否包邮商品
    private boolean isFreeShipping;

    // 交税方
    // 0. 卖家交税
    // 1. 买家交税
    @JsonProperty("TariffType")
    private int tariffType;

    // 状态
    // -1. 已删除
    //  0. 有效
    //  2. 无效
    @JsonProperty("Status")
    private int status;

    // 商品的限购数量(0 为不限制购买数量)
    @JsonProperty("LimitNumber")
    private int limitNumber;

    // 限购的起始时间
    @JsonProperty("LimitStartTime")
    private Date limitStartTime;

    // 库存数量（活动中为活动库存）
    @JsonProperty("StockNum")
    private int stockNum;

    // 商品备案号（杭保商品）
    @JsonProperty("ProductCode")
    private String productCode;

    // 本土退货
    @JsonProperty("LocalReturn")
    private int localReturn;

    // 规格的sku 编号
    @JsonProperty("SKU")
    private String sku;

    // 备货类型（2- FBX商品）
    @JsonProperty("CatalogType")
    private int catalogType;

    // 是否支持7天无理由退货
    private boolean isNoReasonReturn;

    // 商品下有效规格总数
    @JsonProperty("CatalogCount")
    private int catalogCount;

    // 预订商品
    private boolean isPreSale;

    // 规格属性列表
    @JsonProperty("Properties")
    private List<PropertyDto> properties;

    // 规格价格
    @JsonProperty("Price")
    private Double price;

    // 是否是PSP商品
    private boolean pspProduct;

    // 商品关联直播
    @JsonProperty("LiveProduct")
    private LiveProductCartDto liveProduct;

    // 商品活动
    @JsonProperty("ProductActivity")
    private ProductActivityCartDto productActivity;

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

    public String getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(String catalogId) {
        this.catalogId = catalogId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getMainPicUrl() {
        return mainPicUrl;
    }

    public void setMainPicUrl(String mainPicUrl) {
        this.mainPicUrl = mainPicUrl;
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

    @JsonProperty("IsNoReasonReturn")
    public boolean isNoReasonReturn() {
        return isNoReasonReturn;
    }

    public void setNoReasonReturn(boolean noReasonReturn) {
        this.isNoReasonReturn = noReasonReturn;
    }

    public void setValidEnd(Date validEnd) {
        this.validEnd = validEnd;
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

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getThirdCategoryId() {
        return thirdCategoryId;
    }

    public void setThirdCategoryId(int thirdCategoryId) {
        this.thirdCategoryId = thirdCategoryId;
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

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public int getTariffType() {
        return tariffType;
    }

    public void setTariffType(int tariffType) {
        this.tariffType = tariffType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getLimitNumber() {
        return limitNumber;
    }

    public void setLimitNumber(int limitNumber) {
        this.limitNumber = limitNumber;
    }

    public Date getLimitStartTime() {
        return limitStartTime;
    }

    public void setLimitStartTime(Date limitStartTime) {
        this.limitStartTime = limitStartTime;
    }

    public int getStockNum() {
        return stockNum;
    }

    public void setStockNum(int stockNum) {
        this.stockNum = stockNum;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public int getLocalReturn() {
        return localReturn;
    }

    public void setLocalReturn(int localReturn) {
        this.localReturn = localReturn;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public int getCatalogType() {
        return catalogType;
    }

    public void setCatalogType(int catalogType) {
        this.catalogType = catalogType;
    }

    public int getCatalogCount() {
        return catalogCount;
    }

    public void setCatalogCount(int catalogCount) {
        this.catalogCount = catalogCount;
    }

    public List<PropertyDto> getProperties() {
        return properties;
    }

    public void setProperties(List<PropertyDto> properties) {
        this.properties = properties;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @JsonProperty("IsFreeShipping")
    public boolean isFreeShipping() {
        return isFreeShipping;
    }

    public void setFreeShipping(boolean freeShipping) {
        this.isFreeShipping = freeShipping;
    }

    @JsonProperty("IsPspProduct")
    public boolean isPspProduct() {
        return pspProduct;
    }

    public void setPspProduct(boolean pspProduct) {
        this.pspProduct = pspProduct;
    }

    public LiveProductCartDto getLiveProduct() {
        return liveProduct;
    }

    public void setLiveProduct(LiveProductCartDto liveProduct) {
        this.liveProduct = liveProduct;
    }

    public ProductActivityCartDto getProductActivity() {
        return productActivity;
    }

    public void setProductActivity(ProductActivityCartDto productActivity) {
        this.productActivity = productActivity;
    }

    @JsonProperty("IsPreSale")
    public boolean isPreSale() {
        return isPreSale;
    }

    public void setPreSale(boolean preSale) {
        isPreSale = preSale;
    }
}
