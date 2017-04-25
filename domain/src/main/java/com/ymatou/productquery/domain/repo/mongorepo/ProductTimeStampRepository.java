package com.ymatou.productquery.domain.repo.mongorepo;

import com.google.common.collect.Lists;
import com.mongodb.MongoClient;
import com.ymatou.productquery.domain.model.ProductTimeStamp;
import com.ymatou.productquery.infrastructure.mongodb.MongoRepository;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.FindOptions;
import org.mongodb.morphia.query.Query;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zhangyong on 2017/4/21.
 */
@Component
public class ProductTimeStampRepository extends MongoRepository {

    @Resource(name = "productMongoClient")
    private MongoClient mongoClient;

    private final static String dbName = "YmtProducts";

    /**
     * 获取到MongoClient
     *
     * @return
     */
    @Override
    protected MongoClient getMongoClient() {
        return mongoClient;
    }

    private final FindOptions limitOne = new FindOptions().limit(1);

    /**
     * 根据ProductId查询ProudctTimeStamp
     *
     * @param productId
     * @param stampKeyList
     * @return
     */
    public ProductTimeStamp getTimeStampByProductId(String productId, List<String> stampKeyList) {
        Datastore datastore = this.getDataStore(this.dbName);
        Query<ProductTimeStamp> query = datastore.find(ProductTimeStamp.class).disableValidation()
                .field("spid").equal(productId);
        stampKeyList.forEach(x -> query.project(x,true));
        return query.get(limitOne);
    }

    /**
     * 根据ProductIdList查询ProudctTimeStamp
     *
     * @param productIdList
     * @param stampKeys
     * @return
     */
    public List<ProductTimeStamp> getTimeStampByProductIds(List<String> productIdList, String stampKeys) {
        Datastore datastore = this.getDataStore(this.dbName);
        Query<ProductTimeStamp> query = datastore.find(ProductTimeStamp.class).disableValidation()
                .field("spid").in(productIdList);
//        stampKeys.split(",")
        String[] stampKeyList = stampKeys.split(",");
        Lists.newArrayList(stampKeyList).forEach(x -> query.project(x,true));
        return query.asList();
    }
}
