package com.ymatou.productquery.domain.service;

import com.ymatou.productquery.domain.cache.ActivityCacheProcessor;
import com.ymatou.productquery.domain.cache.CatalogCacheProcessor;
import com.ymatou.productquery.domain.cache.LiveCacheProcessor;
import com.ymatou.productquery.domain.cache.ProductCacheProcessor;
import com.ymatou.productquery.domain.model.ActivityProducts;
import com.ymatou.productquery.domain.model.Catalogs;
import com.ymatou.productquery.domain.model.LiveProducts;
import com.ymatou.productquery.domain.model.Products;
import com.ymatou.productquery.domain.repo.mongorepo.ProductRepository;
import com.ymatou.productquery.infrastructure.config.props.BizProps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 列表查询与单品查询共用
 * Created by chenpengxuan on 2017/4/26.
 */
@Component
public class CommonQueryService {

    @Autowired
    private BizProps bizProps;

    @Resource(name = "productCacheProcessor")
    private ProductCacheProcessor productCacheProcessor;

    @Resource(name = "activityCacheProcessor")
    private ActivityCacheProcessor activityCacheProcessor;

    @Resource(name = "catalogCacheProcessor")
    private CatalogCacheProcessor catalogCacheProcessor;

    @Resource(name="liveCacheProcessor")
    private LiveCacheProcessor liveCacheProcessor;

    @Autowired
    private ProductRepository productRepository;

    /**
     * 根据商品id列表获取商品信息
     *
     * @param productIdList
     * @return
     */
    public List<Products> getProductListByProductIdList(List<String> productIdList) {
        if (bizProps.isUseCache()) {
            return productCacheProcessor.getProductInfoByProductIdList(productIdList);
        } else {
            return productRepository.getProductListByProductIdList(productIdList);
        }
    }

    /**
     * 根据商品id获取商品信息
     *
     * @param productId
     * @return
     */
    public Products getProductByProductId(String productId) {
        if (bizProps.isUseCache()) {
            return productCacheProcessor.getProductInfoByProductId(productId);
        } else {
            return productRepository.getProductInfoByProductId(productId);
        }
    }

    /**
     * 根据规格id列表获取规格信息
     *
     * @param catalogIdList
     * @return
     */
    public List<Catalogs> getCatalogListByCatalogIdList(List<String> catalogIdList) {
        if (bizProps.isUseCache()) {
            return catalogCacheProcessor.getCatalogListByCatalogIdList(catalogIdList);
        } else {
            return productRepository.getCatalogListByCatalogIdList(catalogIdList);
        }
    }

    /**
     * 根据商品id列表获取规格列表
     *
     * @param productIdList
     * @return
     */
    public List<Catalogs> getCatalogListByProductIdList(List<String> productIdList) {
        if (bizProps.isUseCache()) {
            return catalogCacheProcessor.getCatalogListByProductIdList(productIdList);
        } else {
            return productRepository.getCatalogListByProductIdList(productIdList);
        }
    }

    /**
     * 根据商品id列表获取活动商品信息列表
     *
     * @param productIdList
     * @return
     */
    public List<ActivityProducts> getActivityProductListByProductIdList(List<String> productIdList) {
        if(bizProps.isUseCache()){
            return activityCacheProcessor.getActivityProductListByProductIdList(productIdList);
        }else{
            return productRepository.getActivityProductListByProductIdList(productIdList);
        }
    }

    /**
     * 根据直播商品id列表获取直播商品信息
     *
     * @param productIdList
     * @return
     */
    public List<LiveProducts> getLiveProductListByProductId(List<String> productIdList) {
        if(bizProps.isUseCache()){
            return liveCacheProcessor.getProductInfoByProductIdList(productIdList);
        }else{
            return productRepository.getLiveProductListByProductIdList(productIdList);
        }
    }
}
