package com.ymatou.productquery.infrastructure.util.CacheUtil;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheStats;
import com.ymatou.productquery.infrastructure.config.props.CacheProps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhangyong on 2017/4/6.
 */
@Component
@DependsOn({"disconfMgrBean2"})
public class CacheManager {

    /**
     * 缓存数据类型
     */
    public enum CacheInfoTypeEnum {
        /**
         * 普通商品
         */
        PRODUCT,

        /**
         * 直播商品
         */
        LIVEPRODUCT,

        /**
         * 活动商品
         */
        ACTIVITYPRODUCT
    }

    @Autowired
    private CacheProps cacheProps;

    private Cache productCacheContainer;

    private Cache liveProductCacheContainer;

    private ConcurrentMap activityProductCacheContainer;

    private static final int CACHE_SIZE_UNIT = 10000;

    /**
     * 预期活动商品缓存数量
     */
    private volatile int expectActivityCacheSize;

    @PostConstruct
    public void init() {
        switch (CacheTypeEnum.valueOf(cacheProps.getCacheType().toUpperCase())) {
            case GUAVACACHE:
                productCacheContainer = CacheBuilder.newBuilder()
                        .maximumSize(cacheProps.getCacheSize() * CACHE_SIZE_UNIT)
                        .expireAfterAccess(cacheProps.getExpireTime(), TimeUnit.HOURS)
                        .concurrencyLevel(cacheProps.getWriteConcurrencyNum())
                        .recordStats()
                        .build();
                liveProductCacheContainer = CacheBuilder.newBuilder()
                        .maximumSize(cacheProps.getCacheSize() * CACHE_SIZE_UNIT)
                        .expireAfterAccess(cacheProps.getExpireTime(), TimeUnit.HOURS)
                        .concurrencyLevel(cacheProps.getWriteConcurrencyNum())
                        .recordStats()
                        .build();
                activityProductCacheContainer = new ConcurrentHashMap(cacheProps.getActivityProductCacheSize());
                break;
            case EHCACHE:
                break;
            default:
                break;
        }
    }

    /**
     * 获取缓存统计信息
     *
     * @return
     */
    public CacheStats getCacheStats(CacheInfoTypeEnum cacheInfoTypeEnum) {
        switch (cacheInfoTypeEnum) {
            case PRODUCT:
                return productCacheContainer.stats();
            case LIVEPRODUCT:
                return liveProductCacheContainer.stats();
            case ACTIVITYPRODUCT:
                return null;
            default:
                return null;
        }
    }

    /**
     * 获取活动商品缓存
     * @return
     */
    public ConcurrentMap getActivityProductCacheContainer(){
        return activityProductCacheContainer;
    }

    /**
     * 获取单个key的缓存
     *
     * @param cacheKey
     * @param <V>
     */
    public <V> V get(String cacheKey, CacheInfoTypeEnum cacheInfoTypeEnum) {
        switch (cacheInfoTypeEnum) {
            case PRODUCT:
                return Optional.ofNullable((V) productCacheContainer.getIfPresent(cacheKey)).orElse(null);
            case LIVEPRODUCT:
                return Optional.ofNullable((V) liveProductCacheContainer.getIfPresent(cacheKey)).orElse(null);
            case ACTIVITYPRODUCT:
                return Optional.ofNullable((V) activityProductCacheContainer.get(cacheKey)).orElse(null);
            default:
                return null;
        }
    }

    /**
     * 获取多个key的缓存
     * @param cacheKeyList
     * @param cacheInfoTypeEnum
     * @param <V>
     * @return
     */
    public <V> Map<String,V> get(List<String> cacheKeyList, CacheInfoTypeEnum cacheInfoTypeEnum) {
        switch (cacheInfoTypeEnum) {
            case PRODUCT:
                return Optional.ofNullable((Map<String,V>) productCacheContainer.getAllPresent(cacheKeyList)).orElse(null);
            case LIVEPRODUCT:
                return Optional.ofNullable((Map<String,V>) liveProductCacheContainer.getAllPresent(cacheKeyList)).orElse(null);
            case ACTIVITYPRODUCT:
                return Optional.ofNullable((Map<String,V>) activityProductCacheContainer.get(cacheKeyList)).orElse(null);
            default:
                return null;
        }
    }

    /**
     * 删除缓存
     * @param cacheKey
     */
    public void delete(String cacheKey, CacheInfoTypeEnum cacheInfoTypeEnum){
        switch (cacheInfoTypeEnum) {
            case PRODUCT:
                productCacheContainer.invalidate(cacheKey);
                break;
            case LIVEPRODUCT:
                liveProductCacheContainer.invalidate(cacheKey);
                break;
            case ACTIVITYPRODUCT:
                activityProductCacheContainer.remove(cacheKey);
                break;
            default:
                break;
        }
    }

    /**
     * 删除缓存
     * @param cacheKeyList
     * @param cacheInfoTypeEnum
     */
    public void delete(List<String> cacheKeyList, CacheInfoTypeEnum cacheInfoTypeEnum){
        switch (cacheInfoTypeEnum) {
            case PRODUCT:
                productCacheContainer.invalidateAll(cacheKeyList);
                break;
            case LIVEPRODUCT:
                liveProductCacheContainer.invalidateAll(cacheKeyList);
                break;
            case ACTIVITYPRODUCT:
                cacheKeyList.forEach(x -> activityProductCacheContainer.remove(x));
                break;
            default:
                break;
        }
    }

    /**
     * 插入缓存
     * @param cacheKey
     * @param cacheInfo
     * @param cacheInfoTypeEnum
     * @param <V>
     */
    public <V> void put(String cacheKey,V cacheInfo, CacheInfoTypeEnum cacheInfoTypeEnum){
        switch (cacheInfoTypeEnum) {
            case PRODUCT:
                productCacheContainer.put(cacheKey,cacheInfo);
                break;
            case LIVEPRODUCT:
                liveProductCacheContainer.put(cacheKey,cacheInfo);
                break;
            case ACTIVITYPRODUCT:
                synchronized (this) {
                    expectActivityCacheSize = activityProductCacheContainer.size() + 1;
                }
                if (expectActivityCacheSize <= cacheProps.getActivityProductCacheSize()) {
                    activityProductCacheContainer.putIfAbsent(cacheKey, cacheInfo);
                }
                break;
            default:
                break;
        }
    }

    /**
     * 插入缓存
     * @param cacheMap
     * @param cacheInfoTypeEnum
     * @param <V>
     */
    public <V> void put(Map<String,V> cacheMap, CacheInfoTypeEnum cacheInfoTypeEnum){
        switch (cacheInfoTypeEnum) {
            case PRODUCT:
                productCacheContainer.putAll(cacheMap);
                break;
            case LIVEPRODUCT:
                liveProductCacheContainer.putAll(cacheMap);
                break;
            case ACTIVITYPRODUCT:
                synchronized (this) {
                    expectActivityCacheSize = activityProductCacheContainer.size() + cacheMap.size();
                }
                if (expectActivityCacheSize <= cacheProps.getActivityProductCacheSize()) {
                    activityProductCacheContainer.putAll(cacheMap);
                }
                break;
            default:
                break;
        }
    }
}
