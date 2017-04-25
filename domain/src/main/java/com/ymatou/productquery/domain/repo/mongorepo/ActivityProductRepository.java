package com.ymatou.productquery.domain.repo.mongorepo;

import com.mongodb.MongoClient;
import com.ymatou.productquery.domain.model.ActivityProducts;
import com.ymatou.productquery.infrastructure.mongodb.MongoRepository;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.FindOptions;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

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
