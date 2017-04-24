package com.ymatou.productquery.domain.repo.mongorepo;

import com.mongodb.MongoClient;
import com.ymatou.productquery.domain.model.ProductTimeStamp;
import com.ymatou.productquery.domain.model.Products;
import com.ymatou.productquery.infrastructure.mongodb.MongoRepository;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.FindOptions;
import org.mongodb.morphia.query.Meta;
import org.mongodb.morphia.query.Query;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangyong on 2017/4/21.
 */
@Component
public class ProductTimeStampRepository extends MongoRepository {

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

    private final FindOptions limitOne = new FindOptions().limit(1);

    /**
     * 根据ProductId查询ProudctTimeStamp
     *
     * @param productId
     * @param stampKeyList
     * @return
     */
    public ProductTimeStamp getTimeStampByProductId(String productId, List<String> stampKeyList) {
        Datastore datastore = this.getDatastore(this.dbName);
        Query<ProductTimeStamp> query = datastore.find(ProductTimeStamp.class).disableValidation()
                .field("spid").equal(productId);
        return query.retrievedFields(true, (String[]) stampKeyList.toArray()).get(limitOne);
    }

    /**
     * 根据ProductIdList查询ProudctTimeStamp
     *
     * @param productIdList
     * @param stampKeyList
     * @return
     */
    public List<ProductTimeStamp> getTimeStampByProductIds(List<String> productIdList, List<String> stampKeyList) {
        Datastore datastore = this.getDatastore(this.dbName);
        Query<ProductTimeStamp> query = datastore.find(ProductTimeStamp.class).disableValidation()
                .field("spid").in(productIdList);
        return query.retrievedFields(true, (String[]) stampKeyList.toArray()).asList();
    }
}
