package com.ymatou.productquery.domain.cache;

import com.ymatou.productquery.domain.model.ActivityProducts;
import com.ymatou.productquery.infrastructure.config.props.BizProps;
import com.ymatou.productquery.infrastructure.config.props.CacheProps;
import com.ymatou.productquery.infrastructure.util.LogWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.ScheduledFuture;
import java.util.stream.Collectors;

/**
 * Created by zhangyong on 2017/4/6.
 */
@Configuration
public class ActivityCacheSchedule {
//    @Autowired
//    private CacheProps cacheProps;
//
//    @Autowired
//    private BizProps bizProps;
//
//    @Autowired
//    private Cache cache;
//
//    @Autowired
//    private ThreadPoolTaskScheduler threadPoolTaskScheduler;
//
//    @Autowired
//    private LogWrapper logWrapper;
//
//    private ScheduledFuture<?> future;
//
//    private ScheduledFuture<?> futureRefreshActivity;
//
//    private static int recordCount;
//
//    private static String cronSetting;
//
//    @Bean
//    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
//        return new ThreadPoolTaskScheduler();
//    }
//
//    @PostConstruct
//    public void init() {
//        if (bizProps.isUseCache() && cacheProps.isUseActivityCache()) {
//            //初始化活动商品缓存
//            recordCount = cache.initActivityProductCache();
//            logWrapper.recordInfoLog("初始化活动商品缓存已执行,新增{}条", recordCount);
//
//            cronSetting = bizProps.isUseCache() && cacheProps.isUseActivityCache() ? "0/" +
//                    cacheProps.getActivityProductFrequency() + " * * * * ?" : "";
//
//            //定时添加活动商品增量
//            scheduler();
//        }
//    }
//
//    /**
//     * 定时任务
//     */
//    public void scheduler() {
//        try {
//            future = threadPoolTaskScheduler.schedule(() ->
//                            recordCount = cache.addNewestActivityProductCache(),
//                    new CronTrigger(cronSetting));
//            logWrapper.recordInfoLog("增量添加活动商品缓存已执行,新增{}条", recordCount);
//
//            futureRefreshActivity = threadPoolTaskScheduler.schedule(() ->
//                            recordCount = cache.refreshActivityProductCache(),
//                    new CronTrigger(cronSetting));
//            logWrapper.recordInfoLog("定期删除过期活动商品缓存已执行,已删除{}条", recordCount);
//        } catch (Exception ex) {
//            logWrapper.recordErrorLog("增量添加活动商品缓存发生异常", ex);
//        }
//    }


}
