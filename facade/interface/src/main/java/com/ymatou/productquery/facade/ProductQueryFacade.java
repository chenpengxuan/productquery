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


    /**
     * 商品明细列表
     *
     * @param request
     * @return
     */
    BaseResponseNetAdapter getProductDetailListByProductIdList(GetProductDetailListByProductIdListRequest request);

    /**
     * 商品明细列表（交易隔离）
     *
     * @param request
     * @return
     */
    BaseResponseNetAdapter GetProductDetailListByTradeIsolation(GetProductDetailListByTradeIsolationRequest request);

    /**
     * 历史商品列表
     *
     * @param request
     * @return
     */
    BaseResponseNetAdapter getProductListByHistoryProductIdList(GetProductListByHistoryProductIdListRequest request);

    /**
     * 商品列表服务
     *
     * @param request
     * @return
     */
    BaseResponseNetAdapter GetProductListByProductIdList(GetProductListByProductIdListRequest request);

    /**
     * 商品列表服务(交易隔离)
     *
     * @param request
     * @return
     */
    BaseResponseNetAdapter GetProductListByTradeIsolation(GetProductListByTradeIsolationRequest request);

    /**
     * 根据商品编号获取商品信息(对应商详页场景)
     *
     * @param request
     * @return
     */
    BaseResponseNetAdapter getProductDetailByProductId(GetProductInfoByProductIdRequest request);


}
