package com.ymatou.productquery.domain.repo;

import com.ymatou.productquery.domain.model.ActivityProducts;
import com.ymatou.productquery.domain.model.Catalogs;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by zhangyong on 2017/4/6.
 */
@Component
public interface Repository {
    /**
     * 获取全部有效活动商品列表
     *
     * @return
     */
    List<ActivityProducts> getAllValidActivityProductList();

    /**
     * 获取新增活动商品信息列表
     *
     * @param newestActivityObjectId 最新主键
     * @return
     */
    List<ActivityProducts> getActivityProductList(ObjectId newestActivityObjectId);
}
