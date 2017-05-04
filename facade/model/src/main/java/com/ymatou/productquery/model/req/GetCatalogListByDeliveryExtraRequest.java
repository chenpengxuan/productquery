package com.ymatou.productquery.model.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

/**
 * Created by zhangyong on 2017/5/3.
 */
public class GetCatalogListByDeliveryExtraRequest extends BaseRequest {

    @JsonProperty("CatalogIdList")
    @NotEmpty(message = "规格id不能为空")
    private List<CatalogDeliveryDto> catalogIdList;

    public List<CatalogDeliveryDto> getCatalogIdList() {
        return catalogIdList;
    }

    public void setCatalogIdList(List<CatalogDeliveryDto> catalogIdList) {
        this.catalogIdList = catalogIdList;
    }
}


