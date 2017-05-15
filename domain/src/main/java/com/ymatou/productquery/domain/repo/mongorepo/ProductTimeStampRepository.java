package com.ymatou.productquery.domain.repo.mongorepo;

import com.mongodb.MongoClient;
import com.ymatou.productquery.domain.model.*;
import com.ymatou.productquery.infrastructure.mongodb.MongoRepository;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.FindOptions;
import org.mongodb.morphia.query.Query;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
     * 设置products更新时间
     * @param productsList
     */
    public void setProductListUpdateTime(List<Products> productsList){
        if(productsList != null){
            List<String> productIdList = productsList.stream().map(Products::getProductId).collect(Collectors.toList());
            List<ProductTimeStamp> productTimeStampList = getTimeStampByProductIds(productIdList, Arrays.asList("sut"));
            if(productTimeStampList != null){
                productsList.forEach(x -> {
                    ProductTimeStamp tempStamp = productTimeStampList.stream().filter(z -> z.getProductId().equals(x.getProductId())).findAny().orElse(null);
                    if(tempStamp != null){
                        x.setUpdateTime(tempStamp.getProductUpdateTime());
                    }
                });
            }
        }
    }

    /**
     * 设置ActivityProduct更新时间
     * @param productsList
     */
    public void setActivityProductListUpdateTime(List<ActivityProducts> productsList){
        if(productsList != null){
            List<String> productIdList = productsList.stream().map(ActivityProducts::getProductId).collect(Collectors.toList());
            List<ProductTimeStamp> productTimeStampList = getTimeStampByProductIds(productIdList, Arrays.asList("aut"));
            if(productTimeStampList != null){
                productsList.forEach(x -> {
                    ProductTimeStamp tempStamp = productTimeStampList.stream().filter(z -> z.getProductId().equals(x.getProductId())).findAny().orElse(null);
                    if(tempStamp != null){
                        x.setUpdateTime(tempStamp.getActivityUpdateTime());
                    }
                });
            }
        }
    }

    /**
     * 设置ActivityProduct更新时间
     * @param productsList
     */
    public void setLiveProductListUpdateTime(List<LiveProducts> productsList){
        if(productsList != null){
            List<String> productIdList = productsList.stream().map(LiveProducts::getProductId).collect(Collectors.toList());
            List<ProductTimeStamp> productTimeStampList = getTimeStampByProductIds(productIdList, Arrays.asList("lut"));
            if(productTimeStampList != null){
                productsList.forEach(x -> {
                    ProductTimeStamp tempStamp = productTimeStampList.stream().filter(z -> z.getProductId().equals(x.getProductId())).findAny().orElse(null);
                    if(tempStamp != null){
                        x.setUpdateTime(tempStamp.getLiveUpdateTime());
                    }
                });
            }
        }
    }

    /**
     * 设置catalogs更新时间
     * @param catalogsList
     */
    public void setCatalogListUpdateTime(List<Catalogs> catalogsList){
        if(catalogsList != null){
            List<String> productIdList = catalogsList.stream().map(Catalogs::getProductId).collect(Collectors.toList());
            List<ProductTimeStamp> productTimeStampList = getTimeStampByProductIds(productIdList, Arrays.asList("cut"));
            if(productTimeStampList != null){
                catalogsList.forEach(x -> {
                    ProductTimeStamp tempStamp = productTimeStampList.stream().filter(z -> z.getProductId().equals(x.getProductId())).findAny().orElse(null);
                    if(tempStamp != null){
                        x.setUpdateTime(tempStamp.getCatalogUpdateTime());
                    }
                });
            }
        }
    }

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
        query.project("spid",true);
        stampKeyList.forEach(x -> query.project(x,true));
        return query.get(limitOne);
    }

    /**
     * 根据ProductIdList查询ProudctTimeStamp
     *
     * @param productIdList
     * @param stampKeyList
     * @return
     */
    public List<ProductTimeStamp> getTimeStampByProductIds(List<String> productIdList, List<String> stampKeyList) {
        Datastore datastore = this.getDataStore(this.dbName);
        Query<ProductTimeStamp> query = datastore.find(ProductTimeStamp.class).disableValidation()
                .field("spid").in(productIdList);
        query.project("spid",true);
        stampKeyList.forEach(x -> query.project(x,true));
        return query.asList();
    }
}
