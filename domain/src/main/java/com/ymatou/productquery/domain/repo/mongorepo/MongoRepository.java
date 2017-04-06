package com.ymatou.productquery.domain.repo.mongorepo;

import com.ymatou.productquery.domain.repo.parallelrepo.Repository;
import com.ymatou.productquery.infrastructure.constants.Constants;
import com.ymatou.productquery.infrastructure.dataprocess.mongo.MongoOperationTypeEnum;
import com.ymatou.productquery.infrastructure.dataprocess.mongo.MongoProcessor;
import com.ymatou.productquery.infrastructure.dataprocess.mongo.MongoQueryData;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by zhangyong on 2017/4/6.
 */
@Service("mongoRepository")
public class MongoRepository implements Repository {
    @Autowired
    private MongoProcessor mongoProcessor;

    /**
     * 根据商品id与时间戳列名获取对应时间戳
     * 用于缓存功能
     *
     * @param productId
     * @param stampKeyList
     * @return
     */
    public Map<String, Object> getTimeStampByProductId(String productId, List<String> stampKeyList) {
        MongoQueryData queryData = new MongoQueryData();

        Map<String, Object> matchConditionMap = new HashMap<>();
        matchConditionMap.put("spid", productId);
        queryData.setMatchCondition(matchConditionMap);

        Map<String, Boolean> projectionMap = new HashMap<>();
        stampKeyList.stream().forEach(key -> projectionMap.put(key, true));
        projectionMap.put("_id", false);
        queryData.setProjection(projectionMap);

        queryData.setTableName(Constants.ProductTimeStampDb);

        queryData.setOperationType(MongoOperationTypeEnum.SELECTSINGLE);
        return mongoProcessor
                .queryMongo(queryData)
                .stream()
                .findAny().orElse(Collections.emptyMap());
    }

    /**
     * 根据商品id列表与时间戳列名获取对应时间戳
     *
     * @param productIdList
     * @param stampKeyList
     * @return
     */
    public List<Map<String, Object>> getTimeStampByProductIdList(List<String> productIdList, List<String> stampKeyList) {
        MongoQueryData queryData = new MongoQueryData();

        Map<String, Object> matchConditionMap = new HashMap<>();
        Map<String, Object> tempMap = new HashMap<>();
        tempMap.put("$in", productIdList);
        matchConditionMap.put("spid", tempMap);
        queryData.setMatchCondition(matchConditionMap);

        Map<String, Boolean> projectionMap = new HashMap<>();
        stampKeyList.stream().forEach(key -> projectionMap.put(key, true));
        projectionMap.put("spid", true);
        projectionMap.put("_id", false);
        queryData.setProjection(projectionMap);

        queryData.setTableName(Constants.ProductTimeStampDb);

        queryData.setOperationType(MongoOperationTypeEnum.SELECTMANY);
        return mongoProcessor
                .queryMongo(queryData)
                .stream()
                .collect(Collectors.toList());
    }

    /**
     * 根据商品id列表与时间戳列名获取对应时间戳
     *
     * @param catalogIdList
     * @param stampKeyList
     * @return
     */
    public List<Map<String, Object>> getTimeStampByCatalogIdList(List<String> catalogIdList, List<String> stampKeyList) {
        MongoQueryData queryData = new MongoQueryData();

        Map<String, Object> matchConditionMap = new HashMap<>();
        Map<String, Object> tempMap = new HashMap<>();
        tempMap.put("$in", catalogIdList);
        matchConditionMap.put("cid", tempMap);
        queryData.setMatchCondition(matchConditionMap);

        Map<String, Boolean> projectionMap = new HashMap<>();
        stampKeyList.stream().forEach(key -> projectionMap.put(key, true));
        projectionMap.put("cid", true);
        projectionMap.put("_id", false);
        queryData.setProjection(projectionMap);

        queryData.setTableName(Constants.CatalogDb);

        queryData.setOperationType(MongoOperationTypeEnum.SELECTMANY);
        return mongoProcessor
                .queryMongo(queryData)
                .stream()
                .collect(Collectors.toList());
    }

    /**
     * 根据规格id列表获取商品id规格id映射关系
     *
     * @param catalogIdList
     * @return
     */
    public List<Map<String, Object>> getProductIdByCatalogIdList(List<String> catalogIdList) {
        MongoQueryData queryData = new MongoQueryData();

        Map<String, Object> matchConditionMap = new HashMap<>();
        Map<String, Object> tempMap = new HashMap<>();
        tempMap.put("$in", catalogIdList);
        matchConditionMap.put("cid", tempMap);
        queryData.setMatchCondition(matchConditionMap);

        Map<String, Boolean> projectionMap = new HashMap<>();
        projectionMap.put("spid", true);
        projectionMap.put("cid", true);
        projectionMap.put("_id", false);
        queryData.setProjection(projectionMap);

        queryData.setTableName(Constants.CatalogDb);

        queryData.setOperationType(MongoOperationTypeEnum.SELECTMANY);
        return mongoProcessor
                .queryMongo(queryData)
                .stream()
                .collect(Collectors.toList());
    }


}
