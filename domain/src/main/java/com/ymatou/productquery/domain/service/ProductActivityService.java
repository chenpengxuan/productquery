package com.ymatou.productquery.domain.service;

import com.ymatou.productquery.domain.model.ActivityCatalogInfo;
import com.ymatou.productquery.domain.model.ActivityProducts;
import com.ymatou.productquery.domain.model.Catalogs;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by zhangyong on 2017/4/11.
 */
public class ProductActivityService {

    /**
     * 取有效的活动商品
     *
     * @param activityProductsList
     * @param catalog
     * @return
     */
    public static ActivityProducts getValidProductActivity(List<ActivityProducts> activityProductsList, Catalogs catalog) {
        ActivityProducts activityProducts = getValidProductActivity(activityProductsList);
        if (activityProducts == null || catalog == null) {
            return null;
        }
        ActivityCatalogInfo activityCatalogInfo = activityProducts.getCatalogs().stream().
                filter(t -> t.getCatalogId().equals(catalog.getCatalogId())).findFirst().orElse(null);
        if (activityCatalogInfo != null && activityCatalogInfo.getActivityStock() > 0) {
            return activityProducts;
        } else return null;
    }

    /**
     * 从商品活动列表中获取最近开始的一个有效的活动策略
     *
     * @param activityProductsList
     * @return
     */
    //// FIXME: 2017/4/13 注意排序效果
    public static ActivityProducts getValidProductActivity(List<ActivityProducts> activityProductsList) {
        if (activityProductsList == null || activityProductsList.isEmpty()) {
            return null;
        }
        Date now = new Date();
        List<ActivityProducts> activityProducts = activityProductsList.stream().filter(t -> t.getStartTime().before(now)
                && t.getEndTime().after(now)).collect(Collectors.toList());
        return Collections.min(activityProducts, (o1, o2) ->
                o1.getStartTime().compareTo(o2.getStartTime()));
    }

}
