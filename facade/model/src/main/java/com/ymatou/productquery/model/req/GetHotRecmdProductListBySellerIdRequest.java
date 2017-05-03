package com.ymatou.productquery.model.req;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by zhujinfeng on 2017/5/2.
 */
public class GetHotRecmdProductListBySellerIdRequest extends BaseRequest {

    @JsonProperty("SellerId")
    private int sellerId;

    public int getSellerId(){return sellerId;}
    public void setSellerId(int sellerId){this.sellerId = sellerId;}

}
