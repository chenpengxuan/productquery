package com.ymatou.productquery.domain.cache;

import com.google.common.cache.CacheStats;
import com.ymatou.productquery.domain.model.ActivityProducts;
import com.ymatou.productquery.domain.model.cache.CacheActivityProductInfo;
import com.ymatou.productquery.domain.repo.mongorepo.ProductRepository;
import com.ymatou.productquery.domain.repo.mongorepo.ProductTimeStampRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 活动商品缓存相关
 * Created by chenpengxuan on 2017/4/28.
 */
public class ActivityCacheProcessor extends BaseCacheProcessor<ActivityProducts,CacheActivityProductInfo>{
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductTimeStampRepository productTimeStampRepository;

    @Override
    protected CacheStats getCacheStats() {
        return null;
    }

    @Override
    protected List<ActivityProducts> processNoneCache(List<String> productIdList) {
        return null;
    }

    @Override
    protected List<ActivityProducts> processPartialHitCache(List<String> productIdList, List<CacheActivityProductInfo> cacheInfoList, Map<String, Date> productUpdateTimeMap) {
        return null;
    }

    @Override
    protected List<ActivityProducts> filterValidCache(List<CacheActivityProductInfo> cacheInfoList, Map<String, Date> productUpdateTimeMap) {
        return null;
    }
}
