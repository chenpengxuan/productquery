package com.ymatou.productquery.domain.cache;

import com.google.common.cache.CacheStats;
import com.ymatou.productquery.domain.model.ActivityProducts;
import com.ymatou.productquery.domain.model.Catalogs;
import com.ymatou.productquery.domain.model.ProductTimeStamp;
import com.ymatou.productquery.domain.model.Products;
import com.ymatou.productquery.domain.repo.mongorepo.ActivityProdutRepository;
import com.ymatou.productquery.domain.repo.mongorepo.ProductRepository;
import com.ymatou.productquery.infrastructure.config.props.BizProps;
import com.ymatou.productquery.infrastructure.config.props.CacheProps;
import com.ymatou.productquery.infrastructure.util.CacheUtil.CacheManager;
import com.ymatou.productquery.infrastructure.util.LogWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

/**
 * Created by zhangyong on 2017/4/20.
 */
@Component
public class Cache {
    /**
     * 缓存工具类
     */
    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private BizProps bizProps;

    @Autowired
    private CacheProps cacheProps;

    @Autowired
    private LogWrapper logWrapper;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ActivityProdutRepository activityProdutRepository;

    /**
     * 获取缓存统计信息
     *
     * @return
     */
    public CacheStats getCacheStats() {
        return cacheManager.getCacheStats();
    }

    /**
     * 根据productid列表查询products
     *
     * @param productIdList
     * @param productUpdateTimeList
     * @return
     */
    public List<Products> getProductsByProductIds(List<String> productIdList, List<ProductTimeStamp> productUpdateTimeList) {
        productIdList = productIdList.stream().distinct().collect(Collectors.toList());

        List<Products> result = cacheManager.get(productIdList).values().stream().map(t -> (Products) t).collect(Collectors.toList());

        return processProductCache(productIdList, result, productUpdateTimeList);
    }

    /**
     * 根据productid列表查询catalogs
     *
     * @param productIdList
     * @param productUpdateTimeList
     * @return
     */
    public List<Catalogs> getCatalogsByProductIds(List<String> productIdList, List<ProductTimeStamp> productUpdateTimeList) {
        productIdList = productIdList.stream().distinct().collect(Collectors.toList());

        List<Catalogs> result = cacheManager.get(productIdList).values().stream().map(t -> (Catalogs) t).collect(Collectors.toList());

        return processCatalogCache(productIdList, result, productUpdateTimeList);
    }

    /**
     * 初始化活动商品缓存
     */
    public int initActivityProductCache() {
        List<ActivityProducts> activityProductList = activityProdutRepository.getAllValidActivityProductList();
        cacheManager.putActivityProduct(activityProductList
                .stream()
                .collect(Collectors.toMap(ActivityProducts::getProductId, y -> y, (key1, key2) -> key2))
        );
        return activityProductList.size();
    }

    /**
     * 添加活动商品增量信息
     */
    public void addNewestActivityProductCache() {
        ConcurrentMap activityProductCache = cacheManager.getActivityProductCacheContainer();

        ActivityProducts latestActivityProduct = (ActivityProducts) activityProductCache.values()
                .stream()
                .max((x, y) ->
                        {
                            long xProductInActivityId = x != null ?
                                    ((ActivityProducts) x).getProductInActivityId() : 0L;
                            long yProductInActivityId = y != null ?
                                    ((ActivityProducts) y).getProductInActivityId() : 0L;
                            return Long.compare(xProductInActivityId,
                                    yProductInActivityId);
                        }
                )
                .orElse(null);

        Integer newestCacheProductInActivityId = latestActivityProduct != null ?
                latestActivityProduct.getProductInActivityId() : 0;

        //获取新增的mongo活动商品信息
        List<ActivityProducts> newestActivityProductList = activityProdutRepository
                .getNewestActivityProductIdList(newestCacheProductInActivityId);

        //批量添加至缓存
        Map tempMap = newestActivityProductList
                .stream()
                .collect(Collectors.toMap(ActivityProducts::getProductId, y -> y, (key1, key2) -> key2));

        cacheManager.putActivityProduct(tempMap);

        logWrapper.recordInfoLog("增量添加活动商品缓存已执行,新增{}条", newestActivityProductList.size());
    }

    private List<Products> processProductCache(List<String> productIdList, List<Products> cacheProductList, List<ProductTimeStamp> productUpdateTimeList) {
        List<Products> result = new ArrayList<>();
        if (cacheProductList == null || cacheProductList.isEmpty()) {
            List<Products> productsList = productRepository.getProductsByProductIds(productIdList);
            result = productsList;
            createProductCacheData(result);
            return result;
        } else {
            //过滤有效业务缓存数据
            List<Products> validProductList = filterValidProductCache(cacheProductList, productUpdateTimeList);
            List<String> needReload = new ArrayList<>();
            needReload.addAll(productIdList);
            List<String> validProductIds = validProductList.stream().map(t -> t.getProductId()).distinct().collect(Collectors.toList());
            needReload.removeAll(validProductIds);
            if (!needReload.isEmpty()) {
                List<Products> reloadProducts = productRepository.getProductsByProductIds(needReload);
                if (reloadProducts != null && !reloadProducts.isEmpty()) {
                    reloadProducts.forEach(t -> cacheManager.put(t.getProductId(), t));
                }
                result.addAll(reloadProducts);
            }
        }
        return result;
    }

    private List<Catalogs> processCatalogCache(List<String> productIdList, List<Catalogs> cacheCatalogList, List<ProductTimeStamp> productUpdateTimeList) {
        List<Catalogs> result = new ArrayList<>();
        if (cacheCatalogList == null || cacheCatalogList.isEmpty()) {
            List<Catalogs> catalogsList = productRepository.getCatalogsByProductIds(productIdList);
            result = catalogsList;
            createCatalogCacheData(result);
            return result;
        } else {
            //过滤有效业务缓存数据
            List<Catalogs> validProductList = filterValidCatalogCache(cacheCatalogList, productUpdateTimeList);
            List<String> needReload = new ArrayList<>();
            needReload.addAll(productIdList);
            List<String> validProductIds = validProductList.stream().map(t -> t.getProductId()).distinct().collect(Collectors.toList());
            needReload.removeAll(validProductIds);
            if (!needReload.isEmpty()) {
                List<Catalogs> reloadProducts = productRepository.getCatalogsByProductIds(needReload);
                if (reloadProducts != null && !reloadProducts.isEmpty()) {
                    reloadProducts.forEach(t -> cacheManager.put(t.getProductId(), t));
                }
                result.addAll(reloadProducts);
            }
        }
        return result;
    }

    private void createCatalogCacheData(List<Catalogs> productsList) {
        Map<String, List<Catalogs>> cacheGroup = productsList.stream().collect(Collectors.groupingBy(Catalogs::getProductId));
        cacheManager.put(cacheGroup);
    }

    private void createProductCacheData(List<Products> productsList) {
        Map<String, List<Products>> cacheGroup = productsList.stream().collect(Collectors.groupingBy(Products::getProductId));
        cacheManager.put(cacheGroup);
    }

    /**
     * 过滤有效商品缓存数据
     *
     * @param catalogList
     * @param productUpdateTimeList
     * @return
     */
    private List<Catalogs> filterValidCatalogCache(List<Catalogs> catalogList, List<ProductTimeStamp> productUpdateTimeList) {
        return catalogList.stream().filter(t -> {
            Long cacheCatalogUp = t.getUpdatetime() != null ? t.getUpdatetime().getTime() : -1L;
            ProductTimeStamp productTimeStamp = productUpdateTimeList.stream().filter(s -> s.getSpid().equals(t.getProductId())).findFirst().orElse(null);
            Long productUp = productTimeStamp != null ? productTimeStamp.getSut().getTime() : 0L;
            return Long.compare(cacheCatalogUp, productUp) == 0;
        }).collect(Collectors.toList());
    }

    /**
     * 过滤有效商品缓存数据
     *
     * @param productList
     * @param productUpdateTimeList
     * @return
     */
    private List<Products> filterValidProductCache(List<Products> productList, List<ProductTimeStamp> productUpdateTimeList) {
        return productList.stream().filter(t -> {
            Long cacheProductUp = t.getUpdatetime() != null ? t.getUpdatetime().getTime() : -1L;
            ProductTimeStamp productTimeStamp = productUpdateTimeList.stream().filter(s -> s.getSpid().equals(t.getProductId())).findFirst().orElse(null);
            Long productUp = productTimeStamp != null ? productTimeStamp.getSut().getTime() : 0L;
            return Long.compare(cacheProductUp, productUp) == 0;
        }).collect(Collectors.toList());
    }

}
