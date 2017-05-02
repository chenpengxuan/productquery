package com.ymatou.productquery.model.req;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by zhujinfeng on 2017/5/2.
 */
public class GetSecKillProductActivityStockRequest extends BaseRequest {

    @JsonProperty("ProductId")
    private String productId;

    @JsonProperty("ActivityId")
    private int activityId;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }
}
