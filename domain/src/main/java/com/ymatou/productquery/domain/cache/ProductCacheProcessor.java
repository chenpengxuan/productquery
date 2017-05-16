package com.ymatou.productquery.domain.cache;

import com.google.common.cache.CacheStats;
import com.google.common.collect.Lists;
import com.ymatou.productquery.domain.model.Catalogs;
import com.ymatou.productquery.domain.model.ProductTimeStamp;
import com.ymatou.productquery.domain.model.Products;
import com.ymatou.productquery.domain.model.cache.CacheProductInfo;
import com.ymatou.productquery.domain.repo.mongorepo.ProductRepository;
import com.ymatou.productquery.domain.repo.mongorepo.ProductTimeStampRepository;
import com.ymatou.productquery.infrastructure.util.CacheUtil.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 商品缓存相关1
 * Created by chenpengxuan on 2017/4/26.
 */
@Component("productCacheProcessor")
public class ProductCacheProcessor extends BaseCacheProcessor<Products,CacheProductInfo> {

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductTimeStampRepository productTimeStampRepository;

    /**
     * 根据商品id列表获取商品信息1
     * @param productIdList
     * @return
     */
    public List<Products> getProductInfoByProductIdList(List<String> productIdList){
        List<CacheProductInfo> cacheProductInfoList = Lists.newArrayList
                (cacheManager.get(productIdList, CacheManager.CacheInfoTypeEnum.PRODUCT).values())
                .stream().map(x -> ((CacheProductInfo)x)).collect(Collectors.toList());

        List<ProductTimeStamp> productTimeStampList = productTimeStampRepository
                .getTimeStampByProductIds(productIdList,Arrays.asList("sut","cut"));

        return processCacheInfo(productIdList,cacheProductInfoList,productTimeStampList);
    }

    /**
     * 根据商品id获取商品信息
     * @param productId
     * @return
     */
    public Products getProductInfoByProductId(String productId){
        CacheProductInfo cacheProductInfo = cacheManager.get(productId, CacheManager.CacheInfoTypeEnum.PRODUCT);

        ProductTimeStamp productTimeStamp = productTimeStampRepository.getTimeStampByProductId(productId,Arrays.asList("sut","cut"));

        return processCacheInfo(Arrays.asList(productId),Arrays.asList(cacheProductInfo),Arrays.asList(productTimeStamp)).stream().findAny().orElse(null);
    }


    @Override
    protected CacheStats getCacheStats() {
        return cacheManager.getCacheStats(CacheManager.CacheInfoTypeEnum.PRODUCT);
    }

    @Override
    protected List<Products> processNoneCache(List<String> productIdList) {
        if(productIdList != null && !productIdList.isEmpty()){
            productIdList.removeAll(Collections.singleton(null));
            productIdList = productIdList.stream().distinct().collect(Collectors.toList());
        }

        List<Products> cacheProductInfoList = productRepository.getProductListByProductIdList(productIdList);
        List<Catalogs> cacheCatalogsInfoList = productRepository.getCatalogListByProductIdList(productIdList);

        productTimeStampRepository.setProductListUpdateTime(cacheProductInfoList);
        productTimeStampRepository.setCatalogListUpdateTime(cacheCatalogsInfoList);

        if(cacheProductInfoList != null && !cacheProductInfoList.isEmpty()){
            Map<String,CacheProductInfo> cacheProductInfoMap = new HashMap<>();

            cacheProductInfoList.forEach(x -> {
                List<Catalogs> tempCatalogList = cacheCatalogsInfoList.stream().filter(z -> z.getProductId().equals(x.getProductId())).collect(Collectors.toList());

                CacheProductInfo cacheProductInfo = x.convertDtoToCacheData();
                cacheProductInfo.setCatalogsList(tempCatalogList);
                cacheProductInfoMap.put(x.getProductId(),x.convertDtoToCacheData());
            });
            cacheManager.put(cacheProductInfoMap, CacheManager.CacheInfoTypeEnum.PRODUCT);
            return cacheProductInfoList;
        }
        return null;
    }

    @Override
    protected List<Products> processPartialHitCache(List<String> productIdList,
                                                    List<CacheProductInfo> cacheInfoList,
                                                    List<ProductTimeStamp> productUpdateTimeList) {
        //过滤有效业务缓存数据
        List<Products> result = new ArrayList<>();

        List<Products> validProductList = filterValidCache(cacheInfoList, productUpdateTimeList);
        List<Catalogs> validCatalogList = filterValidCatalogCache(cacheInfoList,productUpdateTimeList);

        List<String> needReloadCacheIdList = new ArrayList<>();
        List<String> needReloadCacheCatalogIdList = new ArrayList<>();
        needReloadCacheIdList.addAll(productIdList);
        List<String> validProductIds = validProductList.stream().map(t -> t.getProductId()).distinct().collect(Collectors.toList());
        needReloadCacheIdList.removeAll(validProductIds);

        result.addAll(validProductList);

        if (!needReloadCacheIdList.isEmpty()) {
            List<Products> reloadProducts = productRepository.getProductListByProductIdList(needReloadCacheIdList);

            if (reloadProducts != null && !reloadProducts.isEmpty()) {
                productTimeStampRepository.setProductListUpdateTime(reloadProducts);

                Map<String, CacheProductInfo> cacheInfoMap = new HashMap<>();

                reloadProducts.forEach(rp -> {
                    CacheProductInfo tempCacheProductInfo = cacheInfoList.stream().filter(x -> x.getProductId().equals(rp.getProductId()))
                            .findAny().orElse(null);

                    CacheProductInfo tempInfo = rp.convertDtoToCacheData();
                    //如果缓存中存在缓存数据,则只更新局部
                    if(tempCacheProductInfo != null){
                        tempInfo.setCatalogsList(tempCacheProductInfo.getCatalogsList());
                    }
                    cacheInfoMap.put(tempInfo.getProductId(),tempInfo);
                });
                cacheManager.put(cacheInfoMap, CacheManager.CacheInfoTypeEnum.PRODUCT);
                result.addAll(reloadProducts);
            }
        }

        List<String> allCatalogIdList = new ArrayList<>();

        result.forEach(x -> {
            if(x.getCatalogsList() != null && !x.getCatalogsList().isEmpty()){
                x.getCatalogsList().forEach(xx -> allCatalogIdList.add(xx.getCatalogId()));
            }
        });

        needReloadCacheCatalogIdList.addAll(allCatalogIdList);
        needReloadCacheCatalogIdList.removeAll(validCatalogList.stream().map(Catalogs::getCatalogId).collect(Collectors.toList()));

        if(needReloadCacheCatalogIdList != null && !needReloadCacheCatalogIdList.isEmpty()){
            List<Catalogs> reloadCatalogs = productRepository.getCatalogListByCatalogIdList(needReloadCacheCatalogIdList);

            Map<String, CacheProductInfo> cacheInfoMap = new HashMap<>();

            if(reloadCatalogs != null && !reloadCatalogs.isEmpty()){
                productTimeStampRepository.setCatalogListUpdateTime(reloadCatalogs);

                reloadCatalogs.stream().collect(Collectors.groupingBy(Catalogs::getProductId)).forEach((key,valList)->{
                    CacheProductInfo tempCacheProductInfo = cacheManager.get(key, CacheManager.CacheInfoTypeEnum.PRODUCT);

                    if(tempCacheProductInfo != null){
                        tempCacheProductInfo.setCatalogsList(valList);
                        cacheInfoMap.put(tempCacheProductInfo.getProductId(),tempCacheProductInfo);
                    }
                });
                cacheManager.put(cacheInfoMap, CacheManager.CacheInfoTypeEnum.PRODUCT);
            }

        }
        return result;
    }

    @Override
    protected List<Products> filterValidCache(List<CacheProductInfo> cacheInfoList, List<ProductTimeStamp> productUpdateTimeList) {
        if(cacheInfoList != null && !cacheInfoList.isEmpty()){
            List<Products> validProductList = cacheInfoList.stream().filter(t -> {
                Long cacheProductUpdateTimeStamp = t.getUpdateTime() != null ? t.getUpdateTime().getTime() : -1L;

                ProductTimeStamp tempStamp = productUpdateTimeList.stream().filter(pt -> pt.getProductId().equals(t.getProductId())).findAny().orElse(null);
                Long productUpdateTimeStamp = tempStamp != null && tempStamp.getProductUpdateTime() != null
                        ? tempStamp.getProductUpdateTime().getTime() : 0L;

                return Long.compare(cacheProductUpdateTimeStamp, productUpdateTimeStamp) == 0;
            }).map(x -> x.convertCacheDataToDto()).collect(Collectors.toList());

            return validProductList;
        }

        return null;
    }

    /**
     * 过滤有效规格列表
     * @param cacheInfoList
     * @param productUpdateTimeList
     * @return
     */
    protected List<Catalogs> filterValidCatalogCache(List<CacheProductInfo> cacheInfoList, List<ProductTimeStamp> productUpdateTimeList) {
        if(cacheInfoList != null && !cacheInfoList.isEmpty()){
            List<Catalogs> cacheCatalogList = new ArrayList<>();
            List<Catalogs> finalCacheCatalogList = cacheCatalogList;
            cacheInfoList.forEach(x -> {
                if(x.getCatalogsList() != null){
                    finalCacheCatalogList.addAll(x.getCatalogsList());
                }
            });
            //由于购物车场景对应的接口需要使用规格部分的缓存，所以这里需要对有效商品的规格部分再次进行有效性检查
            cacheCatalogList = finalCacheCatalogList.stream().filter(t -> {
                Long cacheCatalogUpdateTimeStamp = t != null ? t.getUpdateTime().getTime() : -1L;

                ProductTimeStamp tempStamp = productUpdateTimeList.stream().filter(pt -> pt.getProductId().equals(t.getProductId())).findAny().orElse(null);

                Long catalogUpdateTimeStamp =  tempStamp != null && tempStamp.getCatalogUpdateTime() != null
                        ? tempStamp.getCatalogUpdateTime().getTime() : 0L;

                return Long.compare(cacheCatalogUpdateTimeStamp, catalogUpdateTimeStamp) == 0;
            }).collect(Collectors.toList());

            return cacheCatalogList;
        }
       return null;
    }
}
