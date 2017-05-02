package com.ymatou.productquery.domain.cache;

import com.google.common.cache.CacheStats;
import com.google.common.collect.Lists;
import com.ymatou.productquery.domain.model.ActivityProducts;
import com.ymatou.productquery.domain.model.ProductTimeStamp;
import com.ymatou.productquery.domain.model.cache.CacheActivityProductInfo;
import com.ymatou.productquery.domain.repo.mongorepo.ProductRepository;
import com.ymatou.productquery.domain.repo.mongorepo.ProductTimeStampRepository;
import com.ymatou.productquery.infrastructure.util.CacheUtil.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 活动商品缓存相关
 * Created by chenpengxuan on 2017/4/28.
 */
public class ActivityCacheProcessor extends BaseCacheProcessor<ActivityProducts,CacheActivityProductInfo>{
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductTimeStampRepository productTimeStampRepository;

    @Autowired
    private CacheManager cacheManager;

    /**
     * 根据商品id列表获取活动商品信息列表
     * @param productIdList
     * @return
     */
    public List<ActivityProducts> getActivityProductListByProductIdList(List<String> productIdList) {
        List<CacheActivityProductInfo> cacheActivityProductInfoList = Lists.newArrayList
                (cacheManager.get(productIdList, CacheManager.CacheInfoTypeEnum.ACTIVITYPRODUCT).values())
                .stream().map(x -> ((CacheActivityProductInfo)x)).collect(Collectors.toList());

        List<ProductTimeStamp> productTimeStampList = productTimeStampRepository
                .getTimeStampByProductIds(productIdList, Arrays.asList("aut"));

        Map<String,Date> productTimeStampMap = new HashMap<>();
        productTimeStampList.forEach(x -> productTimeStampMap.put(x.getProductId(),x.getProductUpdateTime()));
        return processCacheInfo(productIdList,cacheActivityProductInfoList,productTimeStampMap);
    }

    @Override
    protected CacheStats getCacheStats() {
        return null;
    }

    @Override
    protected List<ActivityProducts> processNoneCache(List<String> productIdList) {
        if(productIdList != null && !productIdList.isEmpty()){
            productIdList.removeAll(Collections.singleton(null));
            productIdList = productIdList.stream().distinct().collect(Collectors.toList());
        }

        List<ActivityProducts> activityProductsList = productRepository.getActivityProductListByProductIdList(productIdList);
        if(activityProductsList != null && !activityProductsList.isEmpty()){
            Map<String,CacheActivityProductInfo> cacheActivityProductInfoMap = new HashMap<>();
            activityProductsList.forEach(x -> cacheActivityProductInfoMap.put(x.getProductId(),x.convertDtoToCacheData()));
            cacheManager.put(cacheActivityProductInfoMap, CacheManager.CacheInfoTypeEnum.ACTIVITYPRODUCT);
            return activityProductsList;
        }
        return null;
    }

    @Override
    protected List<ActivityProducts> processPartialHitCache(List<String> productIdList, List<CacheActivityProductInfo> cacheInfoList, Map<String, Date> productUpdateTimeMap) {
        //过滤有效业务缓存数据
        List<ActivityProducts> result = new ArrayList<>();
        List<ActivityProducts> validProductList = filterValidCache(cacheInfoList, productUpdateTimeMap);
        List<String> needReloadCacheIdList = new ArrayList<>();
        needReloadCacheIdList.addAll(productIdList);
        List<String> validProductIds = validProductList.stream().map(t -> t.getProductId()).distinct().collect(Collectors.toList());
        needReloadCacheIdList.removeAll(validProductIds);

        result.addAll(validProductList);

        if (!needReloadCacheIdList.isEmpty()) {
            List<ActivityProducts> reloadProducts = productRepository.getActivityProductListByProductIdList(needReloadCacheIdList);

            if (reloadProducts != null && !reloadProducts.isEmpty()) {
                Map<String, CacheActivityProductInfo> cacheInfoMap = new HashMap<>();
                reloadProducts.forEach(rp -> {

                    cacheInfoMap.put(rp.getProductId(),rp.convertDtoToCacheData());
                });
                cacheManager.put(cacheInfoMap, CacheManager.CacheInfoTypeEnum.PRODUCT);
                result.addAll(reloadProducts);
            }
        }
        return result;
    }

    @Override
    protected List<ActivityProducts> filterValidCache(List<CacheActivityProductInfo> cacheInfoList, Map<String, Date> productUpdateTimeMap) {
        return cacheInfoList.stream().filter(t -> {
            Long cacheProductUpdateTimeStamp = t.getUpdateTime() != null ? t.getUpdateTime().getTime() : -1L;

            Long productUpdateTimeStamp = productUpdateTimeMap != null && productUpdateTimeMap.get(t.getProductId()) != null
                    ? productUpdateTimeMap.get(t.getProductId()).getTime() : 0L;

            return Long.compare(cacheProductUpdateTimeStamp, productUpdateTimeStamp) == 0;
        }).map(x -> x.convertCacheDataToDto()).collect(Collectors.toList());
    }
}
