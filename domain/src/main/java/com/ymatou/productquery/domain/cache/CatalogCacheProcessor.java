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
public class CatalogCacheProcessor extends BaseCacheProcessor<Catalogs, CacheProductInfo> {
    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductTimeStampRepository productTimeStampRepository;

    /**
     * 根据规格id列表获取规格信息
     *
     * @param catalogIdList
     * @return
     */
    public List<Catalogs> getCatalogListByCatalogIdList(List<String> catalogIdList) {
        if (catalogIdList != null && !catalogIdList.isEmpty()) {
            catalogIdList.removeAll(Collections.singleton(null));
            catalogIdList = catalogIdList.stream().distinct().collect(Collectors.toList());
        }
        List<String> productIdList = productRepository.getProductIdsByCatalogIds(catalogIdList);
        if(productIdList != null && !productIdList.isEmpty()){
            List<CacheProductInfo> cacheProductInfoList = Lists.newArrayList
                    (cacheManager.get(productIdList, CacheManager.CacheInfoTypeEnum.PRODUCT).values())
                    .stream().map(x -> ((CacheProductInfo) x)).collect(Collectors.toList());

            List<ProductTimeStamp> productTimeStampList = productTimeStampRepository
                    .getTimeStampByProductIds(productIdList, Arrays.asList("cut"));

            Map<String, Date> productTimeStampMap = new HashMap<>();
            productTimeStampList.forEach(x -> productTimeStampMap.put(x.getProductId(), x.getCatalogUpdateTime()));

            List<Catalogs> result = processCacheInfo(catalogIdList, cacheProductInfoList, productTimeStampList);

            if (result != null && !result.isEmpty()) {
                List<String> tempCatalogIdList = catalogIdList;
                return result.stream().filter(x -> tempCatalogIdList.contains(x.getCatalogId())).collect(Collectors.toList());
            }
        }
        return null;
    }

    /**
     * 根据规格id列表获取对应的每个商品的规格数量
     * @param catalogIdList
     * @return
     */
    public Map<String,Integer> getCatalogNumByCatalogIdList(List<String> catalogIdList){
        Map<String,Integer> result = new HashMap<>();

        if (catalogIdList != null && !catalogIdList.isEmpty()) {
            catalogIdList.removeAll(Collections.singleton(null));
            catalogIdList = catalogIdList.stream().distinct().collect(Collectors.toList());
        }
        List<String> productIdList = productRepository.getProductIdsByCatalogIds(catalogIdList);
        List<CacheProductInfo> cacheProductInfoList = Lists.newArrayList
                (cacheManager.get(productIdList, CacheManager.CacheInfoTypeEnum.PRODUCT).values())
                .stream().map(x -> ((CacheProductInfo) x)).collect(Collectors.toList());
        List<ProductTimeStamp> productTimeStampList = productTimeStampRepository
                .getTimeStampByProductIds(productIdList, Arrays.asList("cut"));

        List<String> needReloadProductIdList = new ArrayList<>();
        if(cacheProductInfoList == null || cacheProductInfoList.isEmpty()){
            needReloadProductIdList.addAll(productIdList);
        }
        else{
            List<Catalogs> validCatalogList = filterValidCache(cacheProductInfoList,productTimeStampList);

            List<String> validProductIdList = validCatalogList.stream().map(Catalogs::getProductId).collect(Collectors.toList());

            needReloadProductIdList.addAll(productIdList);
            needReloadProductIdList.removeAll(validProductIdList);

            validCatalogList.stream().collect(Collectors.groupingBy(Catalogs::getProductId)).forEach((key,val) -> result.put(key,val.size()));
        }

        result.putAll(productRepository.getCatalogCountList(needReloadProductIdList));

        return result;
    }

    /**
     * 根据商品id列表获取规格列表
     *
     * @param productIdList
     * @return
     */
    public List<Catalogs> getCatalogListByProductIdList(List<String> productIdList) {
        List<CacheProductInfo> cacheProductInfoList = Lists.newArrayList
                (cacheManager.get(productIdList, CacheManager.CacheInfoTypeEnum.PRODUCT).values())
                .stream().map(x -> ((CacheProductInfo) x)).collect(Collectors.toList());

        List<ProductTimeStamp> productTimeStampList = productTimeStampRepository
                .getTimeStampByProductIds(productIdList, Arrays.asList("cut"));

        return processCacheInfo(productIdList, cacheProductInfoList, productTimeStampList);
    }

    @Override
    protected CacheStats getCacheStats() {
        return cacheManager.getCacheStats(CacheManager.CacheInfoTypeEnum.PRODUCT);
    }

    @Override
    protected List<Catalogs> processNoneCache(List<String> catalogIdList) {
        if (catalogIdList != null && !catalogIdList.isEmpty()) {
            catalogIdList.removeAll(Collections.singleton(null));
            catalogIdList = catalogIdList.stream().distinct().collect(Collectors.toList());
        }

        List<Catalogs> cacheCatalogInfoList = productRepository.getCatalogListByCatalogIdList(catalogIdList);
        if (cacheCatalogInfoList != null && !cacheCatalogInfoList.isEmpty()) {
            productTimeStampRepository.setCatalogListUpdateTime(cacheCatalogInfoList);

            Map<String, CacheProductInfo> cacheProductInfoMap = new HashMap<>();
            cacheCatalogInfoList.stream().collect(Collectors.groupingBy(Catalogs::getProductId)).forEach((key, list) -> {
                CacheProductInfo cacheProductInfo = new CacheProductInfo();
                cacheProductInfo.setCatalogsList(list);

                cacheProductInfoMap.put(key, cacheProductInfo);
            });

            //针对规格的缓存只用不写
            //cacheManager.put(cacheProductInfoMap, CacheManager.CacheInfoTypeEnum.PRODUCT);
            return cacheCatalogInfoList;
        }
        return null;
    }

    @Override
    protected List<Catalogs> processPartialHitCache(List<String> catalogIdList, List<CacheProductInfo> cacheInfoList, List<ProductTimeStamp> productUpdateTimeList) {
        List<Catalogs> result = new ArrayList<>();
        //过滤有效业务缓存数据
        List<Catalogs> validCatalogList = filterValidCache(cacheInfoList, productUpdateTimeList);
        List<String> needReloadCacheIdList = new ArrayList<>();
        needReloadCacheIdList.addAll(catalogIdList);
        List<String> validCatalogIdList = validCatalogList.stream().map(Catalogs::getCatalogId).distinct().collect(Collectors.toList());
        needReloadCacheIdList.removeAll(validCatalogIdList);

        result.addAll(validCatalogList);

        if (!needReloadCacheIdList.isEmpty()) {
            List<Catalogs> reloadCatalogList = productRepository.getCatalogListByCatalogIdList(needReloadCacheIdList);

            if (reloadCatalogList != null && !reloadCatalogList.isEmpty()) {
                productTimeStampRepository.setCatalogListUpdateTime(reloadCatalogList);
                result.addAll(reloadCatalogList);
            }
        }
        return result;
    }

    @Override
    protected List<Catalogs> filterValidCache(List<CacheProductInfo> cacheInfoList, List<ProductTimeStamp> productUpdateTimeList) {
        List<Catalogs> catalogsList = new ArrayList<>();

        if(cacheInfoList != null && !cacheInfoList.isEmpty()){
            cacheInfoList.forEach(x -> {
                if(x.getCatalogsList() != null && !x.getCatalogsList().isEmpty()){
                    catalogsList.addAll(x.getCatalogsList());
                }
                });

            return catalogsList.stream().filter(t -> {
                Long cacheCatalogUp = t != null ? t.getUpdateTime().getTime() : -1L;
                ProductTimeStamp tempStamp = productUpdateTimeList.stream().filter(pt->pt.getProductId().equals(t.getProductId())).findAny().orElse(null);

                Long productUp = tempStamp != null && tempStamp.getCatalogUpdateTime() != null
                        ? tempStamp.getCatalogUpdateTime().getTime() : 0L;

                return Long.compare(cacheCatalogUp, productUp) == 0;
            }).collect(Collectors.toList());
        }
       return null;
    }
}
