package com.ymatou.productquery.domain.cache;

import com.google.common.cache.CacheStats;
import com.google.common.collect.Lists;
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
 * 商品缓存相关
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
     * 根据商品id列表获取商品信息
     * @param productIdList
     * @return
     */
    public List<Products> getProductInfoByProductIdList(List<String> productIdList){
        List<CacheProductInfo> cacheProductInfoList = Lists.newArrayList
                (cacheManager.get(productIdList, CacheManager.CacheInfoTypeEnum.PRODUCT).values())
                .stream().map(x -> ((CacheProductInfo)x)).collect(Collectors.toList());

        List<ProductTimeStamp> productTimeStampList = productTimeStampRepository
                .getTimeStampByProductIds(productIdList,Arrays.asList("sut"));

        Map<String,Date> productTimeStampMap = new HashMap<>();
        productTimeStampList.forEach(x -> productTimeStampMap.put(x.getProductId(),x.getProductUpdateTime()));
        return processCacheInfo(productIdList,cacheProductInfoList,productTimeStampMap);
    }

    /**
     * 根据商品id获取商品信息
     * @param productId
     * @return
     */
    public Products getProductInfoByProductId(String productId){
        CacheProductInfo cacheProductInfo = cacheManager.get(productId, CacheManager.CacheInfoTypeEnum.PRODUCT);

        ProductTimeStamp productTimeStamp = productTimeStampRepository.getTimeStampByProductId(productId,Arrays.asList("sut"));

        Map<String,Date> productTimeStampMap = productTimeStamp != null ? new HashMap<>():null;
        if(productTimeStampMap != null){
            productTimeStampMap.put(productTimeStamp.getProductId(),productTimeStamp.getProductUpdateTime());
        }
        return processCacheInfo(Arrays.asList(productId),Arrays.asList(cacheProductInfo),productTimeStampMap).stream().findAny().orElse(null);
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
        if(cacheProductInfoList != null && !cacheProductInfoList.isEmpty()){
            Map<String,CacheProductInfo> cacheProductInfoMap = new HashMap<>();
            cacheProductInfoList.forEach(x -> cacheProductInfoMap.put(x.getProductId(),x.convertDtoToCacheData()));
            cacheManager.put(cacheProductInfoMap, CacheManager.CacheInfoTypeEnum.PRODUCT);
            return cacheProductInfoList;
        }
        return null;
    }

    @Override
    protected List<Products> processPartialHitCache(List<String> productIdList,
                                                    List<CacheProductInfo> cacheInfoList,
                                                    Map<String, Date> productUpdateTimeMap) {
        //过滤有效业务缓存数据
        List<Products> result = new ArrayList<>();
        List<Products> validProductList = filterValidCache(cacheInfoList, productUpdateTimeMap);
        List<String> needReloadCacheIdList = new ArrayList<>();
        needReloadCacheIdList.addAll(productIdList);
        List<String> validProductIds = validProductList.stream().map(t -> t.getProductId()).distinct().collect(Collectors.toList());
        needReloadCacheIdList.removeAll(validProductIds);

        result.addAll(validProductList);

        if (!needReloadCacheIdList.isEmpty()) {
            List<Products> reloadProducts = productRepository.getProducts(needReloadCacheIdList);

            if (reloadProducts != null && !reloadProducts.isEmpty()) {
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
        return result;
    }

    @Override
    protected List<Products> filterValidCache(List<CacheProductInfo> cacheInfoList, Map<String, Date> productUpdateTimeMap) {
        return cacheInfoList.stream().filter(t -> {
            Long cacheProductUpdateTimeStamp = t.getUpdateTime() != null ? t.getUpdateTime().getTime() : -1L;

            Long productUpdateTimeStamp = productUpdateTimeMap != null && productUpdateTimeMap.get(t.getProductId()) != null
                    ? productUpdateTimeMap.get(t.getProductId()).getTime() : 0L;

            return Long.compare(cacheProductUpdateTimeStamp, productUpdateTimeStamp) == 0;
        }).map(x -> x.convertCacheDataToDto()).collect(Collectors.toList());
    }
}
