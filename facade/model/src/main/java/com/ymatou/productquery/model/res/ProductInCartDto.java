package com.ymatou.productquery.model.res;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;

/**
 * Created by zhangyong on 2017/4/10.
 */
public class ProductInCartDto {
    /// <summary>
    /// 商品编号
    /// </summary>
    @JsonProperty("ProductId")
    private String productId;

    /// <summary>
    /// 快照版本
    /// </summary>
    @JsonProperty("Version")
    private String version;

    /// <summary>
    /// 规格编号
    /// </summary>
    @JsonProperty("CatalogId")
    private String catalogId;

    /// <summary>
    /// 商品名称
    /// </summary>
    @JsonProperty("ProductName")
    private String productName;

    /// <summary>
    /// 商品主要图片
    /// </summary>
    @JsonProperty("MainPicUrl")
    private String mainPicUrl;

    /// <summary>
    /// 有效开始时间（年月日时分秒）
    /// </summary>
    @JsonProperty("ValidStart")
    private Date validStart;

    /// <summary>
    /// 有效结束时间（年月日时分秒）
    /// </summary>
    @JsonProperty("ValidEnd")
    private Date validEnd;

    /// <summary>
    /// 品牌编号
    /// </summary>
    @JsonProperty("BrandId")
    private int brandId;

    /// <summary>
    /// 一级分类
    /// </summary>
    @JsonProperty("MasterCategoryId")
    private int masterCategoryId;

    /// <summary>
    /// 二级分类
    /// </summary>
    @JsonProperty("CategoryId")
    private int categoryId;

    /// <summary>
    /// 三级分类
    /// </summary>
    @JsonProperty("ThirdCategoryId")
    private int thirdCategoryId;

    /// <summary>
    /// 买手用户编号
    /// </summary>
    @JsonProperty("SellerId")
    private int sellerId;

    /// <summary>
    /// 买手用户账号
    /// </summary>
    @JsonProperty("SellerName")
    private String sellerName;

    /// <summary>
    /// 发货类型
    /// 1.国内
    /// 2.海外直邮
    /// 3.贝海直邮
    /// 4.商家保税
    /// 5.贝海保税
    /// 6.海外认证直邮
    /// 7.海外拼邮
    /// </summary>
    @JsonProperty("DeliveryMethod")
    private int deliveryMethod;

    /// <summary>
    /// 保税区
    /// 1.广州
    /// 2.宁波
    /// 3.杭州
    /// 4.郑州
    /// 5.天津
    /// 6.重庆
    /// 7.深圳
    /// 8.上海
    /// </summary>
    @JsonProperty("BondedArea")
    private int bondedArea;

    /// <summary>
    /// 重量
    /// </summary>
    @JsonProperty("Weight")
    private Double weight;

    /// <summary>
    /// 是否包邮商品
    /// </summary>
    @JsonProperty("IsFreeShipping")
    private boolean freeShipping;

    /// <summary>
    /// 交税方
    /// 0. 卖家交税
    /// 1. 买家交税
    /// </summary>
    @JsonProperty("TariffType")
    private int tariffType;

    /// <summary>
    /// 状态
    /// -1. 已删除
    ///  0. 有效
    ///  2. 无效
    /// </summary>
    @JsonProperty("Status")
    private ProductStatusEnum status;

    /// <summary>
    /// 商品的限购数量(0 为不限制购买数量)
    /// </summary>
    @JsonProperty("LimitNumber")
    private int limitNumber;

    /// <summary>
    /// 限购的起始时间
    /// </summary>
    @JsonProperty("LimitStartTime")
    private Date limitStartTime;

    /// <summary>
    /// 库存数量（活动中为活动库存）
    /// </summary>
    @JsonProperty("StockNum")
    private int stockNum;

    /// <summary>
    /// 商品备案号（杭保商品）
    /// </summary>
    @JsonProperty("ProductCode")
    private String productCode;

    /// <summary>
    /// 本土退货
    /// </summary>
    @JsonProperty("LocalReturn")
    private int localReturn;

    /// <summary>
    /// 规格的sku 编号
    /// </summary>
    @JsonProperty("SKU")
    private String sku;

    /// <summary>
    /// 备货类型（2- FBX商品）
    /// </summary>
    @JsonProperty("CatalogType")
    private int catalogType;

    /// <summary>
    /// 是否支持7天无理由退货
    /// </summary>
    @JsonProperty("IsNoReasonReturn")
    private boolean noReasonReturn;

    /// <summary>
    /// 商品下有效规格总数
    /// </summary>
    @JsonProperty("CatalogCount")
    private int catalogCount;

    /// <summary>
    /// 预订商品
    /// </summary>
    @JsonProperty("IsPreSale")
    private boolean preSale;

    /// <summary>
    /// 规格属性列表
    /// </summary>
    @JsonProperty("Properties")
    private List<PropertyDto> properties;

    /// <summary>
    /// 规格价格
    /// </summary>
    @JsonProperty("Price")
    private Double price;

    /// <summary>
    /// 是否是PSP商品
    /// </summary>
    @JsonProperty("IsPspProduct")
    private boolean pspProduct;

    /// <summary>
    /// 商品关联直播
    /// </summary>
    @JsonProperty("LiveProduct")
    private LiveProductCartDto liveProduct;

    /// <summary>
    /// 商品活动
    /// </summary>
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

    public boolean isNoReasonReturn() {
        return noReasonReturn;
    }

    public void setNoReasonReturn(boolean noReasonReturn) {
        this.noReasonReturn = noReasonReturn;
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

    public ProductStatusEnum getStatus() {
        return status;
    }

    public void setStatus(ProductStatusEnum status) {
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

    public boolean getPreSale() {
        return preSale;
    }

    public void setPreSale(boolean preSale) {
        preSale = preSale;
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

    public boolean isFreeShipping() {
        return freeShipping;
    }

    public void setFreeShipping(boolean freeShipping) {
        this.freeShipping = freeShipping;
    }

    public boolean isPreSale() {
        return preSale;
    }

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
}
