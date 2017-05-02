package com.ymatou.productquery.domain.repo.mongorepo;

import com.mongodb.MongoClient;
import com.ymatou.productquery.domain.model.ActivityProducts;
import com.ymatou.productquery.domain.model.Catalogs;
import com.ymatou.productquery.domain.model.LiveProducts;
import com.ymatou.productquery.domain.model.Products;
import com.ymatou.productquery.infrastructure.mongodb.MongoRepository;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.ArraySlice;
import org.mongodb.morphia.query.FindOptions;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
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

    private final FindOptions limitOne = new FindOptions().limit(1);

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
    public List<Products> getProductListByProductIdList(List<String> productIdList) {
        Datastore datastore = this.getDataStore(this.dbName);
        return datastore.find(Products.class).disableValidation()
                .field("spid").in(productIdList).asList();
    }

    /**
     * 根据ProductIds查询直播商品列表
     * @param productIdList
     * @return
     */
    public List<LiveProducts> getLiveProductListByProductIdList(List<String> productIdList) {
        Datastore datastore = this.getDataStore(this.dbName);
        Date now = new Date();
        return datastore.find(LiveProducts.class).disableValidation()
                .field("spid").in(productIdList)
                .field("start").lessThanOrEq(now)
                .field("end").greaterThanOrEq(now)
                .field("status").equal(1)
                .asList();
    }

    /**
     * 根据ProductId查询Product
     *
     * @param productId
     * @return
     */
    public Products getProductInfoByProductId(String productId) {
        Datastore datastore = this.getDataStore(this.dbName);
        return datastore.find(Products.class).disableValidation()
                .field("spid").equal(productId).get(limitOne);
    }

    /**
     * 根据productId查询Catalogs
     *
     * @param productId
     * @return
     */
    public List<Catalogs> getCatalogListByProductId(String productId) {
        Datastore datastore = this.getDataStore(this.dbName);
        return datastore.find(Catalogs.class).disableValidation()
                .field("spid").equal(productId).asList();
    }

    /**
     * 根据规格id列表获取规格信息列表
     * @param catalogIdList
     * @return
     */
    public List<Catalogs> getCatalogListByCatalogIdList(List<String> catalogIdList) {
        Datastore datastore = this.getDataStore(this.dbName);
        return datastore.find(Catalogs.class).disableValidation()
                .field("cid").equal(catalogIdList).asList();
    }

    /**
     * 根据商品id列表获取活动商品列表
     * @param productIdList
     * @return
     */
    public List<ActivityProducts> getActivityProductListByProductIdList(List<String> productIdList) {
        Datastore datastore = this.getDataStore(this.dbName);
        return datastore.find(ActivityProducts.class).disableValidation()
                .field("spid").in(productIdList)
                .field("end").greaterThanOrEq(new Date())
                .asList();
    }

    /**
     * 根据productids查询Catalogs
     *
     * @param productIdList
     * @return
     */
    public List<Catalogs> getCatalogListByProductIdList(List<String> productIdList) {
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
