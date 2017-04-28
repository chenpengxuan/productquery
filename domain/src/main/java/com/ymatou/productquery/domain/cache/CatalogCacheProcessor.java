package com.ymatou.productquery.domain.cache;

import com.google.common.cache.CacheStats;
import com.google.common.collect.Lists;
import com.ymatou.productquery.domain.model.Catalogs;
import com.ymatou.productquery.domain.model.ProductTimeStamp;
import com.ymatou.productquery.domain.model.cache.CacheProductInfo;
import com.ymatou.productquery.domain.repo.mongorepo.ProductRepository;
import com.ymatou.productquery.domain.repo.mongorepo.ProductTimeStampRepository;
import com.ymatou.productquery.infrastructure.util.CacheUtil.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 规格缓存相关
 * Created by chenpengxuan on 2017/4/28.
 */
@Component("catalogCacheProcessor")
public class CatalogCacheProcessor extends BaseCacheProcessor<Catalogs,CacheProductInfo>{
    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductTimeStampRepository productTimeStampRepository;

    /**
     * 根据规格id列表获取规格信息
     * @param catalogIdList
     * @return
     */
    public List<Catalogs> getCatalogListByCatalogIdList(List<String> catalogIdList){
        if(catalogIdList != null && !catalogIdList.isEmpty()){
            catalogIdList.removeAll(Collections.singleton(null));
            catalogIdList = catalogIdList.stream().distinct().collect(Collectors.toList());
        }
        List<String> productIdList = productRepository.getProductIdsByCatalogIds(catalogIdList);

        List<CacheProductInfo> cacheProductInfoList = Lists.newArrayList
                (cacheManager.get(productIdList, CacheManager.CacheInfoTypeEnum.PRODUCT).values())
                .stream().map(x -> ((CacheProductInfo)x)).collect(Collectors.toList());

        List<ProductTimeStamp> productTimeStampList = productTimeStampRepository
                .getTimeStampByProductIds(productIdList,Arrays.asList("cut"));

        Map<String,Date> productTimeStampMap = new HashMap<>();
        productTimeStampList.forEach(x -> productTimeStampMap.put(x.getProductId(),x.getProductUpdateTime()));

        List<Catalogs> result = processCacheInfo(productIdList,cacheProductInfoList,productTimeStampMap);

        if(result != null && result.isEmpty()){
            List<String> tempCatalogIdList = catalogIdList;
            return result.stream().filter(x -> tempCatalogIdList.contains(x.getCatalogId())).collect(Collectors.toList());
        }
        return null;
    }

    /**
     * 根据商品id列表获取规格列表
     * @param productIdList
     * @return
     */
    public List<Catalogs> getCatalogListByProductIdList(List<String> productIdList){
        List<CacheProductInfo> cacheProductInfoList = Lists.newArrayList
                (cacheManager.get(productIdList, CacheManager.CacheInfoTypeEnum.PRODUCT).values())
                .stream().map(x -> ((CacheProductInfo)x)).collect(Collectors.toList());

        List<ProductTimeStamp> productTimeStampList = productTimeStampRepository
                .getTimeStampByProductIds(productIdList,Arrays.asList("cut"));

        Map<String,Date> productTimeStampMap = new HashMap<>();
        productTimeStampList.forEach(x -> productTimeStampMap.put(x.getProductId(),x.getProductUpdateTime()));

        return processCacheInfo(productIdList,cacheProductInfoList,productTimeStampMap);
    }

    @Override
    protected CacheStats getCacheStats() {
        return cacheManager.getCacheStats(CacheManager.CacheInfoTypeEnum.PRODUCT);
    }

    @Override
    protected List<Catalogs> processNoneCache(List<String> productIdList) {
        if(productIdList != null && !productIdList.isEmpty()){
            productIdList.removeAll(Collections.singleton(null));
            productIdList = productIdList.stream().distinct().collect(Collectors.toList());
        }

        List<Catalogs> cacheCatalogInfoList = productRepository.getCatalogListByProductIdList(productIdList);
        if(cacheCatalogInfoList != null && !cacheCatalogInfoList.isEmpty()){
            Map<String,CacheProductInfo> cacheProductInfoMap = new HashMap<>();
            cacheCatalogInfoList.stream().collect(Collectors.groupingBy(Catalogs::getProductId)).forEach((key,list)->{
                CacheProductInfo cacheProductInfo = new CacheProductInfo();
                cacheProductInfo.setCatalogsList(list);

                cacheProductInfoMap.put(key,cacheProductInfo);
            });

            cacheManager.put(cacheProductInfoMap, CacheManager.CacheInfoTypeEnum.PRODUCT);

            return cacheCatalogInfoList;
        }
        return null;
    }

    @Override
    protected List<Catalogs> processPartialHitCache(List<String> productIdList, List<CacheProductInfo> cacheInfoList, Map<String, Date> productUpdateTimeMap) {
        List<Catalogs> result = new ArrayList<>();
        //过滤有效业务缓存数据
        List<Catalogs> validCatalogList = filterValidCache(cacheInfoList, productUpdateTimeMap);
        List<String> needReloadCacheIdList = new ArrayList<>();
        needReloadCacheIdList.addAll(productIdList);
        List<String> validProductIds = validCatalogList.stream().map(Catalogs::getProductId).distinct().collect(Collectors.toList());
        needReloadCacheIdList.removeAll(validProductIds);

        result.addAll(validCatalogList);

        if (!needReloadCacheIdList.isEmpty()) {
            List<Catalogs> reloadCatalogList = productRepository.getCatalogListByCatalogIdList(needReloadCacheIdList);

            if (reloadCatalogList != null && !reloadCatalogList.isEmpty()) {
                Map<String, CacheProductInfo> cacheInfoMap = new HashMap<>();

                reloadCatalogList.stream().collect(Collectors.groupingBy(Catalogs::getProductId)).forEach((key,rcList) -> {
                    CacheProductInfo tempCacheProductInfo = cacheInfoList.stream().filter(x -> x.getProductId().equals(key))
                            .findAny().orElse(null);

                    if(tempCacheProductInfo == null){
                        tempCacheProductInfo = new CacheProductInfo();
                    }
                    tempCacheProductInfo.setCatalogsList(rcList);
                    cacheInfoMap.put(key,tempCacheProductInfo);
                });
                cacheManager.put(cacheInfoMap, CacheManager.CacheInfoTypeEnum.PRODUCT);

                result.addAll(reloadCatalogList);
            }
        }
        return result;
    }

    @Override
    protected List<Catalogs> filterValidCache(List<CacheProductInfo> cacheInfoList, Map<String, Date> productUpdateTimeMap) {
        List<Catalogs> catalogsList = new ArrayList<>();
        cacheInfoList.forEach(x -> catalogsList.addAll(x.getCatalogsList()));

        return catalogsList.stream().filter(t -> {
            Long cacheCatalogUp = t != null ? t.getUpdateTime().getTime() : -1L;

            Long productUp = productUpdateTimeMap != null && productUpdateTimeMap.get(t.getProductId()) != null
                    ? productUpdateTimeMap.get(t.getProductId()).getTime() : 0L;

            return Long.compare(cacheCatalogUp, productUp) == 0;
        }).collect(Collectors.toList());
    }
}
