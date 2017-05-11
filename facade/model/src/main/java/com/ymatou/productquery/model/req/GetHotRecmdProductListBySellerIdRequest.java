package com.ymatou.productquery.model.req;

import javax.ws.rs.QueryParam;

/**
 * Created by zhujinfeng on 2017/5/2.
 */
public class GetHotRecmdProductListBySellerIdRequest extends BaseRequest {

    @QueryParam("SellerId")
    private int sellerId;

    public int getSellerId(){return sellerId;}
    public void setSellerId(int sellerId){this.sellerId = sellerId;}

}
