package com.ymatou.productquery.domain.service;

import com.ymatou.productquery.domain.model.ActivityProducts;
import com.ymatou.productquery.domain.model.LiveProducts;
import com.ymatou.productquery.model.res.ProductStatusEnum;

import java.util.Date;

/**
 * Created by zhangyong on 2017/4/13.
 */
public class ProductStatusService {
    public static ProductStatusEnum getProductStatus(int action, Date validStart, Date validEnd, LiveProducts liveProduct, ActivityProducts activityProduct) {
        if (action == -1) {
            return ProductStatusEnum.Deleted;
        }
        if (activityProduct != null) {
            return ProductStatusEnum.Available;
        }
        Date now = new Date();
        if (liveProduct != null) {
            return now.after(liveProduct.getStartTime()) && now.before(liveProduct.getEndTime()) && liveProduct.getSellStatus() == 1
                    ? ProductStatusEnum.Available : ProductStatusEnum.Disable;
        }
        return now.after(validStart)&&now.before(validEnd)
                ?ProductStatusEnum.Available:ProductStatusEnum.Disable;
    }
}
