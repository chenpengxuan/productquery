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
    BaseResponseNetAdapter getProductDetailListByTradeIsolation(GetProductDetailListByTradeIsolationRequest request);

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
    BaseResponseNetAdapter getProductListByProductIdList(GetProductListByProductIdListRequest request);

    /**
     * 商品列表服务(交易隔离)
     *
     * @param request
     * @return
     */
    BaseResponseNetAdapter getProductListByTradeIsolation(GetProductListByTradeIsolationRequest request);

    /**
     * 根据商品编号获取商品信息(对应商详页场景)
     *
     * @param request
     * @return
     */
    BaseResponseNetAdapter getProductInfoByProductId(GetProductInfoByProductIdRequest request);

    /**
     * 根据商品编号取交易隔离的商品信息
     * @param request
     * @return
     */
    BaseResponseNetAdapter getProductInfoByTradeIsolation(GetProductInfoByProductIdRequest request);

    /**
     * 取直播中置顶商品列表
     * @param request
     * @return
     */
    BaseResponseNetAdapter getTopProductListByLiveId(GetTopProductListByLiveIdRequest request);

    /**
     * 取买手新品列表
     * @param request
     * @return
     */
    BaseResponseNetAdapter getNewestProductList(GetNewestProductListBySellerIdRequest request);

    /**
     * 买手热推商品列表
     * @param request
     * @return
     */
    BaseResponseNetAdapter getHotRecmdProductListBySellerId(GetHotRecmdProductListBySellerIdRequest request);

    /**
     * 买手推荐的商品列表
     * @param request
     * @return
     */
    BaseResponseNetAdapter getSellerRecommendProductList(GetSellerRecommendProductListRequest request);

    /**
     * 取秒杀商品的活动库存量
     * @param request
     * @return
     */
    BaseResponseNetAdapter getSecKillProductActivityStock(GetSecKillProductActivityStockRequest request);

    /**
     * 取商品图文描述扩展信息
     * @param request
     * @return
     */
    BaseResponseNetAdapter getProductDescExtraByProductId (GetProductDescExtraByProductIdRequest request);


}
