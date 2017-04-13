package com.ymatou.productquery.domain.repo;

import com.ymatou.productquery.domain.model.*;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by zhangyong on 2017/4/6.
 */
@Component
public interface Repository {

    /**
     * 根据规格id列表获取商品id规格id映射关系
     *
     * @param catalogIdList
     * @return
     */
    List<String> getProductIdsByCatalogIds(List<String> catalogIdList);

    /**
     * 获取全部有效活动商品列表
     *
     * @return
     */
    List<ActivityProducts> getAllValidActivityProductList();

    /**
     * 获取新增活动商品信息列表
     *
     * @param newestActivityObjectId 最新主键
     * @return
     */
    List<ActivityProducts> getActivityProductList(ObjectId newestActivityObjectId);

    /**
     * 获取活动商品信息列表
     *
     * @param productIdList
     * @return
     */
    List<ActivityProducts> getActivityProductList(List<String> productIdList);

    /**
     * 获取历史商品
     *
     * @param productIdList
     * @return
     */
    List<ProductDetailModel> getHistoryProductListByProductIdList(List<String> productIdList);

    /**
     * 根据ProductIds查询Proudcts
     *
     * @param productIdList
     * @return
     */
    List<Products> getProductsByProductIds(List<String> productIdList);

    /**
     * 根据ProductIds查询Catalogs
     *
     * @param productIdList
     * @return
     */
    List<Catalogs> getCatalogsByProductIds(List<String> productIdList);

    List<LiveProducts> getLiveProductList(List<String> productIdList);
}
