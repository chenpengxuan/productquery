package com.ymatou.productquery.infrastructure.config.datasource;

import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.ymatou.productquery.infrastructure.config.props.MongoProps;
import org.jongo.Jongo;
import org.jongo.marshall.jackson.JacksonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.annotation.Resource;

/**
 * mongo data source
 * Created by chenpengxuan on 2017/2/6.
 */
@Configuration
@DependsOn({"disconfMgrBean2"})
public class MongoDataSource {
    @Autowired
    private MongoProps mongoProps;

    @Bean("productMongoUri")
    public MongoClientURI getMongoClientURI() {
        return new MongoClientURI(mongoProps.getMongoProductUrl());
    }

    @Bean("productMongoClient")
    public MongoClient getMongoClient() {
        MongoClientURI mongoClientURI = new MongoClientURI(mongoProps.getMongoProductUrl());
        return new MongoClient(mongoClientURI);
    }

    @Bean("historyProductMongoUri")
    public MongoClientURI getHistoryMongoClientURI() {
        return new MongoClientURI(mongoProps.getMongoHistoryProductUrl());
    }

    @Bean("historyProductMongoClient")
    public MongoClient getHistoryMongoClient() {
        return new MongoClient(mongoProps.getMongoHistoryProductUrl());
    }
}