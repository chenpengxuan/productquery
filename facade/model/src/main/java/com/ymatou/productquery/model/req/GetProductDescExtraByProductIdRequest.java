package com.ymatou.productquery.model.req;

import javax.ws.rs.QueryParam;

/**
 * Created by zhujinfeng on 2017/5/2.
 */
public class GetProductDescExtraByProductIdRequest extends BaseRequest {

    /**
     * 商品编号
     */
    @QueryParam("ProductId")
    private String productId;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

}
