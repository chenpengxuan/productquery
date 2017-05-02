package com.ymatou.productquery.model.req;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by zhujinfeng on 2017/5/2.
 */
public class GetProductDescExtraByProductIdRequest extends BaseRequest {

    @JsonProperty("ProductId")
    private String productId;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

}
