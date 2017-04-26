package com.ymatou.productquery.facade;

import com.ymatou.productquery.model.req.*;
import com.ymatou.productquery.model.res.BaseResponseNetAdapter;

/**
 * Created by zhangyong on 2017/4/10.
 */
public interface ProductQueryFacade {
    /**
     * 购物车中商品列表（普通购物车中用）
     *
     * @param request
     * @return
     */
    BaseResponseNetAdapter getCatalogListByCatalogIdList(GetCatalogListByCatalogIdListRequest request);

    /**
     * 购物车中商品列表（交易隔离中用）
     *
     * @param request
     * @return
     */
    BaseResponseNetAdapter getCatalogListByTradeIsolation(GetCatalogListByTradeIsolationRequest request);

    BaseResponseNetAdapter getProductDetailListByProductIdList(GetProductDetailListByProductIdListRequest request);

    BaseResponseNetAdapter getProductListByHistoryProductIdList(GetProductListByHistoryProductIdListRequest request);
}
