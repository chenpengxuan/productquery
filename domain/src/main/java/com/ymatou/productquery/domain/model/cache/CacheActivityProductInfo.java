package com.ymatou.productquery.domain.model.cache;

import com.ymatou.productquery.domain.model.ActivityCatalogInfo;

import java.util.Date;
import java.util.List;

/**
 * Created by chenpengxuan on 2017/4/26.
 */
public class CacheActivityProductInfo extends BaseCacheInfo{
    /**
     * 活动id
     */
    private int activityId;

    /**
     * 活动名称
     */
    private String activityName;

    /**
     * 商品活动关联id
     */
    private int productInActivityId;

    /**
     * 活动商品开始时间
     */
    private Date startTime;

    /**
     * 活动商品结束时间
     */
    private Date endTime;

    /**
     * 活动商品市场价
     */
    private Double marketPrice;

    /**
     * 活动限购数
     */
    private int activityLimit;

    /**
     * 商品限购数
     */
    private int productLimit;

    /**
     * 是否交易隔离活动商品
     */
    private boolean isTradeIsolation;

    /**
     * 是否新客活动商品
     */
    private boolean isNewBuyer;

    /**
     *
     */
    private boolean isPartCatalogs;

    /**
     *
     */
    private List<ActivityCatalogInfo> activityCatalogList;
}
