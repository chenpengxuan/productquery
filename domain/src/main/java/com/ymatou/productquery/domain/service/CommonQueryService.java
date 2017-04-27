package com.ymatou.productquery.domain.service;

import com.ymatou.productquery.domain.model.ActivityProducts;
import com.ymatou.productquery.domain.model.Catalogs;
import com.ymatou.productquery.domain.model.LiveProducts;
import com.ymatou.productquery.domain.model.Products;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 列表查询与单品查询共用
 * Created by chenpengxuan on 2017/4/26.
 */
@Component
public class CommonQueryService {
    /**
     * 根据商品id列表获取商品信息
     * @param productIdList
     * @return
     */
    List<Products> getProductListByProductIdList(List<String> productIdList){
        return null;
    }

    /**
     * 根据商品id获取商品信息
     * @param productId
     * @return
     */
    Products getProductByProductId(String productId){
        return null;
    }

    /**
     * 根据规格id列表获取规格信息
     * @param catalogIdList
     * @return
     */
    List<Catalogs> getCatalogListByCatalogIdList(List<String> catalogIdList){
        return null;
    }

    /**
     * 根据商品id列表获取活动商品信息列表
     * @param productIdList
     * @return
     */
    List<ActivityProducts> getActivityProductListByProductIdList(List<String> productIdList){
        return null;
    }

    /**
     * 根据直播商品id列表获取直播商品信息
     * @param productIdList
     * @return
     */
    List<LiveProducts> getLiveProductListByProductId(List<String> productIdList){
        return null;
    }

    /**
     * 根据商品id列表获取规格列表
     * @param productIdList
     * @return
     */
    List<Catalogs> getCatalogListByProductIdList(List<String> productIdList){
        return null;
    }
}
