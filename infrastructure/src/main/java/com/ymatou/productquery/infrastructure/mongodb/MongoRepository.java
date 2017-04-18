/**
 * (C) Copyright 2016 Ymatou (http://www.ymatou.com/).
 * <p>
 * All rights reserved.
 */
package com.ymatou.productquery.infrastructure.mongodb;


import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.ReadPreference;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.conversions.Bson;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.QueryFactory;

import java.util.Set;

/**
 * Mongo仓储基类
 */
public abstract class MongoRepository {

    Morphia morphia = new Morphia();

    protected MongoRepository() {
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
    protected Datastore getDatastore(String dbName) {
        return morphia.createDatastore(getMongoClient(), dbName);
    }

    /**
     * 获取到集合名称
     *
     * @param dbName
     * @return
     */
    protected Set<String> getCollectionNames(String dbName) {
        return getDatastore(dbName).getDB().getCollectionNames();
    }

    /**
     * 获取到指定集合
     *
     * @param dbName
     * @param collectionName
     * @return
     */
    protected DBCollection getCollection(String dbName, String collectionName) {
        return getDatastore(dbName).getDB().getCollection(collectionName);
    }


    /**
     * 获取实体信息
     *
     * @param c
     * @param dbName
     * @param fieldName
     * @param fieldValue
     * @return
     */
    protected <T> T getEntity(Class<T> c, String dbName, String fieldName, String fieldValue,
                              ReadPreference readPreference) {
        Datastore datastore = getDatastore(dbName);

        datastore.getMongo().setReadPreference(readPreference);

        return datastore.find(c).field(fieldName).equal(fieldValue).get();
    }

    /**
     * 插入文档
     *
     * @param dbName
     * @param entity
     */
    protected void insertEntiy(String dbName, Object entity) {
        Datastore datastore = getDatastore(dbName);
        datastore.save(entity);
    }

    /**
     * 插入文档
     *
     * @param dbName
     * @param collectionName
     * @param entity
     */
    protected void insertEntiy(String dbName, String collectionName, Object entity) {
        MongoClient mongoClient = getMongoClient();
        MongoDatabase database = mongoClient.getDatabase(dbName);
        MongoCollection<DBObject> collection = database.getCollection(collectionName, DBObject.class);

        DBObject dbObject = morphia.toDBObject(entity);

        collection.insertOne(dbObject);
    }


    /**
     * 更新文档
     *
     * @param dbName
     * @param collectionName
     * @param doc
     * @param res
     */
    protected UpdateResult updateOne(String dbName, String collectionName, Bson doc, Bson res) {
        MongoClient mongoClient = getMongoClient();
        MongoDatabase database = mongoClient.getDatabase(dbName);
        MongoCollection<DBObject> collection = database.getCollection(collectionName, DBObject.class);


        return collection.updateOne(doc, res);
    }


    /**
     * 删除文档
     *
     * @param dbName
     * @param collectionName
     * @param filter
     */
    protected DeleteResult deleteOne(String dbName, String collectionName, Bson filter) {
        MongoClient mongoClient = getMongoClient();
        MongoDatabase database = mongoClient.getDatabase(dbName);
        MongoCollection<DBObject> collection = database.getCollection(collectionName, DBObject.class);


        return collection.deleteOne(filter);
    }


    /**
     * 创建查询
     *
     * @param type
     * @param dbName
     * @param collectionName
     * @return
     */
    protected <T> Query<T> newQuery(final Class<T> type, String dbName, String collectionName,
                                    ReadPreference readPreference) {
        Datastore datastore = getDatastore(dbName);
        datastore.getMongo().setReadPreference(readPreference);

        DBCollection collection = datastore.getDB().getCollection(collectionName);
        QueryFactory queryFactory = datastore.getQueryFactory();

        return queryFactory.createQuery(datastore, collection, type);
    }
}
