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

    private Cache productCacheFactory;

    private Cache liveProductCacheFactory;

    private ConcurrentMap activityProductCacheFactory;

    private static final int CACHE_SIZE_UNIT = 10000;

    /**
     * 预期活动商品缓存数量
     */
    private volatile int expectActivityCacheSize;

    @PostConstruct
    public void init() {
        switch (CacheTypeEnum.valueOf(cacheProps.getCacheType().toUpperCase())) {
            case GUAVACACHE:
                productCacheFactory = CacheBuilder.newBuilder()
                        .maximumSize(cacheProps.getCacheSize() * CACHE_SIZE_UNIT)
                        .expireAfterAccess(cacheProps.getExpireTime(), TimeUnit.HOURS)
                        .concurrencyLevel(cacheProps.getWriteConcurrencyNum())
                        .recordStats()
                        .build();
                liveProductCacheFactory = CacheBuilder.newBuilder()
                        .maximumSize(cacheProps.getCacheSize() * CACHE_SIZE_UNIT)
                        .expireAfterAccess(cacheProps.getExpireTime(), TimeUnit.HOURS)
                        .concurrencyLevel(cacheProps.getWriteConcurrencyNum())
                        .recordStats()
                        .build();
                activityProductCacheFactory = new ConcurrentHashMap(cacheProps.getActivityProductCacheSize());
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
                return productCacheFactory.stats();
            case LIVEPRODUCT:
                return liveProductCacheFactory.stats();
            case ACTIVITYPRODUCT:
                return null;
            default:
                return null;
        }
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
                return Optional.ofNullable((V) productCacheFactory.getIfPresent(cacheKey)).orElse(null);
            case LIVEPRODUCT:
                return Optional.ofNullable((V) liveProductCacheFactory.getIfPresent(cacheKey)).orElse(null);
            case ACTIVITYPRODUCT:
                return Optional.ofNullable((V) activityProductCacheFactory.get(cacheKey)).orElse(null);
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
                return Optional.ofNullable((Map<String,V>) productCacheFactory.getAllPresent(cacheKeyList)).orElse(null);
            case LIVEPRODUCT:
                return Optional.ofNullable((Map<String,V>) liveProductCacheFactory.getAllPresent(cacheKeyList)).orElse(null);
            case ACTIVITYPRODUCT:
                return Optional.ofNullable((Map<String,V>) activityProductCacheFactory.get(cacheKeyList)).orElse(null);
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
                productCacheFactory.invalidate(cacheKey);
            case LIVEPRODUCT:
                liveProductCacheFactory.invalidate(cacheKey);
            case ACTIVITYPRODUCT:
                activityProductCacheFactory.remove(cacheKey);
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
                productCacheFactory.invalidateAll(cacheKeyList);
            case LIVEPRODUCT:
                liveProductCacheFactory.invalidateAll(cacheKeyList);
            case ACTIVITYPRODUCT:
                cacheKeyList.forEach(x -> activityProductCacheFactory.remove(x));
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
                productCacheFactory.put(cacheKey,cacheInfo);
            case LIVEPRODUCT:
                liveProductCacheFactory.put(cacheKey,cacheInfo);
            case ACTIVITYPRODUCT:
                synchronized (this) {
                    expectActivityCacheSize = activityProductCacheFactory.size() + 1;
                }
                if (expectActivityCacheSize <= cacheProps.getActivityProductCacheSize()) {
                    activityProductCacheFactory.putIfAbsent(cacheKey, cacheInfo);
                }
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
                productCacheFactory.putAll(cacheMap);
            case LIVEPRODUCT:
                liveProductCacheFactory.putAll(cacheMap);
            case ACTIVITYPRODUCT:
                synchronized (this) {
                    expectActivityCacheSize = activityProductCacheFactory.size() + cacheMap.size();
                }
                if (expectActivityCacheSize <= cacheProps.getActivityProductCacheSize()) {
                    activityProductCacheFactory.putAll(cacheMap);
                }
            default:
                break;
        }
    }
}
