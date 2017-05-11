package com.ymatou.productquery.model.req;

import javax.ws.rs.QueryParam;

/**
 * Created by zhujinfeng on 2017/5/2.
 */
public class GetSecKillProductActivityStockRequest extends BaseRequest {

    @QueryParam("ProductId")
    private String productId;

    @QueryParam("ActivityId")
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
