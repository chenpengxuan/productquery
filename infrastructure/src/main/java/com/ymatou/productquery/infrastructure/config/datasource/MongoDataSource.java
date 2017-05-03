package com.ymatou.productquery.infrastructure.config.datasource;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.ymatou.productquery.infrastructure.config.props.MongoProps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

/**
 * mongo data source
 * Created by chenpengxuan on 2017/2/6.
 */
@Configuration
@DependsOn({"disconfMgrBean2"})
public class MongoDataSource {
    @Autowired
    private MongoProps mongoProps;

    @Bean("productMongoClient")
    public MongoClient getMongoClient() {
        MongoClientURI mongoClientURI = new MongoClientURI(mongoProps.getMongoProductUrl());
        return new MongoClient(mongoClientURI);
    }

    @Bean("historyProductMongoClient")
    public MongoClient getHistoryMongoClient() {
        MongoClientURI mongoClientURI=new MongoClientURI(mongoProps.getMongoHistoryProductUrl());
        return new MongoClient(mongoClientURI);
    }
}
