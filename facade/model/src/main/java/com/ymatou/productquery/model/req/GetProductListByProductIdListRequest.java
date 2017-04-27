package com.ymatou.productquery.model.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

/**
 * Created by zhangyong on 2017/4/27.
 */
public class GetProductListByProductIdListRequest extends BaseRequest {
    // <summary>
    // 商品编号列表
    // </summary>
    @JsonProperty("ProductIdList")
    @NotEmpty(message = "商品id不能为空")
    private List<String> productIdList;

    public List<String> getProductIdList() {
        return productIdList;
    }

    public void setProductIdList(List<String> productIdList) {
        this.productIdList = productIdList;
    }
}
