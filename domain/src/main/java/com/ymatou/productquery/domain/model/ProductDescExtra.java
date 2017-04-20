package com.ymatou.productquery.domain.model;

import org.mongodb.morphia.annotations.Property;

import java.util.List;

/**
 * Created by zhangyong on 2017/4/6.
 */
public class ProductDescExtra {
    @Property("spid")
    private String productId;//	商品编号
    @Property("desc")
    private String descText;//	图文描述 - 文字部分
    @Property("descpics")
    private List<String> descPicList;//	图文描述 - 图片部分
    @Property("sizepics")
    private List<String> sizePicList;//	尺码表
    @Property("notice")
    private String noticeText;//	买家须知文本
    @Property("notipics")
    private List<String> noticePicList;//	买家须知图片列表
    @Property("intro")
    private String sellerInfoText;//	买手介绍文本
    @Property("intropics")
    private List<String> sellerIntroPicList;//	买家须知图片列表
    @Property("props")
    private List<ProductDescPropertyInfo> propertyList;//	商品属性列表

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getDescText() {
        return descText;
    }

    public void setDescText(String descText) {
        this.descText = descText;
    }

    public List<String> getDescPicList() {
        return descPicList;
    }

    public void setDescPicList(List<String> descPicList) {
        this.descPicList = descPicList;
    }

    public List<String> getSizePicList() {
        return sizePicList;
    }

    public void setSizePicList(List<String> sizePicList) {
        this.sizePicList = sizePicList;
    }

    public String getNoticeText() {
        return noticeText;
    }

    public void setNoticeText(String noticeText) {
        this.noticeText = noticeText;
    }

    public List<String> getNoticePicList() {
        return noticePicList;
    }

    public void setNoticePicList(List<String> noticePicList) {
        this.noticePicList = noticePicList;
    }

    public String getSellerInfoText() {
        return sellerInfoText;
    }

    public void setSellerInfoText(String sellerInfoText) {
        this.sellerInfoText = sellerInfoText;
    }

    public List<String> getSellerIntroPicList() {
        return sellerIntroPicList;
    }

    public void setSellerIntroPicList(List<String> sellerIntroPicList) {
        this.sellerIntroPicList = sellerIntroPicList;
    }

    public List<ProductDescPropertyInfo> getPropertyList() {
        return propertyList;
    }

    public void setPropertyList(List<ProductDescPropertyInfo> propertyList) {
        this.propertyList = propertyList;
    }
}
