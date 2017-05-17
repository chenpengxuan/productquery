package com.ymatou.productquery.domain.service;

import com.ymatou.productquery.domain.cache.ActivityCacheProcessor;
import com.ymatou.productquery.domain.cache.CatalogCacheProcessor;
import com.ymatou.productquery.domain.cache.LiveCacheProcessor;
import com.ymatou.productquery.domain.cache.ProductCacheProcessor;
import com.ymatou.productquery.domain.model.*;
import com.ymatou.productquery.domain.model.cache.CacheProductInfo;
import com.ymatou.productquery.domain.repo.mongorepo.HistoryProductRepository;
import com.ymatou.productquery.domain.repo.mongorepo.ProductRepository;
import com.ymatou.productquery.domain.repo.mongorepo.ProductTimeStampRepository;
import com.ymatou.productquery.infrastructure.config.props.BizProps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 列表查询与单品查询共用
 * Created by chenpengxuan on 2017/4/26.
 */
@Component
public class CommonQueryService {

    @Autowired
    private BizProps bizProps;

    @Resource(name = "productCacheProcessor")
    private ProductCacheProcessor productCacheProcessor;

    @Resource(name = "activityCacheProcessor")
    private ActivityCacheProcessor activityCacheProcessor;

    @Resource(name = "catalogCacheProcessor")
    private CatalogCacheProcessor catalogCacheProcessor;

    @Resource(name = "liveCacheProcessor")
    private LiveCacheProcessor liveCacheProcessor;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductTimeStampRepository productTimeStampRepository;

    @Autowired
    private HistoryProductRepository historyProductRepository;

    /**
     * 根据商品id列表获取商品信息
     *
     * @param productIdList
     * @return
     */
    public List<Products> getProductListByProductIdList(List<String> productIdList) {
        if (bizProps.isUseCache()) {
            return productCacheProcessor.getProductInfoByProductIdList(productIdList);
        } else {
            return productRepository.getProductListByProductIdList(productIdList);
        }
    }

    /**
     * 根据规格id列表获取对应的每个商品的规格数量
     * @param catalogIdList
     * @return
     */
    public Map<String,Integer> getCatalogNumByCatalogIdList(List<String> catalogIdList) {
        if(bizProps.isUseCache()){
            return catalogCacheProcessor.getCatalogNumByCatalogIdList(catalogIdList);
        }else{
            List<String> productIdList = productRepository.getProductIdsByCatalogIds(catalogIdList);
            return productRepository.getCatalogCountList(productIdList);
        }
    }

    /**
     * 根据商品id获取商品信息
     *
     * @param productId
     * @return
     */
    public Products getProductByProductId(String productId) {
        if (bizProps.isUseCache()) {
            return productCacheProcessor.getProductInfoByProductId(productId);
        } else {
            return productRepository.getProductInfoByProductId(productId);
        }
    }

    /**
     * 根据规格id列表获取规格信息
     *
     * @param catalogIdList
     * @return
     */
    public List<Catalogs> getCatalogListByCatalogIdList(List<String> catalogIdList) {
        if (bizProps.isUseCache()) {
            return catalogCacheProcessor.getCatalogListByCatalogIdList(catalogIdList);
        } else {
            return productRepository.getCatalogListByCatalogIdList(catalogIdList);
        }
    }

    /**
     * 根据规格id列表获取商品及规格信息列表 用于购物车场景
     * @param catalogIdList
     * @return
     */
    public List<CacheProductInfo> getProductListByCatalogIdList(List<String> catalogIdList) {
        List<Catalogs> catalogsList;
        List<Products> productsList = new ArrayList<>();
        List<String> productIdList;
        List<CacheProductInfo> result = new ArrayList<>();

        if (bizProps.isUseCache()) {
            catalogsList = catalogCacheProcessor.getCatalogListByCatalogIdList(catalogIdList);
        } else {
            catalogsList = productRepository.getCatalogListByCatalogIdList(catalogIdList);
        }

        productIdList = catalogsList != null && !catalogIdList.isEmpty() ?catalogsList.stream().map(Catalogs::getProductId).collect(Collectors.toList()):null;

        if(productIdList != null && !productIdList.isEmpty()){
            productsList = getProductListByProductIdList(productIdList);
        }

        if(catalogsList != null && !catalogsList.isEmpty()){
            List<Products> tempProductList = productsList;

            catalogsList.stream().collect(Collectors.groupingBy(Catalogs::getProductId)).forEach((key,group) -> {
                Products tempProduct = tempProductList.stream().filter(z -> z.getProductId().equals(key)).findAny().orElse(null);

                if(tempProduct != null){
                    CacheProductInfo cacheProductInfo = tempProduct.convertDtoToCacheData();
                    cacheProductInfo.setCatalogsList(group);
                    result.add(cacheProductInfo);
                }
            });
        }

        return result;
    }

    /**
     * 根据商品id列表获取规格列表
     *
     * @param productIdList
     * @return
     */
    public List<Catalogs> getCatalogListByProductIdList(List<String> productIdList) {
        if (bizProps.isUseCache()) {
            return catalogCacheProcessor.getCatalogListByProductIdList(productIdList);
        } else {
            return productRepository.getCatalogListByProductIdList(productIdList);
        }
    }

    /**
     * 根据商品id列表获取活动商品信息列表
     *
     * @param productIdList
     * @return
     */
    public List<ActivityProducts> getActivityProductListByProductIdList(List<String> productIdList) {
        if (bizProps.isUseCache()) {
            return activityCacheProcessor.getActivityProductListByProductIdList(productIdList);
        } else {
            return productRepository.getActivityProductListByProductIdList(productIdList);
        }
    }

    /**
     * 根据直播商品id列表获取直播商品信息
     *
     * @param productIdList
     * @return
     */
    public List<LiveProducts> getLiveProductListByProductId(List<String> productIdList) {
        if (bizProps.isUseCache()) {
            return liveCacheProcessor.getProductInfoByProductIdList(productIdList);
        } else {
            return productRepository.getLiveProductListByProductIdList(productIdList);
        }
    }

    /**
     * 取直播中置顶商品列表
     *
     * @param liveId
     * @return
     */
    List<String> getTopProductIdListByLiveId(int liveId) {
        return productRepository.getTopLiveProductIdList(liveId);
    }

    /**
     * 买手热推商品列表
     *
     * @param sellerId
     * @return
     */
    List<String> getHotRecmdProductIdListBySellerId(int sellerId) {
        return productRepository.getHotRecmdProductList(sellerId);
    }

    /**
     * 取买手新品列表
     *
     * @param sellerId
     * @param curPage
     * @param pageSize
     * @return
     */
    List<String> getNewestProductIdList(int sellerId, int curPage, int pageSize) {
        return productRepository.getNewestProductList(sellerId, curPage, pageSize);
    }

    /**
     * 取商品图文描述扩展
     *
     * @param productId
     * @return
     */
    ProductDescExtra getProductDescExtra(String productId) {
        return productRepository.getProductDescExtra(productId);
    }

    /**
     * 取买手的置顶商品编号列表
     *
     * @param sellerIdList
     * @return
     */
    List<String> getTopLiveProductIdListBySellerIdList(List<Integer> sellerIdList) {
        return productRepository.getTopLiveProductIdListBySellerIdList(sellerIdList);
    }

    /**
     * 取买手的活动商品编号列表
     *
     * @param sellerIdList
     * @return
     */
    List<String> getActivityProductIdListBySellerIdList(List<Integer> sellerIdList) {
        return productRepository.getActivityProductIdListBySellerIdList(sellerIdList);
    }

    /**
     * 查询历史商品
     *
     * @param productIdList
     * @return
     */
    public List<HistoryProductModel> getHistoryProductListByProductIdList(List<String> productIdList) {
        return historyProductRepository.getHistoryProductListByProductIdList(productIdList);
    }
}
