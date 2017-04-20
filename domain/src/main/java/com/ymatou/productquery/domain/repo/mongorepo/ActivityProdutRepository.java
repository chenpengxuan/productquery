package com.ymatou.productquery.domain.repo.mongorepo;

import com.mongodb.MongoClient;
import com.ymatou.productquery.domain.model.ActivityProducts;
import com.ymatou.productquery.infrastructure.mongodb.MongoRepository;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zhangyong on 2017/4/19.
 */
@Component
public class ActivityProdutRepository extends MongoRepository {
    @Resource(name = "productMongoClient")
    private MongoClient mongoClient;

    private final String dbName = "YmtProducts";

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
     * 根据多个商品编号查询活动商品列表
     *
     * @param productIdList
     * @return
     */
    public List<ActivityProducts> getActivityProductList(List<String> productIdList) {
        Datastore datastore = this.getDatastore(this.dbName);
        return datastore.find(ActivityProducts.class).disableValidation()
                .field("spid").in(productIdList).asList();
    }

    /**
     * 获取全部有效活动商品列表
     *
     * @return
     */
    public List<ActivityProducts> getAllValidActivityProductList() {
        Datastore datastore = this.getDatastore(this.dbName);
        return datastore.find(ActivityProducts.class).disableValidation()
                .asList();
    }

    /**
     * 获取新增活动商品信息列表
     *
     * @param newestActivityObjectId 最新主键
     * @return
     */
    public List<ActivityProducts> getActivityProductList(ObjectId newestActivityObjectId) {
        Datastore datastore = this.getDatastore(this.dbName);
        return datastore.find(ActivityProducts.class).disableValidation()
                .field("_id").greaterThan(newestActivityObjectId)
                .asList();
    }
}
