package com.ymatou.productquery.model.res;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by zhujinfeng on 2017/5/2.
 */
public class TopProductInLiveDto {

    // 直播编号
    @JsonProperty("LiveId")
    private int liveId;

    // 商品编号
    @JsonProperty("ProductId")
    private String productId;

    // 商品首图
    @JsonProperty("PicUrl")
    private String picUrl;

    // 商品价格
    @JsonProperty("Price")
    private double price;

    // 是否优选商品
    @JsonProperty("IsPspProduct")
    private boolean isPspProduct;


    public int getLiveId(){return liveId;}
    public void setLiveId(int liveId){this.liveId = liveId;}

    public String getProductId(){return productId;}
    public void setProductId(String productId){this.productId = productId;}

    public String getPicUrl(){return picUrl;}
    public void setPicUrl(String picUrl){this.picUrl = picUrl;}

    public double getPrice(){return price;}
    public void setPrice(double price){this.price = price;}

    public boolean getIsPspProduct(){return isPspProduct;}
    public void isPspProduct(boolean isPspProduct){this.isPspProduct = isPspProduct;}

}
