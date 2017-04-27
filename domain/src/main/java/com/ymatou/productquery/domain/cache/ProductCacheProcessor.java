package com.ymatou.productquery.domain.cache;

import com.google.common.cache.CacheStats;
import com.google.common.collect.Lists;
import com.ymatou.productquery.domain.model.ProductTimeStamp;
import com.ymatou.productquery.domain.model.cache.BaseCacheInfo;
import com.ymatou.productquery.domain.model.cache.CacheProductInfo;
import com.ymatou.productquery.infrastructure.util.CacheUtil.CacheManager;
import com.ymatou.productquery.infrastructure.util.LogWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 商品缓存相关
 * Created by chenpengxuan on 2017/4/26.
 */
@Component("productCacheProcessor")
public class ProductCacheProcessor extends BaseCacheProcessor {
    @Autowired
    private LogWrapper logWrapper;

    @Autowired
    private CacheManager cacheManager;

    @Override
    protected CacheStats getCacheStats() {
        return cacheManager.getCacheStats(CacheManager.CacheInfoTypeEnum.PRODUCT);
    }

    @Override
    protected List<BaseCacheInfo> getProductsByProductIds(List<String> productIdList,
                                                          List<ProductTimeStamp> productUpdateTimeList,
                                                          Function<List<String>, List<BaseCacheInfo>> repoFunc) {
        List<CacheProductInfo> cacheInfoList = Lists.newArrayList(cacheManager.get(productIdList,CacheManager.CacheInfoTypeEnum.PRODUCT).values())
                .stream().map(x -> (CacheProductInfo)x).collect(Collectors.toList());

        Map tempProductUpdateTimeMap = productUpdateTimeList.stream()
                .collect(Collectors.toMap(ProductTimeStamp::getProductId,val -> val.getProductUpdateTime(),(key1,key2)->key2));

        cacheInfoList = processCacheInfo(productIdList,cacheInfoList,tempProductUpdateTimeMap,)
    }

    @Override
    protected BaseCacheInfo getProductByProductId(String productId, ProductTimeStamp productUpdateTime) {
        return null;
    }
}
