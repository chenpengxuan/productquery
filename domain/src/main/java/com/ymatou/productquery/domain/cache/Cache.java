package com.ymatou.productquery.domain.cache;

import com.google.common.cache.CacheStats;
import com.google.common.primitives.Longs;
import com.ymatou.productquery.domain.model.*;
import com.ymatou.productquery.domain.repo.mongorepo.ActivityProductRepository;
import com.ymatou.productquery.domain.repo.mongorepo.LiveProductRepository;
import com.ymatou.productquery.domain.repo.mongorepo.ProductRepository;
import com.ymatou.productquery.infrastructure.config.props.BizProps;
import com.ymatou.productquery.infrastructure.config.props.CacheProps;
import com.ymatou.productquery.infrastructure.util.CacheUtil.CacheManager;
import com.ymatou.productquery.infrastructure.util.LogWrapper;
import com.ymatou.productquery.infrastructure.util.Tuple;
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
    private ActivityProductRepository activityProductRepository;

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

        Map<String, Tuple<ActivityProducts, ActivityProducts>> result;

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
                result = activityProductRepository.getValidAndNextActivityProductByProductId(productIdList, nextActivityExpire);
                if(result != null && !result.isEmpty()){
                    result.values().forEach(x -> {
                        if(x.first != null){
                            cacheList.add(x.first);
                        }
                        if(x.second != null){
                            cacheList.add(x.second);
                        }
                    });
                }
            }
        }
        else {
            result = new HashMap<>();
            List<ActivityProducts> tempActivityProductList = processActivityProductCache(productIdList,cacheList,activityProductStampMap);
            Long now = new Date().getTime();
            tempActivityProductList.forEach(x -> {
                ActivityProducts currentActivityProduct = tempActivityProductList.stream().filter(z -> z.getProductId().equals(x.getProductId())
                && Longs.compare(now,x.getStartTime() != null ? x.getStartTime().getTime():0L) >= 0
                && Longs.compare(now,x.getEndTime() != null ? x.getEndTime().getTime():0L) <= 0).findAny().orElse(null);

                ActivityProducts nextActivityProduct = tempActivityProductList.stream().filter(z -> z.getProductId().equals(x.getProductId())
                && Longs.compare(now,x.getStartTime() != null ? x.getEndTime().getTime():0L) < 0)
                        .min((d1,d2) -> d1.getStartTime().compareTo(d2.getStartTime())).orElse(null);

                Tuple<ActivityProducts,ActivityProducts> tempTuple = new Tuple<>(currentActivityProduct,nextActivityProduct);

                result.put(x.getProductId(),tempTuple);
            });
        }
        return result;
    }


    /**
     * 初始化活动商品缓存
     */
    public int initActivityProductCache() {
        List<ActivityProducts> activityProductList = activityProductRepository.getAllValidActivityProductList();
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
        List<ActivityProducts> newestActivityProductList = activityProductRepository
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
     * @param activityProductList
     * @param activityProductUpdateTimeList
     * @return
     */
    private List<ActivityProducts> processActivityProductCache(List<String> productIdList,List<ActivityProducts> activityProductList,
                                                                                  List<ProductTimeStamp> activityProductUpdateTimeList) {

        Long now = new Date().getTime();

        List<ActivityProducts> validActivityProductsList = new ArrayList<>();
        List<String> needReloadActivityProductIdList = new ArrayList<>();

        if(activityProductList != null && !activityProductList.isEmpty()){
            //当活动商品发生变更时，有可能从mongo中根据限定条件取出来是空，所以先把productId取出来
            activityProductList.removeAll(Collections.singleton(null));

            activityProductList.forEach(x -> {
                ProductTimeStamp tempProductTimeStamp = activityProductUpdateTimeList.stream().filter(z -> z.getProductId()
                        .equals(x.getProductId())).findAny().orElse(null);

                String activityProductId = x.getProductId();
                Long endTime = x.getEndTime()!=null? x.getEndTime().getTime():0L;
                Long activityProductStamp = tempProductTimeStamp != null ?
                        tempProductTimeStamp.getActivityUpdateTime() != null ?tempProductTimeStamp.getActivityUpdateTime().getTime():-1L :-1L;
                Long updateStamp = x.getUpdateTime() != null ? x.getUpdateTime().getTime():0L;
                if (Long.compare(activityProductStamp, updateStamp) == 0) {
                    validActivityProductsList.add(x);
                }
                //过期的活动商品
                if (now > endTime) {
                    cacheManager.deleteActivityProduct(activityProductId);
                }
            });
        }

        needReloadActivityProductIdList.addAll(productIdList);
        List<String> validProductIdList = validActivityProductsList.stream().map(ActivityProducts::getProductId).collect(Collectors.toList());
        needReloadActivityProductIdList.removeAll(validProductIdList);

        if(needReloadActivityProductIdList != null && !needReloadActivityProductIdList.isEmpty()){
            List<ActivityProducts> reloadActivityProductList = activityProductRepository.getActivityProductList(needReloadActivityProductIdList);

            if(reloadActivityProductList != null && !reloadActivityProductList.isEmpty()){
                validActivityProductsList.addAll(reloadActivityProductList);

                Map activityProductMap = reloadActivityProductList.stream()
                        .collect(Collectors.toMap(ActivityProducts::getProductId,val -> val,(key1,key2)->key1));
                cacheManager.putActivityProduct(activityProductMap);
            }
        }
        return validActivityProductsList;
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
            List<Products> validProductList = filterValidCache(cacheProductList, productUpdateTimeList.stream()
                    .collect(Collectors.toMap(ProductTimeStamp::getProductId,val -> val.getProductUpdateTime(),(key1,key2)->key2)));

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
            List<LiveProducts> validProductList = filterValidCache(cacheLiveProductList,  productUpdateTimeList.stream().collect(Collectors
                    .toMap(ProductTimeStamp::getProductId,val -> val.getLiveUpdateTime(),(key1,key2)->key1)));
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
            List<Catalogs> validProductList = filterValidCache(cacheCatalogList, productUpdateTimeList.stream().collect(Collectors
                    .toMap(ProductTimeStamp::getProductId,val -> val.getCatalogUpdateTime(),(key1,key2)->key1)));

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
     * 过滤有效缓存数据
     *
     * @param baseInfoList
     * @param productUpdateTimeMap
     * @return
     */
    private <T extends BaseInfo> List<T> filterValidCache(List<T> baseInfoList, Map<String,Date> productUpdateTimeMap) {
        return baseInfoList.stream().filter(t -> {
            Long cacheCatalogUp = t.getUpdateTime() != null ? t.getUpdateTime().getTime() : -1L;
            Long productUp = productUpdateTimeMap != null && productUpdateTimeMap.get(t.getProductId()) != null
                    ? productUpdateTimeMap.get(t.getProductId()).getTime() : 0L;
            return Long.compare(cacheCatalogUp, productUp) == 0;
        }).collect(Collectors.toList());
    }
}
