package com.ymatou.productquery.model.req;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by zhujinfeng on 2017/5/2.
 */
public class GetNewestProductListBySellerIdRequest extends BaseRequest {

    @JsonProperty("SellerId")
    private int sellerId;

    @JsonProperty("CurPage")
    private int curPage;

    @JsonProperty("PageSize")
    private int pageSize;

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
