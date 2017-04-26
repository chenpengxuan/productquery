package com.ymatou.productquery.domain.repo.mongorepo;

import com.mongodb.MongoClient;
import com.ymatou.productquery.domain.model.Catalogs;
import com.ymatou.productquery.domain.model.Products;
import com.ymatou.productquery.infrastructure.mongodb.MongoRepository;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.ArraySlice;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        this.insertEntity(this.dbName, product);
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

        Datastore datastore = this.getDataStore(this.dbName);
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
        Datastore datastore = this.getDataStore(this.dbName);
        return datastore.find(Products.class).disableValidation()
                .field("spid").in(productIdList).asList();
    }

    /**
     * 根据ProductId查询Product
     *
     * @param productId
     * @return
     */
    public List<Products> getProductByProductId(String productId) {
        Datastore datastore = this.getDataStore(this.dbName);
        return datastore.find(Products.class).disableValidation()
                .field("spid").equal(productId).asList();
    }

    /**
     * 根据productId查询Catalogs
     *
     * @param productId
     * @return
     */
    public List<Catalogs> getCatalogsByProductId(String productId) {
        Datastore datastore = this.getDataStore(this.dbName);
        return datastore.find(Catalogs.class).disableValidation()
                .field("spid").equal(productId).asList();
    }

    /**
     * 根据productids查询Catalogs
     *
     * @param productIdList
     * @return
     */
    public List<Catalogs> getCatalogsByProductIds(List<String> productIdList) {
        Datastore datastore = this.getDataStore(this.dbName);
        return datastore.find(Catalogs.class).disableValidation()
                .field("spid").in(productIdList).asList();
    }

    /**
     * 根据catalogid查catalogs取productid
     *
     * @param catalogIdList
     * @return
     */
    public List<String> getProductIdsByCatalogIds(List<String> catalogIdList) {
        Datastore datastore = this.getDataStore(this.dbName);
        List<Catalogs> query = datastore.find(Catalogs.class).disableValidation()
                .field("cid").in(catalogIdList)
                .project("spid", true).asList();
        return query.stream().map(t -> t.getProductId()).distinct().collect(Collectors.toList());
    }

}
