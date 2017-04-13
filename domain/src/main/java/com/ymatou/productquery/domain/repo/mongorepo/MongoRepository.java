package com.ymatou.productquery.domain.repo.mongorepo;

import com.ymatou.productquery.domain.model.*;
import com.ymatou.productquery.domain.repo.Repository;
import com.ymatou.productquery.infrastructure.constants.Constants;
import com.ymatou.productquery.infrastructure.dataprocess.mongo.MongoOperationTypeEnum;
import com.ymatou.productquery.infrastructure.dataprocess.mongo.MongoProcessor;
import com.ymatou.productquery.infrastructure.dataprocess.mongo.MongoQueryData;
import com.ymatou.productquery.infrastructure.util.MapUtil;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    /**
     * 获取活动商品信息列表
     *
     * @param productIdList
     * @return
     */
    public List<ActivityProducts> getActivityProductList(List<String> productIdList) {
        MongoQueryData queryData = new MongoQueryData();
        Map<String, Object> matchConditionMap = new HashMap<>();
        Map<String, Object> temp = new HashMap<>();
        temp.put("$in", productIdList);
        matchConditionMap.put("spid", temp);
        queryData.setMatchCondition(matchConditionMap);
        String query = MapUtil.makeJsonStringFromMapForJongo(matchConditionMap);
        return mongoProcessor.find(query, ActivityProducts.class, Constants.ActivityProductDb);
    }

    /**
     * 获取新增活动商品信息列表
     *
     * @param newestActivityObjectId 最新主键
     * @return
     */
    public List<ActivityProducts> getActivityProductList(ObjectId newestActivityObjectId) {
        Map<String, Object> matchConditionMap = new HashMap<>();
        Map<String, Object> temp = new HashMap<>();
        temp.put("$gt", newestActivityObjectId);
        matchConditionMap.put("_id", temp);
        String query = MapUtil.makeJsonStringFromMapForJongo(matchConditionMap);
        return mongoProcessor.find(query, ActivityProducts.class, Constants.ActivityProductDb);
    }

    /**
     * 获取全部有效活动商品列表
     *
     * @return
     */
    public List<ActivityProducts> getAllValidActivityProductList() {
        Map<String, Object> matchConditionMap = new HashMap<>();
        Map<String, Object> tempGte = new HashMap<>();
        tempGte.put("$gte", new Date());
        matchConditionMap.put("end", tempGte);
        String query = MapUtil.makeJsonStringFromMapForJongo(matchConditionMap);
        return mongoProcessor.find(query, ActivityProducts.class, Constants.ActivityProductDb);
    }

    /**
     * 根据catalogid查catalogs取productid
     *
     * @param catalogIdList
     * @return
     */
    public List<String> getProductIdsByCatalogIds(List<String> catalogIdList) {
        List<String> pids = new ArrayList<>();
        MongoQueryData queryData = new MongoQueryData();
        Map<String, Object> matchCondition = new HashMap<>();
        Map<String, Object> temp = new HashMap<>();
        temp.put("$in", catalogIdList);
        matchCondition.put("cid", temp);
        queryData.setMatchCondition(matchCondition);
        Map<String, Boolean> projectionMap = new HashMap<>();
        projectionMap.put("spid", true);
        projectionMap.put("_id", false);
        queryData.setProjection(projectionMap);
        queryData.setTableName(Constants.CatalogDb);
        queryData.setOperationType(MongoOperationTypeEnum.SELECTMANY);
        Stream<Object> query = mongoProcessor.queryMongo(queryData).stream().map(t -> t.get("spid")).distinct();
        query.forEach(t -> pids.add(t.toString()));
        return pids;
    }

    /**
     * 取商品关联直播编号列表
     *
     * @param productIdList
     * @return
     */
    public List<LiveProducts> getLiveProductList(List<String> productIdList) {
        MongoQueryData queryData = new MongoQueryData();
        Map<String, Object> matchConditionMap = new HashMap<>();
        Map<String, Object> temp = new HashMap<>();
        temp.put("$in", productIdList);
        matchConditionMap.put("spid", temp);
        queryData.setMatchCondition(matchConditionMap);
        String query = MapUtil.makeJsonStringFromMapForJongo(matchConditionMap);
        return mongoProcessor.find(query, LiveProducts.class, Constants.LiveProudctDb);
    }

    /**
     * 根据多个商品编号查询活动商品列表
     *
     * @param productIdList
     * @return
     */
    public List<ActivityProducts> getActivityProducts(List<String> productIdList) {
        MongoQueryData queryData = new MongoQueryData();
        Map<String, Object> matchConditionMap = new HashMap<>();
        Map<String, Object> temp = new HashMap<>();
        temp.put("$in", productIdList);
        matchConditionMap.put("spid", temp);
        queryData.setMatchCondition(matchConditionMap);
        String query = MapUtil.makeJsonStringFromMapForJongo(matchConditionMap);
        return mongoProcessor.find(query, ActivityProducts.class, Constants.ActivityProductDb);
    }


    /**
     * 查询历史商品
     *
     * @param productIdList
     * @return
     */
    public List<ProductDetailModel> getHistoryProductListByProductIdList(List<String> productIdList) {
        MongoQueryData queryData = new MongoQueryData();
        Map<String, Object> matchConditionMap = new HashMap<>();
        Map<String, Object> temp = new HashMap<>();
        temp.put("$in", productIdList);
        matchConditionMap.put("ProductId", temp);
        queryData.setMatchCondition(matchConditionMap);
        String query = MapUtil.makeJsonStringFromMapForJongo(matchConditionMap);
        return mongoProcessor.findHistoryProduct(query, ProductDetailModel.class, Constants.HistoryProductModel);
    }

    /**
     * 根据ProductIds查询Proudcts
     *
     * @param productIdList
     * @return
     */
    public List<Products> getProductsByProductIds(List<String> productIdList) {
        MongoQueryData queryData = new MongoQueryData();
        Map<String, Object> matchConditionMap = new HashMap<>();
        Map<String, Object> temp = new HashMap<>();
        temp.put("$in", productIdList);
        matchConditionMap.put("spid", temp);
        queryData.setMatchCondition(matchConditionMap);
        String query = MapUtil.makeJsonStringFromMapForJongo(matchConditionMap);
        return mongoProcessor.find(query, Products.class, Constants.ProductDb);
    }

    public List<Catalogs> getCatalogsByProductIds(List<String> productIdList) {
        MongoQueryData queryData = new MongoQueryData();
        Map<String, Object> matchConditionMap = new HashMap<>();
        Map<String, Object> temp = new HashMap<>();
        temp.put("$in", productIdList);
        matchConditionMap.put("spid", temp);
        queryData.setMatchCondition(matchConditionMap);
        String query = MapUtil.makeJsonStringFromMapForJongo(matchConditionMap);
        return mongoProcessor.find(query, Catalogs.class, Constants.CatalogDb);
    }
}
