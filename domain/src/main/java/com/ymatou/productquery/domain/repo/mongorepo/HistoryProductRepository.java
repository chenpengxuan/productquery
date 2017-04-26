package com.ymatou.productquery.domain.repo.mongorepo;

import com.mongodb.MongoClient;
import com.ymatou.productquery.domain.model.HistoryProductModel;
import com.ymatou.productquery.domain.model.ProductDetailModel;
import com.ymatou.productquery.infrastructure.mongodb.MongoRepository;
import org.mongodb.morphia.Datastore;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zhangyong on 2017/4/19.
 */
@Component
public class HistoryProductRepository extends MongoRepository {
    @Resource(name = "historyProductMongoClient")
    private MongoClient mongoClient;

    private final String dbName = "ProductHis";

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
     * 查询历史商品
     *
     * @param productIdList
     * @return
     */
    public List<HistoryProductModel> getHistoryProductListByProductIdList(List<String> productIdList) {
        Datastore datastore = this.getDataStore(this.dbName);
        return datastore.find(HistoryProductModel.class).disableValidation()
                .field("ProductId").in(productIdList).asList();

    }

}
