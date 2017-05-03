package com.ymatou.productquery.domain.repo.mongorepo;

import com.mongodb.MongoClient;
import com.ymatou.productquery.domain.model.*;
import com.ymatou.productquery.infrastructure.mongodb.MongoRepository;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.ArraySlice;
import org.mongodb.morphia.query.FindOptions;
import org.mongodb.morphia.query.Sort;
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
     *
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
     * 根据规格id列表获取规格信息列表
     *
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
     *
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

    /**
     * 取直播置顶商品编号列表
     *
     * @param liveId
     * @return
     */
    public List<String> getTopLiveProductIdList(int liveId) {
        Datastore datastore = this.getDataStore(this.dbName);

        List<LiveProducts> liveProductsList = datastore.find(LiveProducts.class).disableValidation()
                .field("lid").equal(liveId)
                .field("istop").equal(true)
                .field("status").equal(1)
                .order(Sort.ascending("sort"))
                .project("spid", true).asList();

        return liveProductsList.stream().map(live -> live.getProductId()).collect(Collectors.toList());
    }

    /**
     * 取买手热推商品
     *
     * @param sellerId
     * @return
     */
    public List<String> getHotRecmdProductList(int sellerId) {
        Datastore datastore = this.getDataStore(this.dbName);

        List<Products> productList = datastore.find(Products.class).disableValidation()
                .field("sid").equal(sellerId)
                .field("istop").equal(true)
                .project("spid", true).asList();

        return productList.stream().map(pid -> pid.getProductId()).collect(Collectors.toList());
    }

    /**
     * 取新品列表
     *
     * @param sellerId
     * @param curPage
     * @param pageSize
     * @return
     */
    public List<String> getNewestProductList(int sellerId, int curPage, int pageSize) {
        Datastore datastore = this.getDataStore(this.dbName);

        FindOptions options = new FindOptions().skip((curPage - 1) * pageSize).limit(pageSize);
        Date now = new Date();

        List<Products> productList = datastore.find(Products.class).disableValidation()
                .field("sid").equal(sellerId)
                .field("isnew").equal(true)
                .field("newstart").lessThanOrEq(now)
                .field("newend").greaterThanOrEq(now)
                .order(Sort.descending("newstart"))
                .project("spid", true).asList();

        return productList.stream().map(pid -> pid.getProductId()).collect(Collectors.toList());
    }

    /**
     * 取商品图文描述扩展信息
     *
     * @param productId
     * @return
     */
    public ProductDescExtra getProductDescExtra(String productId) {
        Datastore datastore = this.getDataStore(this.dbName);
        return datastore.find(ProductDescExtra.class).disableValidation()
                .field("spid").equal(productId).get(limitOne);
    }

    /**
     * 取买手的置顶商品编号列表
     *
     * @param sellerIdList
     * @return
     */
    public List<String> getTopLiveProductIdListBySellerIdList(List<Integer> sellerIdList) {
        Datastore datastore = this.getDataStore(this.dbName);
        Date utcNow = new Date();
        List<LiveProducts> liveProductsList = datastore.find(LiveProducts.class).disableValidation()
                .field("sid").in(sellerIdList)
                .field("istop").equal(true)
                .field("status").equal(1)
                .field("start").lessThanOrEq(utcNow)
                .field("end").greaterThanOrEq(utcNow)
                .order(Sort.ascending("sort"))
                .project("spid", true).asList();

        return liveProductsList.stream().map(pid -> pid.getProductId()).collect(Collectors.toList());
    }

    /**
     * 取买手的活动商品编号列表
     *
     * @param sellerIdList
     * @return
     */
    public List<String> getActivityProductIdListBySellerIdList(List<Integer> sellerIdList) {
        Datastore datastore = this.getDataStore(this.dbName);
        Date utcNow = new Date();

        List<ActivityProducts> activityProductList = datastore.find(ActivityProducts.class).disableValidation()
                .field("sid").in(sellerIdList)
                .field("start").lessThanOrEq(utcNow)
                .field("end").greaterThanOrEq(utcNow)
                .project("spid", true).asList();

        return activityProductList.stream().map(pid -> pid.getProductId()).collect(Collectors.toList());
    }
}
