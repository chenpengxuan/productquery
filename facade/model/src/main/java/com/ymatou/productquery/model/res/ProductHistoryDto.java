package com.ymatou.productquery.model.res;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Created by zhangyong on 2017/4/25.
 */
public class ProductHistoryDto {

    // 商品编号
    @JsonProperty("ProductId")
    private String productId;

    // 商品标题
    @JsonProperty("Title")
    private String title;

    // 商品主图
    @JsonProperty("MainPic")
    private String mainPic;

    // 买手编号
    @JsonProperty("SellerId")
    private int sellerId;

    // 买手账号
    @JsonProperty("SellerName")
    private String sellerName;

    // 包税类型
    @JsonProperty("TariffType")
    private int tariffType;

    // 是否包邮
    @JsonProperty("FreeShipping")
    private boolean freeShipping;

    // 发货类型
    @JsonProperty("DeliveryMethod")
    private int deliveryMethod;

    // 本土退货
    @JsonProperty("LocalReturn")
    private int localReturn;

    // 价格
    @JsonProperty("Price")
    private Double price;

    // 开始时间
    @JsonProperty("ValidStart")
    private Date validStart;

    // 结束时间
    @JsonProperty("ValidEnd")
    private Date validEnd;

    // 商品的售卖状态
    @JsonProperty("Status")
    private int status;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
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

    public int getTariffType() {
        return tariffType;
    }

    public void setTariffType(int tariffType) {
        this.tariffType = tariffType;
    }

    public boolean isFreeShipping() {
        return freeShipping;
    }

    public void setFreeShipping(boolean freeShipping) {
        this.freeShipping = freeShipping;
    }

    public int getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(int deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public int getLocalReturn() {
        return localReturn;
    }

    public void setLocalReturn(int localReturn) {
        this.localReturn = localReturn;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
