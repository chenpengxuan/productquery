package com.ymatou.productquery.domain.service;

import com.ymatou.productquery.domain.model.ActivityCatalogInfo;
import com.ymatou.productquery.domain.model.ActivityProducts;
import org.joda.time.DateTime;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by zhangyong on 2017/4/11.
 */
public class ProductActivityService {

    /**
     * 从商品活动列表中获取一个有效的活动策略
     *
     * @param activityProductList
     * @return
     */
    public static ActivityProducts getValidProductActivity(List<ActivityProducts> activityProductList) {
        if (activityProductList == null || activityProductList.isEmpty()) {
            return null;
        }
        Date now = new Date();
        ActivityProducts activityProduct = activityProductList.stream()
                .filter(c -> c.getStartTime().getTime() <= now.getTime() && c.getEndTime().getTime() >= now.getTime()).findFirst().orElse(null);

        if (activityProduct != null && activityProduct.getCatalogs() != null && !activityProduct.getCatalogs().isEmpty()) {
            int activityStock = activityProduct.getCatalogs().stream().collect(Collectors.summingInt(ActivityCatalogInfo::getActivityStock));
            if (activityStock == 0) {
                return null;
            }
        }
        return activityProduct;
    }

    /**
     * 取下一个即将开始的活动
     *
     * @param activityProductsList
     * @param nextActivityExpire
     * @param activityProduct
     * @return
     */
    public static ActivityProducts getNextProductActivity(List<ActivityProducts> activityProductsList, int nextActivityExpire, ActivityProducts activityProduct) {
        if(activityProductsList != null && !activityProductsList.isEmpty()){
            if (activityProduct != null) {
                return activityProductsList.stream().filter(t -> !t.getEndTime().before(DateTime.now().toDate())
                        && !t.getStartTime().after(DateTime.now().plusDays(nextActivityExpire).toDate())
                        && t.getProductInActivityId() != activityProduct.getProductInActivityId())
                        .min((t1, t2) -> t1.getEndTime().compareTo(t2.getEndTime())).orElse(null);

            }
            return activityProductsList.stream().filter(t -> !t.getEndTime().before(DateTime.now().toDate())
                    && !t.getStartTime().after(DateTime.now().plusDays(nextActivityExpire).toDate()))
                    .min((t1, t2) -> t1.getEndTime().compareTo(t2.getEndTime())).orElse(null);
        }
        return null;
    }
}
