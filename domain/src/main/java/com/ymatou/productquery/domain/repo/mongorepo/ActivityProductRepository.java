package com.ymatou.productquery.domain.repo.mongorepo;

import com.mongodb.MongoClient;
import com.ymatou.productquery.domain.model.ActivityProducts;
import com.ymatou.productquery.infrastructure.mongodb.MongoRepository;
import com.ymatou.productquery.infrastructure.util.Tuple;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.FindOptions;
import org.mongodb.morphia.query.Sort;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by zhangyong on 2017/4/19.
 */
@Component
public class ActivityProductRepository extends MongoRepository {
    @Resource(name = "productMongoClient")
    private MongoClient mongoClient;

    private static final String dbName = "YmtProducts";

    private static final FindOptions limitOne = new FindOptions().limit(1);

    /**
     * 获取到MongoClient
     *
     * @return
     */
    @Override
    protected MongoClient getMongoClient() {
        return mongoClient;
    }

    /**
     * 根据多个 商品编号查询活动商品列表
     *
     * @param productIdList
     * @return
     */
    public List<ActivityProducts> getActivityProductList(List<String> productIdList) {
        Datastore datastore = this.getDataStore(this.dbName);
        Date now = new Date();
        return datastore.find(ActivityProducts.class).disableValidation()
                .field("spid").in(productIdList)
                .field("end").greaterThanOrEq(now).asList();
    }

    /**
     * 根据productId查询活动商品
     *
     * @param productId
     * @return
     */
    public List<ActivityProducts> getActivityProductByProductId(String productId) {
        Datastore datastore = this.getDataStore(this.dbName);
        Date now = new Date();
        return datastore.find(ActivityProducts.class).disableValidation()
                .field("spid").equal(productId)
                .field("end").greaterThanOrEq(now).asList();
    }

    /**
     * 根据productid查询正在进行的以及即将开始的活动
     *
     * @param productId
     * @return
     */
    public Tuple<ActivityProducts, ActivityProducts> getValidAndNextActivityProductByProductId(String productId, int nextActivityExpire) {
        Datastore datastore = this.getDataStore(this.dbName);
        Date now = new Date();
        ActivityProducts valid = datastore.find(ActivityProducts.class).disableValidation()
                .field("spid").equal(productId)
                .field("end").greaterThanOrEq(now)
                .field("start").lessThanOrEq(now)
                .get(limitOne);

        ActivityProducts next = datastore.find(ActivityProducts.class).disableValidation()
                .field("spid").equal(productId)
                .field("start").greaterThan(now)
                .order(Sort.ascending("start"))
                .get(limitOne);
        return new Tuple<>(valid, next);
    }

    /**
     * 根据productid查询正在进行的以及即将开始的活动
     *
     * @param productIdList
     * @param nextActivityExpire
     * @return
     */
    public Map<String, Tuple<ActivityProducts, ActivityProducts>> getValidAndNextActivityProductByProductId(List<String> productIdList, int nextActivityExpire) {
        Map<String, Tuple<ActivityProducts, ActivityProducts>> stringTupleMap = new HashMap<>();
        productIdList.forEach(t -> stringTupleMap.put(t, getValidAndNextActivityProductByProductId(t, nextActivityExpire)));
        return stringTupleMap;
    }

    /**
     * 获取全部有效活动商品列表
     *
     * @return
     */
    public List<ActivityProducts> getAllValidActivityProductList() {
        Datastore datastore = this.getDataStore(this.dbName);
        Date now = new Date();
        return datastore.find(ActivityProducts.class).disableValidation()
                .field("end").greaterThanOrEq(now)
                .asList();
    }

    /**
     * 获取新增活动商品信息列表
     *
     * @param newestProductInActivityId 最新
     * @return
     */
    public List<ActivityProducts> getNewestActivityProductIdList(int newestProductInActivityId) {
        Datastore datastore = this.getDataStore(this.dbName);
        return datastore.find(ActivityProducts.class).disableValidation()
                .field("inaid").greaterThan(newestProductInActivityId)
                .asList();
    }
}
