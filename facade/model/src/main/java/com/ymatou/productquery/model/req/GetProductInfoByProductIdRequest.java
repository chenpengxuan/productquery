package com.ymatou.productquery.model.req;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.ws.rs.QueryParam;

/**
 * 根据商品编号获取商品信息
 * Created by chenpengxuan on 2017/4/25.
 */
public class GetProductInfoByProductIdRequest extends BaseRequest {
    /**
     * 商品编号列表
     */
    @QueryParam("ProductId")
    @NotEmpty(message = "商品id不能为空")
    @NotNull(message = "商品id不能为空")
    private String productId;

    /**
     * 下一场活动延长取值有效期，默认是1天内。
     */
    @QueryParam("NextActivityExpire")
    @Min(value = 1, message = "活动延长取值有效期必须大于或等于1天")
    private int nextActivityExpire;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getNextActivityExpire() {
        return nextActivityExpire;
    }

    public void setNextActivityExpire(int nextActivityExpire) {
        this.nextActivityExpire = nextActivityExpire;
    }
}
