package com.ymatou.productquery.model.req;

import java.util.List;

/**
 * Created by zhangyong on 2017/4/10.
 */
public class GetCatalogListByCatalogIdListRequest extends BaseRequest {
    /**
     * 规格编号列表
     */
    private List<String> CatalogIdList;

    public List<String> getCatalogIdList() {
        return CatalogIdList;
    }

    public void setCatalogIdList(List<String> catalogIdList) {
        CatalogIdList = catalogIdList;
    }
}
