package com.ymatou.productquery.domain.cache;

import com.google.common.cache.CacheStats;
import com.ymatou.productquery.domain.model.ProductTimeStamp;
import com.ymatou.productquery.domain.model.cache.BaseCacheInfo;
import com.ymatou.productquery.domain.repo.mongorepo.ActivityProductRepository;
import com.ymatou.productquery.domain.repo.mongorepo.LiveProductRepository;
import com.ymatou.productquery.domain.repo.mongorepo.ProductRepository;
import com.ymatou.productquery.infrastructure.config.props.BizProps;
import com.ymatou.productquery.infrastructure.config.props.CacheProps;
import com.ymatou.productquery.infrastructure.util.CacheUtil.CacheManager;
import com.ymatou.productquery.infrastructure.util.LogWrapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by chenpengxuan on 2017/4/26.
 */
public abstract class BaseCacheProcessor {
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
    protected abstract CacheStats getCacheStats();

    /**
     * 根据productid列表查询products
     *
     * @param productIdList
     * @param productUpdateTimeList
     * @return
     */
    protected abstract List<BaseCacheInfo> getProductsByProductIds(List<String> productIdList,
                                                                   List<ProductTimeStamp> productUpdateTimeList,
                                                                   Function<List<String>, List<BaseCacheInfo>> repoFunc);

    /**
     * 根据productId获取product
     *
     * @param productId
     * @param productUpdateTime
     * @return
     */
    protected abstract BaseCacheInfo getProductByProductId(String productId, ProductTimeStamp productUpdateTime,Function<String,BaseCacheInfo>);

    /**
     * 商品缓存数据处理
     *
     * @param productIdList
     * @param cacheInfoList
     * @param productUpdateTimeMap
     * @param repoFunc
     * @return
     */
    protected List<BaseCacheInfo> processCacheInfo(List<String> productIdList,
                                                   List<BaseCacheInfo> cacheInfoList,
                                                   Map<String, Date> productUpdateTimeMap,
                                                   Function<List<String>, List<BaseCacheInfo>> repoFunc,
                                                   Consumer<Map<String, BaseCacheInfo>> cacheSaveFunc
    ) {
        List<BaseCacheInfo> result;
        if (cacheInfoList == null || cacheInfoList.isEmpty()) {
            result = repoFunc.apply(productIdList);
            Map<String, BaseCacheInfo> cacheMap = result.stream().collect(Collectors.toMap(BaseCacheInfo::getProductId, val -> val, (key1, key2) -> key2));
            cacheSaveFunc.accept(cacheMap);
            return result;
        } else {
            //过滤有效业务缓存数据
            List<BaseCacheInfo> validProductList = filterValidCache(cacheInfoList, productUpdateTimeMap);
            result = new ArrayList<>();
            List<String> needReloadCacheIdList = new ArrayList<>();
            needReloadCacheIdList.addAll(productIdList);
            List<String> validProductIds = validProductList.stream().map(t -> t.getProductId()).distinct().collect(Collectors.toList());
            needReloadCacheIdList.removeAll(validProductIds);
            if (!needReloadCacheIdList.isEmpty()) {
                List<BaseCacheInfo> reloadProducts = repoFunc.apply(needReloadCacheIdList);

                if (reloadProducts != null && !reloadProducts.isEmpty()) {
                    Map<String, BaseCacheInfo> cacheInfoMap = reloadProducts.stream()
                            .collect(Collectors.toMap(BaseCacheInfo::getProductId, val -> val, (key1, key2) -> key2));

                    cacheSaveFunc.accept(cacheInfoMap);

                    result.addAll(reloadProducts);
                }
            }
            return result;
        }
    }

    /**
     * 过滤有效缓存数据
     *
     * @param baseInfoList
     * @param productUpdateTimeMap
     * @return
     */
    private  List<BaseCacheInfo> filterValidCache(List<BaseCacheInfo> baseInfoList, Map<String,Date> productUpdateTimeMap) {
        return baseInfoList.stream().filter(t -> {
            Long cacheCatalogUp = t.getUpdateTime() != null ? t.getUpdateTime().getTime() : -1L;
            Long productUp = productUpdateTimeMap != null && productUpdateTimeMap.get(t.getProductId()) != null
                    ? productUpdateTimeMap.get(t.getProductId()).getTime() : 0L;
            return Long.compare(cacheCatalogUp, productUp) == 0;
        }).collect(Collectors.toList());
    }
}
