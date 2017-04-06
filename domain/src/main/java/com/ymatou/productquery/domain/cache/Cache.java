package com.ymatou.productquery.domain.cache;

import com.ymatou.productquery.domain.model.ActivityProducts;
import com.ymatou.productquery.domain.model.Catalogs;
import com.ymatou.productquery.domain.repo.Repository;
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


    /**
     * 初始化活动商品缓存
     */
    public int initActivityProductCache() {
        List<ActivityProducts> activityProductList = realBusinessRepository.getAllValidActivityProductList();
        cacheManager.putActivityProduct(activityProductList
                .stream()
                .collect(Collectors.toMap(ActivityProducts::getSpid, y -> y, (key1, key2) -> key2))
        );
        return activityProductList.size();
    }

    /**
     * 添加活动商品增量信息
     */
    public int addNewestActivityProductCache() {
        ConcurrentMap activityProductCache = cacheManager.getActivityProductCacheContainer();

        //从缓存中获取最后创建的活动商品数据的主键
        ObjectId newestCacheActivityProductPrimaryKey = ((ActivityProducts) activityProductCache.values()
                .stream()
                .max((x, y) ->
                        Integer.compare(((ActivityProducts) x).getId().getTimestamp()
                                , ((ActivityProducts) y).getId().getTimestamp()))
                .get())
                .getId();

        //获取新增的mongo活动商品信息
        List<ActivityProducts> newestActivityProductList = realBusinessRepository
                .getActivityProductList(newestCacheActivityProductPrimaryKey);

        //批量添加至缓存
        cacheManager.putActivityProduct(newestActivityProductList
                .stream()
                .collect(Collectors.toMap(ActivityProducts::getSpid, y -> y, (key1, key2) -> key2)));

        return newestActivityProductList.size();
    }

    /**
     * 刷新活动商品缓存
     * 去除过期商品缓存
     */
    public int refreshActivityProductCache() {
        ConcurrentMap activityProductCache = cacheManager.getActivityProductCacheContainer();

        //获取过期的活动商品缓存信息列表
        List<ActivityProducts> invalidActivityProductCacheList = (List<ActivityProducts>) activityProductCache.values()
                .stream()
                .filter(x -> {
                    ActivityProducts tempProduct = (ActivityProducts) x;
                    Long endTime = tempProduct.getEnd().getTime();
                    Long now = new Date().getTime();
                    return now > endTime;
                })
                .collect(Collectors.toList());

        //批量删除过期活动商品信息列表
        List<String> invalidActivityProductCacheIdList = invalidActivityProductCacheList
                .stream()
                .map(x -> x.getSpid())
                .collect(Collectors.toList());
        cacheManager.deleteActivityProduct(invalidActivityProductCacheIdList);
        return invalidActivityProductCacheIdList.size();
    }

}
