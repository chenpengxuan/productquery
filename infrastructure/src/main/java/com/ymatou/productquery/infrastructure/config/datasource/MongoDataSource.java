package com.ymatou.productquery.infrastructure.config.datasource;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import com.ymatou.productquery.infrastructure.config.props.MongoProps;
import org.jongo.Jongo;
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

    @Bean
    public Jongo jongoClient(){
        MongoClientURI uri = new MongoClientURI(mongoProps.getMongoProductUrl());
        //Todo getDB deprecated,please use getDatabase
        DB db =new MongoClient(uri).getDB(uri.getDatabase());
        return new Jongo(db);
    }

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
