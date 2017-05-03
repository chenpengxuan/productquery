package com.ymatou.productquery.model.res;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by zhujinfeng on 2017/5/2.
 */
public class ProductDescExtraDto {

    @JsonProperty("ProductId")
    private String productId;

    @JsonProperty("DescText")
    private String descText;

    @JsonProperty("DescPicList")
    private List<String> descPicList;

    @JsonProperty("SizePicList")
    private List<String> sizePicList;

    @JsonProperty("NoticeText")
    private String noticeText;

    @JsonProperty("NoticePicList")
    private List<String> noticePicList;

    @JsonProperty("SellerIntroText")
    private String sellerIntroText;

    @JsonProperty("SellerIntroPicList")
    private List<String> sellerIntroPicList;

    @JsonProperty("PropertyList")
    private List<DescPropertyDto> descPropertyDtoList;

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

    public String getSellerIntroText() {
        return sellerIntroText;
    }

    public void setSellerIntroText(String sellerIntroText) {
        this.sellerIntroText = sellerIntroText;
    }

    public List<String> getSellerIntroPicList() {
        return sellerIntroPicList;
    }

    public void setSellerIntroPicList(List<String> sellerIntroPicList) {
        this.sellerIntroPicList = sellerIntroPicList;
    }

    public List<DescPropertyDto> getDescPropertyDtoList() {
        return descPropertyDtoList;
    }

    public void setDescPropertyDtoList(List<DescPropertyDto> descPropertyDto) {
        this.descPropertyDtoList = descPropertyDto;
    }
}


