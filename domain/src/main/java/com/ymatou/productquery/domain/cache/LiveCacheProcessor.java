package com.ymatou.productquery.domain.cache;

import com.google.common.cache.CacheStats;
import com.google.common.collect.Lists;
import com.ymatou.productquery.domain.model.LiveProducts;
import com.ymatou.productquery.domain.model.ProductTimeStamp;
import com.ymatou.productquery.domain.model.cache.CacheLiveProductInfo;
import com.ymatou.productquery.domain.repo.mongorepo.ProductRepository;
import com.ymatou.productquery.domain.repo.mongorepo.ProductTimeStampRepository;
import com.ymatou.productquery.infrastructure.util.CacheUtil.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by chenpengxuan on 2017/5/2.
 */
@Component("liveCacheProcessor")
public class LiveCacheProcessor  extends BaseCacheProcessor<LiveProducts,CacheLiveProductInfo>{
    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductTimeStampRepository productTimeStampRepository;

    /**
     * 根据商品id列表获取商品信息
     * @param productIdList
     * @return
     */
    public List<LiveProducts> getProductInfoByProductIdList(List<String> productIdList){
        List<CacheLiveProductInfo> cacheProductInfoList = Lists.newArrayList
                (cacheManager.get(productIdList, CacheManager.CacheInfoTypeEnum.LIVEPRODUCT).values())
                .stream().map(x -> ((CacheLiveProductInfo)x)).collect(Collectors.toList());

        List<ProductTimeStamp> productTimeStampList = productTimeStampRepository
                .getTimeStampByProductIds(productIdList, Arrays.asList("lut"));

        return processCacheInfo(productIdList,cacheProductInfoList,productTimeStampList);
    }

    @Override
    protected CacheStats getCacheStats() {
        return cacheManager.getCacheStats(CacheManager.CacheInfoTypeEnum.LIVEPRODUCT);
    }

    @Override
    protected List<LiveProducts> processNoneCache(List<String> productIdList) {
        if(productIdList != null && !productIdList.isEmpty()){
            productIdList.removeAll(Collections.singleton(null));
            productIdList = productIdList.stream().distinct().collect(Collectors.toList());
        }

        List<LiveProducts> cacheProductInfoList = productRepository.getLiveProductListByProductIdList(productIdList);

        if(cacheProductInfoList != null && !cacheProductInfoList.isEmpty()){
            Map<String,CacheLiveProductInfo> cacheProductInfoMap = new HashMap<>();
            cacheProductInfoList.forEach(x -> cacheProductInfoMap.put(x.getProductId(),x.convertDtoToCacheData()));
            cacheManager.put(cacheProductInfoMap, CacheManager.CacheInfoTypeEnum.LIVEPRODUCT);
            return cacheProductInfoList;
        }
        return null;
    }

    @Override
    protected List<LiveProducts> processPartialHitCache(List<String> productIdList, List<CacheLiveProductInfo> cacheInfoList, List<ProductTimeStamp> productUpdateTimeList) {
        //过滤有效业务缓存数据
        List<LiveProducts> result = new ArrayList<>();
        List<LiveProducts> validProductList = filterValidCache(cacheInfoList, productUpdateTimeList);
        List<String> needReloadCacheIdList = new ArrayList<>();
        needReloadCacheIdList.addAll(productIdList);
        List<String> validProductIds = validProductList.stream().map(t -> t.getProductId()).distinct().collect(Collectors.toList());
        needReloadCacheIdList.removeAll(validProductIds);

        result.addAll(validProductList);

        if (!needReloadCacheIdList.isEmpty()) {
            List<LiveProducts> reloadProducts = productRepository.getLiveProductListByProductIdList(needReloadCacheIdList);

            if (reloadProducts != null && !reloadProducts.isEmpty()) {
                Map<String, CacheLiveProductInfo> cacheInfoMap = new HashMap<>();

                reloadProducts.forEach(rp -> {
                    CacheLiveProductInfo tempInfo = rp.convertDtoToCacheData();
                    cacheInfoMap.put(tempInfo.getProductId(),tempInfo);
                });
                cacheManager.put(cacheInfoMap, CacheManager.CacheInfoTypeEnum.LIVEPRODUCT);
                result.addAll(reloadProducts);
            }
        }
        return result;
    }

    @Override
    protected List<LiveProducts> filterValidCache(List<CacheLiveProductInfo> cacheInfoList, List<ProductTimeStamp> productUpdateTimeList) {
        return cacheInfoList.stream().filter(t -> {
            Long cacheProductUpdateTimeStamp = t.getUpdateTime() != null ? t.getUpdateTime().getTime() : -1L;

            ProductTimeStamp tempStamp = productUpdateTimeList.stream().filter(pt -> pt.getProductId().equals(t.getProductId())).findAny().orElse(null);

            Long productUpdateTimeStamp = tempStamp != null && tempStamp.getLiveUpdateTime() != null
                    ? tempStamp.getLiveUpdateTime().getTime() : 0L;

            return Long.compare(cacheProductUpdateTimeStamp, productUpdateTimeStamp) == 0;
        }).map(x -> x.convertCacheDataToDto()).collect(Collectors.toList());
    }
}
