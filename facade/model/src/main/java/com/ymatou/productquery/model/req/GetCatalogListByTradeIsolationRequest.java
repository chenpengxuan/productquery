package com.ymatou.productquery.model.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

/**
 * Created by zhangyong on 2017/4/20.
 */
public class GetCatalogListByTradeIsolationRequest extends BaseRequest {
    /**
     * 规格编号列表
     */
    @JsonProperty("CatalogIdList")
    @NotEmpty(message = "规格id不能为空")
    private List<String> catalogIdList;

    public List<String> getCatalogIdList() {
        return catalogIdList;
    }

    public void setCatalogIdList(List<String> catalogIdList) {
        this.catalogIdList = catalogIdList;
    }
}
