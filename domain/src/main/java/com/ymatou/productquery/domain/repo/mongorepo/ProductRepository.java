package com.ymatou.productquery.domain.repo.mongorepo;

import com.mongodb.MongoClient;
import com.ymatou.productquery.domain.model.Products;
import com.ymatou.productquery.infrastructure.mongodb.MongoRepository;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.ArraySlice;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangyong on 2017/4/17.
 */
@Component
public class ProductRepository extends MongoRepository {

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
     * 插入
     *
     * @param product
     */
    public void insert(Products product) {
        this.insertEntiy(this.dbName, product);
    }

    /**
     * 根据商品Id获取商品信息
     *
     * @param productIds
     * @return
     */
    public List<Products> getProducts(List<String> productIds) {

        if (productIds == null || productIds.size() == 0) {
            return new ArrayList<>();
        }

        Datastore datastore = this.getDatastore(this.dbName);
        return datastore.find(Products.class).disableValidation()
                .field("spid").in(productIds)
                .project("spid", true)
                .project("ispsp", true)
                .project("pics", new ArraySlice(1))
                .project("minp", true)
                .project("_id", false)
                .asList();
    }

    /**
     * 根据ProductIds查询Proudcts
     *
     * @param productIdList
     * @return
     */
    public List<Products> getProductsByProductIds(List<String> productIdList) {
        Datastore datastore = this.getDatastore(this.dbName);
        return datastore.find(Products.class).disableValidation()
                .field("spid").in(productIdList)
                .project("sid", true)
                .project("_id", false).asList();
    }

}
