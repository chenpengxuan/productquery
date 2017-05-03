package com.ymatou.productquery.domain.repo.mongorepo;

import com.mongodb.MongoClient;
import com.ymatou.productquery.infrastructure.mongodb.MongoRepository;
import org.mongodb.morphia.Datastore;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zhangyong on 2017/4/19.
 */
@Component
public class LiveProductRepository extends MongoRepository {
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
     * 取商品关联直播编号列表
     *
     * @param productIdList
     * @return
     */
    public List<String> get(List<String> productIdList) {
        Datastore datastore = this.getDataStore(this.dbName);
//        return datastore.find(LiveProducts.class).disableValidation()
//                .field("spid").in(productIdList).asList();
        return null;
    }
}
