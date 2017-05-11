package com.ymatou.productquery.model.req;

import javax.ws.rs.QueryParam;

/**
 * Created by zhujinfeng on 2017/5/2.
 */
public class GetNewestProductListBySellerIdRequest extends BaseRequest {

    @QueryParam("SellerId")
    private int sellerId;

    @QueryParam("CurPage")
    private int curPage;

    @QueryParam("PageSize")
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
