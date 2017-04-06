package com.ymatou.productquery.domain.cache;

import com.google.common.collect.Maps;
import com.google.common.primitives.Longs;
import com.ymatou.productquery.domain.model.Catalogs;
import com.ymatou.productquery.domain.repo.parallelrepo.Repository;
import com.ymatou.productquery.infrastructure.config.props.BizProps;
import com.ymatou.productquery.infrastructure.util.CacheUtil.CacheManager;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

/**
 * Created by zhangyong on 2017/4/6.
 */
@Component
public class Cache {
    /**
     * 缓存工具类
     */
    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private BizProps bizProps;

    @Resource(name = "mongoRepository")
    private Repository mongoRepository;

    @Resource(name = "parallelRepository")
    private Repository parallelRepository;

    private Repository realBusinessRepository;

    @PostConstruct
    public void init() {
        if (bizProps.isUseParallel()) {
            realBusinessRepository = parallelRepository;
        } else {
            realBusinessRepository = mongoRepository;
        }
    }

    /**
     * 获取规格信息列表
     *
     * @param productId
     * @return
     */
    public List<Catalogs> getCatalogListByProduct(String productId, Date catalogUpdateTime) {
        return null;
    }

    /**
     * 创建新缓存数据
     *
     * @param catalogList
     */
    private void createNewCacheData(List<Catalogs> catalogList) {
        return;
    }

}
