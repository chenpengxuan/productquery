package com.ymatou.productquery.model.res;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by zhujinfeng on 2017/5/2.
 */
public class RecmdProductIdDto {
    // 商品编号
    @JsonProperty("ProductId")
    private String productId;

    // 直播置顶商品
    @JsonProperty("IsTopProduct")
    private boolean isTopProduct;

    public String getProductId(){return productId;}
    public void setProductId(String productId){ this.productId = productId;}

    public boolean getIsTopProduct(){return isTopProduct;}
    public void isTopProduct(boolean isTopProduct){this.isTopProduct = isTopProduct;}

}
