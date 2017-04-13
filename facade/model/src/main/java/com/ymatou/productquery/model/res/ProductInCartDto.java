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
    private String catalogId;

    /// <summary>
    /// 商品名称
    /// </summary>
    private String ProductName;

    /// <summary>
    /// 商品主要图片
    /// </summary>
    private String MainPicUrl;

    /// <summary>
    /// 有效开始时间（年月日时分秒）
    /// </summary>

    private Date ValidStart;

    /// <summary>
    /// 有效结束时间（年月日时分秒）
    /// </summary>
    private Date ValidEnd;

    /// <summary>
    /// 品牌编号
    /// </summary>
    private int BrandId;

    /// <summary>
    /// 一级分类
    /// </summary>
    private int MasterCategoryId;

    /// <summary>
    /// 二级分类
    /// </summary>
    private int CategoryId;

    /// <summary>
    /// 三级分类
    /// </summary>
    private int ThirdCategoryId;

    /// <summary>
    /// 买手用户编号
    /// </summary>
    private int SellerId;

    /// <summary>
    /// 买手用户账号
    /// </summary>
    private String SellerName;

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
    private int DeliveryMethod;

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
    private int BondedArea;

    /// <summary>
    /// 重量
    /// </summary>
    private Double Weight;

    /// <summary>
    /// 是否包邮商品
    /// </summary>
    private boolean IsFreeShipping;

    /// <summary>
    /// 交税方
    /// 0. 卖家交税
    /// 1. 买家交税
    /// </summary>
    private int TariffType;

    /// <summary>
    /// 状态
    /// -1. 已删除
    ///  0. 有效
    ///  2. 无效
    /// </summary>
    private ProductStatusEnum Status;

    /// <summary>
    /// 商品的限购数量(0 为不限制购买数量)
    /// </summary>
    private int LimitNumber;

    /// <summary>
    /// 限购的起始时间
    /// </summary>
    private Date LimitStartTime;

    /// <summary>
    /// 库存数量（活动中为活动库存）
    /// </summary>
    private int StockNum;

    /// <summary>
    /// 商品备案号（杭保商品）
    /// </summary>
    private String ProductCode;

    /// <summary>
    /// 本土退货
    /// </summary>
    private int LocalReturn;

    /// <summary>
    /// 规格的sku 编号
    /// </summary>
    private String SKU;

    /// <summary>
    /// 备货类型（2- FBX商品）
    /// </summary>
    private int CatalogType;

    /// <summary>
    /// 是否支持7天无理由退货
    /// </summary>
    private boolean IsNoReasonReturn;

    /// <summary>
    /// 商品下有效规格总数
    /// </summary>
    private int CatalogCount;

    /// <summary>
    /// 预订商品
    /// </summary>
    private boolean IsPreSale;

    /// <summary>
    /// 规格属性列表
    /// </summary>
    private List<PropertyDto> Properties;

    /// <summary>
    /// 规格价格
    /// </summary>
    private Double Price;

    /// <summary>
    /// 是否是PSP商品
    /// </summary>
    private boolean IsPspProduct;

    /// <summary>
    /// 商品关联直播
    /// </summary>
    private LiveProductCartDto LiveProduct;

    /// <summary>
    /// 商品活动
    /// </summary>
    private ProductActivityCartDto ProductActivity;

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
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getMainPicUrl() {
        return MainPicUrl;
    }

    public void setMainPicUrl(String mainPicUrl) {
        MainPicUrl = mainPicUrl;
    }

    public Date getValidStart() {
        return ValidStart;
    }

    public void setValidStart(Date validStart) {
        ValidStart = validStart;
    }

    public Date getValidEnd() {
        return ValidEnd;
    }

    public void setValidEnd(Date validEnd) {
        ValidEnd = validEnd;
    }

    public int getBrandId() {
        return BrandId;
    }

    public void setBrandId(int brandId) {
        BrandId = brandId;
    }

    public int getMasterCategoryId() {
        return MasterCategoryId;
    }

    public void setMasterCategoryId(int masterCategoryId) {
        MasterCategoryId = masterCategoryId;
    }

    public int getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(int categoryId) {
        CategoryId = categoryId;
    }

    public int getThirdCategoryId() {
        return ThirdCategoryId;
    }

    public void setThirdCategoryId(int thirdCategoryId) {
        ThirdCategoryId = thirdCategoryId;
    }

    public int getSellerId() {
        return SellerId;
    }

    public void setSellerId(int sellerId) {
        SellerId = sellerId;
    }

    public String getSellerName() {
        return SellerName;
    }

    public void setSellerName(String sellerName) {
        SellerName = sellerName;
    }

    public int getDeliveryMethod() {
        return DeliveryMethod;
    }

    public void setDeliveryMethod(int deliveryMethod) {
        DeliveryMethod = deliveryMethod;
    }

    public int getBondedArea() {
        return BondedArea;
    }

    public void setBondedArea(int bondedArea) {
        BondedArea = bondedArea;
    }

    public Double getWeight() {
        return Weight;
    }

    public void setWeight(Double weight) {
        Weight = weight;
    }

    public boolean getFreeShipping() {
        return IsFreeShipping;
    }

    public void setFreeShipping(boolean freeShipping) {
        IsFreeShipping = freeShipping;
    }

    public int getTariffType() {
        return TariffType;
    }

    public void setTariffType(int tariffType) {
        TariffType = tariffType;
    }

    public ProductStatusEnum getStatus() {
        return Status;
    }

    public void setStatus(ProductStatusEnum status) {
        Status = status;
    }

    public int getLimitNumber() {
        return LimitNumber;
    }

    public void setLimitNumber(int limitNumber) {
        LimitNumber = limitNumber;
    }

    public Date getLimitStartTime() {
        return LimitStartTime;
    }

    public void setLimitStartTime(Date limitStartTime) {
        LimitStartTime = limitStartTime;
    }

    public int getStockNum() {
        return StockNum;
    }

    public void setStockNum(int stockNum) {
        StockNum = stockNum;
    }

    public String getProductCode() {
        return ProductCode;
    }

    public void setProductCode(String productCode) {
        ProductCode = productCode;
    }

    public int getLocalReturn() {
        return LocalReturn;
    }

    public void setLocalReturn(int localReturn) {
        LocalReturn = localReturn;
    }

    public String getSKU() {
        return SKU;
    }

    public void setSKU(String SKU) {
        this.SKU = SKU;
    }

    public int getCatalogType() {
        return CatalogType;
    }

    public void setCatalogType(int catalogType) {
        CatalogType = catalogType;
    }

    public boolean getNoReasonReturn() {
        return IsNoReasonReturn;
    }

    public void setNoReasonReturn(boolean noReasonReturn) {
        IsNoReasonReturn = noReasonReturn;
    }

    public int getCatalogCount() {
        return CatalogCount;
    }

    public void setCatalogCount(int catalogCount) {
        CatalogCount = catalogCount;
    }

    public boolean getPreSale() {
        return IsPreSale;
    }

    public void setPreSale(boolean preSale) {
        IsPreSale = preSale;
    }

    public List<PropertyDto> getProperties() {
        return Properties;
    }

    public void setProperties(List<PropertyDto> properties) {
        Properties = properties;
    }

    public Double getPrice() {
        return Price;
    }

    public void setPrice(Double price) {
        Price = price;
    }

    public boolean getPspProduct() {
        return IsPspProduct;
    }

    public void setPspProduct(boolean pspProduct) {
        IsPspProduct = pspProduct;
    }

    public LiveProductCartDto getLiveProduct() {
        return LiveProduct;
    }

    public void setLiveProduct(LiveProductCartDto liveProduct) {
        LiveProduct = liveProduct;
    }

    public ProductActivityCartDto getProductActivity() {
        return ProductActivity;
    }

    public void setProductActivity(ProductActivityCartDto productActivity) {
        ProductActivity = productActivity;
    }
}
