/**
 * (C) Copyright 2016 Ymatou (http://www.ymatou.com/).
 * <p>
 * All rights reserved.
 */
package com.ymatou.productquery.infrastructure.mongodb;


import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

/**
 * Mongo仓储基类
 */
public abstract class MongoRepository {

    private Morphia morphia;

    protected MongoRepository() {
        morphia = new Morphia();
        this.morphia.getMapper().getConverters().addConverter(BigDecimalConverter.class);
    }

    /**
     * 获取到MongoClient
     *
     * @return
     */
    protected abstract MongoClient getMongoClient();

    /**
     * 获取到制定库名的数据源
     *
     * @param dbName
     * @return
     */
    protected Datastore getDataStore(String dbName) {
        return morphia.createDatastore(getMongoClient(), dbName);
    }


}
