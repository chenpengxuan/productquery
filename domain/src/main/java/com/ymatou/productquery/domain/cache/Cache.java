package com.ymatou.productquery.domain.cache;

import com.google.common.cache.CacheStats;
import com.ymatou.productquery.domain.model.*;
import com.ymatou.productquery.domain.repo.mongorepo.ActivityProdutRepository;
import com.ymatou.productquery.domain.repo.mongorepo.LiveProductRepository;
import com.ymatou.productquery.domain.repo.mongorepo.ProductRepository;
import com.ymatou.productquery.infrastructure.config.props.BizProps;
import com.ymatou.productquery.infrastructure.config.props.CacheProps;
import com.ymatou.productquery.infrastructure.util.CacheUtil.CacheManager;
import com.ymatou.productquery.infrastructure.util.LogWrapper;
import com.ymatou.productquery.infrastructure.util.Tuple;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    @Autowired
    private LiveProductRepository liveProductRepository;

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
     * 根据productid列表查询liveproducts
     *
     * @param productIdList
     * @param productUpdateTimeList
     * @return
     */
    public List<LiveProducts> getLiveProductsByProductIds(List<String> productIdList, List<ProductTimeStamp> productUpdateTimeList) {
        productIdList = productIdList.stream().distinct().collect(Collectors.toList());

        List<LiveProducts> result = cacheManager.get(productIdList).values().stream().map(t -> (LiveProducts) t).collect(Collectors.toList());

        return processLiveProductCache(productIdList, result, productUpdateTimeList);
    }

    /**
     * 获取活动商品信息列表
     * ActivityProducts缓存放的是正在进行以及即将进行的活动商品
     *
     * @param productIdList
     * @param activityProductStampMap
     * @param nextActivityExpire
     * @return
     */
    public Map<String, Tuple<ActivityProducts, ActivityProducts>> getActivityProductList(List<String> productIdList,
                                                                                         List<ProductTimeStamp> activityProductStampMap, int nextActivityExpire) {
        productIdList = productIdList
                .stream()
                .distinct()
                .collect(Collectors.toList());

        //从缓存中获取数据
        List<ActivityProducts> cacheList = cacheManager.getActivityProduct(productIdList);
        if (cacheList != null && !cacheList.isEmpty()) {
            //针对Lists.newArrayList创建的列表 排除空元素
            cacheList.removeAll(Collections.singleton(null));
        }

        //如果缓存为空 则认为都不是活动商品
        if (cacheList == null || cacheList.isEmpty()) {
            //如果缓存为空，但是缓存容器没有满的情况下则认为不是活动商品
            if (cacheManager.getActivityProductCacheFactory().size() < cacheProps.getActivityProductCacheSize()) {
                return null;
            } else {
                logWrapper.recordErrorLog("活动商品缓存size需要扩容，超出容量的活动商品已改为从mongo查询，不影响正常业务");
                return activityProdutRepository.getValidAndNextActivityProductByProductId(productIdList, nextActivityExpire);
            }
        } else {
            Map<String, Tuple<ActivityProducts, ActivityProducts>> stringTupleMap = new HashMap<>();
            cacheList.forEach(c -> {
                stringTupleMap.put(c.getProductId(), processActivityProductCache(c, activityProductStampMap.stream()
                        .filter(t -> t.getSpid().equals(c.getProductId())).findFirst().orElse(null), nextActivityExpire));

            });
            return stringTupleMap;
        }
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

    /**
     * 缓存活动商品数据处理逻辑
     *
     * @param activityProduct
     * @return
     */
    private Tuple<ActivityProducts, ActivityProducts> processActivityProductCache(ActivityProducts activityProduct,
                                                                                  ProductTimeStamp activityProductUpdateTime, int nextActivityExpire) {
        Tuple<ActivityProducts, ActivityProducts> result = new Tuple(null, null);
        Long startTime = activityProduct.getStartTime().getTime();
        Long endTime = activityProduct.getEndTime().getTime();
        Long now = new Date().getTime();
        Long updateStamp = activityProductUpdateTime != null ? activityProductUpdateTime.getAut().getTime() : 0L;
        Long activityProductStamp = activityProduct.getUpdateTime() != null
                ? activityProduct.getUpdateTime().getTime() : -1L;
        //当活动商品发生变更时，有可能从mongo中根据限定条件取出来是空，所以先把productId取出来
        String activityProductId = activityProduct.getProductId();
        if (Long.compare(activityProductStamp, updateStamp) != 0) {
            result = activityProdutRepository.getValidAndNextActivityProductByProductId(activityProduct.getProductId(), nextActivityExpire);

            if (activityProduct != null) {
                cacheManager.putActivityProduct(activityProduct.getProductId(), result);
                startTime = activityProduct.getStartTime().getTime();
                endTime = activityProduct.getEndTime().getTime();
            }
        }

        //过期的活动商品
        if (now > endTime) {
            cacheManager.deleteActivityProduct(activityProductId);
            return null;
        }
        //活动商品数据发生变化，取数据重新刷缓存
        else if (now < startTime) {
            return null;
        }
        return result;
    }

    /**
     * 商品缓存数据处理
     *
     * @param productIdList
     * @param cacheProductList
     * @param productUpdateTimeList
     * @return
     */
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

    /**
     * 直播商品缓存数据处理
     *
     * @param productIdList
     * @param cacheLiveProductList
     * @param productUpdateTimeList
     * @return
     */
    private List<LiveProducts> processLiveProductCache(List<String> productIdList, List<LiveProducts> cacheLiveProductList, List<ProductTimeStamp> productUpdateTimeList) {
        List<LiveProducts> result = new ArrayList<>();
        if (cacheLiveProductList == null || cacheLiveProductList.isEmpty()) {
            List<LiveProducts> productsList = liveProductRepository.getLiveProductList(productIdList);
            result = productsList;
            createLiveProductCacheData(result);
            return result;
        } else {
            //过滤有效业务缓存数据
            List<LiveProducts> validProductList = filterValidLiveProductCache(cacheLiveProductList, productUpdateTimeList);
            List<String> needReload = new ArrayList<>();
            needReload.addAll(productIdList);
            List<String> validProductIds = validProductList.stream().map(t -> t.getProductId()).distinct().collect(Collectors.toList());
            needReload.removeAll(validProductIds);
            if (!needReload.isEmpty()) {
                List<LiveProducts> reloadProducts = liveProductRepository.getLiveProductList(needReload);
                if (reloadProducts != null && !reloadProducts.isEmpty()) {
                    reloadProducts.forEach(t -> cacheManager.put(t.getProductId(), t));
                }
                result.addAll(reloadProducts);
            }
        }
        return result;
    }


    /**
     * 规格缓存数据处理
     *
     * @param productIdList
     * @param cacheCatalogList
     * @param productUpdateTimeList
     * @return
     */
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

    private void createLiveProductCacheData(List<LiveProducts> liveProductsList) {
        Map<String, List<LiveProducts>> cacheGroup = liveProductsList.stream().collect(Collectors.groupingBy(LiveProducts::getProductId));
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

    /**
     * 过滤有效商品缓存数据
     *
     * @param liveProductsList
     * @param productUpdateTimeList
     * @return
     */
    private List<LiveProducts> filterValidLiveProductCache(List<LiveProducts> liveProductsList, List<ProductTimeStamp> productUpdateTimeList) {
        return liveProductsList.stream().filter(t -> {
            Long cacheLiveProductUp = t.getUpdatetime() != null ? t.getUpdatetime().getTime() : -1L;
            ProductTimeStamp productTimeStamp = productUpdateTimeList.stream().filter(s -> s.getSpid().equals(t.getProductId())).findFirst().orElse(null);
            Long productUp = productTimeStamp != null ? productTimeStamp.getSut().getTime() : 0L;
            return Long.compare(cacheLiveProductUp, productUp) == 0;
        }).collect(Collectors.toList());
    }

}
