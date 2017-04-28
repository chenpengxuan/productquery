package com.ymatou.productquery.domain.cache;

import com.google.common.cache.CacheStats;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by chenpengxuan on 2017/4/26.
 */
public abstract class BaseCacheProcessor<T,C> {

    /**
     * 获取缓存统计信息
     *
     * @return
     */
    protected abstract CacheStats getCacheStats();

    /**
     * 商品缓存数据处理
     *
     * @param productIdList
     * @param cacheInfoList
     * @param productUpdateTimeMap
     * @return
     */
    protected List<T> processCacheInfo(List<String> productIdList,
                                       List<C> cacheInfoList,
                                       Map<String, Date> productUpdateTimeMap) {
        if (cacheInfoList == null || cacheInfoList.isEmpty()) {
            return processNoneCache(productIdList);
        } else {
            cacheInfoList.removeAll(Collections.singleton(null));
            return processPartialHitCache(productIdList, cacheInfoList, productUpdateTimeMap);
        }
    }

    /**
     * 处理没有缓存命中的情况
     *
     * @return
     */
    protected abstract List<T> processNoneCache(List<String> productIdList);

    /**
     * 处理部分缓存命中的情况
     *
     * @param productIdList
     * @param cacheInfoList
     * @param productUpdateTimeMap
     * @return
     */
    protected abstract List<T> processPartialHitCache(List<String> productIdList,
                                                      List<C> cacheInfoList,
                                                      Map<String, Date> productUpdateTimeMap);

    /**
     * 过滤有效缓存数据
     *
     * @param cacheInfoList
     * @param productUpdateTimeMap
     * @return
     */
    protected abstract List<T> filterValidCache(List<C> cacheInfoList, Map<String, Date> productUpdateTimeMap);
}
