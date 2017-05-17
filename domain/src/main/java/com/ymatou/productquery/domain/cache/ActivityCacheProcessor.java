package com.ymatou.productquery.domain.cache;

import com.google.common.cache.CacheStats;
import com.google.common.collect.Lists;
import com.ymatou.productquery.domain.model.ActivityProducts;
import com.ymatou.productquery.domain.model.ProductTimeStamp;
import com.ymatou.productquery.domain.model.cache.CacheActivityProductInfo;
import com.ymatou.productquery.domain.repo.mongorepo.ProductRepository;
import com.ymatou.productquery.domain.repo.mongorepo.ProductTimeStampRepository;
import com.ymatou.productquery.infrastructure.config.props.CacheProps;
import com.ymatou.productquery.infrastructure.util.CacheUtil.CacheManager;
import com.ymatou.productquery.infrastructure.util.LogWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

/**
 * 活动商品缓存相关
 * Created by chenpengxuan on 2017/4/28.
 */
@Component("activityCacheProcessor")
public class ActivityCacheProcessor extends BaseCacheProcessor<ActivityProducts, CacheActivityProductInfo> {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductTimeStampRepository productTimeStampRepository;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private LogWrapper logWrapper;

    @Autowired
    private CacheProps cacheProps;

    /**
     * 初始化活动商品缓存
     */
    public int initActivityProductCache() {
        List<CacheActivityProductInfo> activityProductList = productRepository.getValidActivityProductList().stream()
                .map(x -> x.convertDtoToCacheData()).collect(Collectors.toList());
        Map<String,CacheActivityProductInfo> cacheActivityProductInfoMap = activityProductList
                .stream()
                .collect(Collectors.toMap(CacheActivityProductInfo::getProductId, y -> y, (key1, key2) -> key2));

        cacheManager.put(cacheActivityProductInfoMap, CacheManager.CacheInfoTypeEnum.ACTIVITYPRODUCT);
        return activityProductList.size();
    }

    /**
     * 检查活动容器size是否已满
     * @return
     */
    private boolean checkActivityCacheIsFull(){
        return cacheProps.getActivityProductCacheSize() <= cacheManager.getActivityProductCacheContainer().size();
    }

    /**
     * 添加活动商品增量信息
     */
    public void addNewestActivityProductCache() {
        ConcurrentMap activityProductCache = cacheManager.getActivityProductCacheContainer();

        List<String> cacheProductIdList = (List<String>) activityProductCache.keySet()
                .stream().map(x -> x.toString()).collect(Collectors.toList());
        List<String> validProductIdList = productRepository.getValidActivityProductIdList();

        List<String> needReloadProductIdList = new ArrayList<>();
        needReloadProductIdList.addAll(validProductIdList);
        needReloadProductIdList.removeAll(cacheProductIdList);

        //获取新增的mongo活动商品信息
        List<ActivityProducts> newestActivityProductList = productRepository
                .getActivityProductList(needReloadProductIdList);

        //批量添加至缓存
        Map tempMap = newestActivityProductList
                .stream()
                .collect(Collectors.toMap(ActivityProducts::getProductId, y -> y, (key1, key2) -> key2));

        cacheManager.put(tempMap, CacheManager.CacheInfoTypeEnum.ACTIVITYPRODUCT);

        logWrapper.recordInfoLog("增量添加活动商品缓存已执行,新增{}条", newestActivityProductList.size());
    }

    /**
     * 根据商品id列表获取活动商品信息列表
     *
     * @param productIdList
     * @return
     */
    public List<ActivityProducts> getActivityProductListByProductIdList(List<String> productIdList) {
        Map cacheActivityProductInfoMap = cacheManager.get(productIdList, CacheManager.CacheInfoTypeEnum.ACTIVITYPRODUCT);
        if(cacheActivityProductInfoMap != null && !cacheActivityProductInfoMap.isEmpty()){
            if(!checkActivityCacheIsFull()){
                List<CacheActivityProductInfo> cacheActivityProductInfoList = (List<CacheActivityProductInfo>) Lists.newArrayList(cacheActivityProductInfoMap.values())
                        .stream().map(x -> x).collect(Collectors.toList());

                List<ProductTimeStamp> productTimeStampList = productTimeStampRepository
                        .getTimeStampByProductIds(productIdList, Arrays.asList("aut"));

                return processCacheInfo(productIdList, cacheActivityProductInfoList, productTimeStampList);
            }
            else{
                logWrapper.recordErrorLog("活动缓存容量已满，需要进行配置扩容并重启服务，当前数据改成从mongo读取，不影响业务");
                return productRepository.getActivityProductListByProductIdList(productIdList);
            }
        }
        return null;
    }

    @Override
    protected CacheStats getCacheStats() {
        return null;
    }

    @Override
    protected List<ActivityProducts> processNoneCache(List<String> productIdList) {
        if (productIdList != null && !productIdList.isEmpty()) {
            productIdList.removeAll(Collections.singleton(null));
            productIdList = productIdList.stream().distinct().collect(Collectors.toList());
        }

        List<ActivityProducts> activityProductsList = productRepository.getActivityProductListByProductIdList(productIdList);
        if (activityProductsList != null && !activityProductsList.isEmpty()) {
            productTimeStampRepository.setActivityProductListUpdateTime(activityProductsList);

            Map<String, CacheActivityProductInfo> cacheActivityProductInfoMap = new HashMap<>();
            activityProductsList.forEach(x -> cacheActivityProductInfoMap.put(x.getProductId(), x.convertDtoToCacheData()));
            cacheManager.put(cacheActivityProductInfoMap, CacheManager.CacheInfoTypeEnum.ACTIVITYPRODUCT);
            return activityProductsList;
        }
        return null;
    }

    @Override
    protected List<ActivityProducts> processPartialHitCache(List<String> productIdList, List<CacheActivityProductInfo> cacheInfoList, List<ProductTimeStamp> productUpdateTimeList) {
        //过滤有效业务缓存数据
        List<ActivityProducts> result = new ArrayList<>();
        List<ActivityProducts> validProductList = filterValidCache(cacheInfoList, productUpdateTimeList);
        List<String> needReloadCacheIdList = new ArrayList<>();
        needReloadCacheIdList.addAll(productIdList);
        List<String> validProductIds = validProductList.stream().map(t -> t.getProductId()).distinct().collect(Collectors.toList());
        needReloadCacheIdList.removeAll(validProductIds);

        result.addAll(validProductList);

        if (!needReloadCacheIdList.isEmpty()) {
            List<ActivityProducts> reloadProducts = productRepository.getActivityProductListByProductIdList(needReloadCacheIdList);

            if (reloadProducts != null && !reloadProducts.isEmpty()) {
                productTimeStampRepository.setActivityProductListUpdateTime(reloadProducts);

                Map<String, CacheActivityProductInfo> cacheInfoMap = new HashMap<>();
                reloadProducts.forEach(rp -> {

                    cacheInfoMap.put(rp.getProductId(), rp.convertDtoToCacheData());
                });
                cacheManager.put(cacheInfoMap, CacheManager.CacheInfoTypeEnum.ACTIVITYPRODUCT);
                result.addAll(reloadProducts);
            }
        }
        return result;
    }

    @Override
    protected List<ActivityProducts> filterValidCache(List<CacheActivityProductInfo> cacheInfoList, List<ProductTimeStamp> productUpdateTimeList) {
        return cacheInfoList.stream().filter(t -> {
            Long cacheProductUpdateTimeStamp = t.getUpdateTime() != null ? t.getUpdateTime().getTime() : -1L;
            ProductTimeStamp tempStamp = productUpdateTimeList.stream().filter(pt -> pt.getProductId().equals(t.getProductId())).findAny().orElse(null);

            Long productUpdateTimeStamp = tempStamp != null && tempStamp.getActivityUpdateTime() != null
                    ? tempStamp.getActivityUpdateTime().getTime() : 0L;

            return Long.compare(cacheProductUpdateTimeStamp, productUpdateTimeStamp) == 0;
        }).map(x -> x.convertCacheDataToDto()).collect(Collectors.toList());
    }
}
