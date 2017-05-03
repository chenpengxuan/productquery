package com.ymatou.productquery.domain.service;

import com.ymatou.productquery.domain.model.*;
import com.ymatou.productquery.domain.repo.mongorepo.HistoryProductRepository;
import com.ymatou.productquery.domain.repo.mongorepo.ProductRepository;
import com.ymatou.productquery.infrastructure.config.props.BizProps;
import com.ymatou.productquery.infrastructure.util.LogWrapper;
import com.ymatou.productquery.infrastructure.util.Tuple;
import com.ymatou.productquery.model.BizException;
import com.ymatou.productquery.model.res.DescPropertyDto;
import com.ymatou.productquery.model.res.ProductDescExtraDto;
import com.ymatou.productquery.model.res.ProductDetailDto;
import com.ymatou.productquery.model.res.SecKillProductActivityStockDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 单品查询相关
 * Created by zhangyong on 2017/4/10.
 */
@Component
public class ItemQueryService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    private CommonQueryService commonQueryService;

    @Autowired
    private HistoryProductRepository historyProductRepository;

    @Autowired
    BizProps bizProps;

    @Autowired
    private LogWrapper logWrapper;

    /**
     * 取复杂结构商品列表
     *
     * @param productId
     * @param nextActivityExpire
     * @param tradeIsolation
     * @return
     */
    public ProductDetailDto getProductDetail(String productId, int nextActivityExpire, boolean tradeIsolation) {
        Products product = commonQueryService.getProductByProductId(productId);
        if (product == null) {
            logWrapper.recordInfoLog("line 52,商品不存在，取历史商品，{}", productId);
            HistoryProductModel historyProductModel = historyProductRepository.getHistoryProductInfoByProductId(productId);
            return ProductMapperExtension.toProductDetailDto(historyProductModel);
        }
        List<String> productIds = new ArrayList<>();
        productIds.add(productId);
        List<Catalogs> catalogs = commonQueryService.getCatalogListByProductIdList(productIds);
        if (catalogs == null) {
            logWrapper.recordInfoLog("line 61,商品不存在，{}", productId);
            throw new BizException("商品不存在");
        }

        List<LiveProducts> liveProductsList = commonQueryService.getLiveProductListByProductId(productIds);
        List<ActivityProducts> activityProducts = commonQueryService.getActivityProductListByProductIdList(productIds);
        ActivityProducts activityProduct = ProductActivityService.getValidProductActivity(activityProducts);

        ProductDetailDto productDetailDto = setCurrentAndNextActivityProduct(product, catalogs, activityProducts
                , activityProduct, nextActivityExpire, tradeIsolation);

        //直播
        LiveProducts liveProduct = liveProductsList.stream().findFirst().orElse(null);
        productDetailDto.setLiveProduct(ProductMapperExtension.toProductLiveDto(liveProduct));
        // 设置商品的有效期, 直播有效取直播时间， 直播无效活动有效，取活动时间
        if (liveProduct != null) {
            productDetailDto.setValidStart(liveProduct.getStartTime());
            productDetailDto.setValidEnd(liveProduct.getEndTime());
        }

        // 商品的状态
        productDetailDto.setStatus(ProductStatusService.getProductStatus(product.getAction(), product.getValidStart()
                , product.getValidEnd(), liveProduct, activityProduct));

        return productDetailDto;
    }

    /**
     * 当前活动和下一场活动组装逻辑
     *
     * @param product
     * @param catalogs
     * @param activityProductsList
     * @param activityProduct
     * @param nextActivityExpire
     * @param tradeIsolation
     */
    public ProductDetailDto setCurrentAndNextActivityProduct(Products product, List<Catalogs> catalogs
            , List<ActivityProducts> activityProductsList, ActivityProducts activityProduct, int nextActivityExpire, boolean tradeIsolation) {
        ProductDetailDto productDetailDto;
        //活动
        if (activityProduct != null && (!activityProduct.isTradeIsolation() || tradeIsolation)) {
            productDetailDto = ProductMapperExtension.toProductDetailDto(product, catalogs, activityProduct);

            productDetailDto.setProductActivity(ProductMapperExtension.toProductActivityDto(activityProduct));
            productDetailDto.setValidStart(activityProduct.getStartTime());
            productDetailDto.setValidEnd(activityProduct.getEndTime());
            Tuple<Double, Double> maxmin = ProductMapperExtension.getMaxMinPrice(productDetailDto.getCatalogList(), activityProduct);
            double max = Math.max(maxmin.first, Double.valueOf(product.getMaxCatalogPrice().split(",")[0]));
            double min = Math.min(maxmin.second, Double.valueOf(product.getMinCatalogPrice().split(",")[0]));
            productDetailDto.getProductActivity().setMaxActivityPrice(max);
            productDetailDto.getProductActivity().setMinActivityPrice(min);
        } else {
            productDetailDto = ProductMapperExtension.toProductDetailDto(product, catalogs, activityProduct);
        }

        //下一场活动
        ActivityProducts nextActivityProduct = ProductActivityService.getNextProductActivity(activityProductsList, nextActivityExpire, activityProduct);
        if (nextActivityProduct != null && (!activityProduct.isTradeIsolation() || tradeIsolation)) {
            productDetailDto.setNextActivity(ProductMapperExtension.toProductActivityDto(nextActivityProduct));
            Tuple<Double, Double> maxmin = ProductMapperExtension.getMaxMinPrice(productDetailDto.getCatalogList(), nextActivityProduct);
            double max = Math.max(maxmin.first, Double.valueOf(product.getMaxCatalogPrice().split(",")[0]));
            double min = Math.min(maxmin.second, Double.valueOf(product.getMinCatalogPrice().split(",")[0]));
            productDetailDto.getNextActivity().setMaxActivityPrice(max);
            productDetailDto.getNextActivity().setMinActivityPrice(min);
        }
        return productDetailDto;
    }


    /**
     * 取秒杀商品的活动库存量
     * @param productId
     * @param activityId
     * @return
     */
    public List<SecKillProductActivityStockDto> getSecKillProductActivityStockList(String productId, int activityId) {
        List<String> productIdList = new ArrayList<>();
        productIdList.add(productId);

        List<SecKillProductActivityStockDto> stockDtoList = new ArrayList<>();

        Products product = commonQueryService.getProductByProductId(productId);
        List<ActivityProducts> activityProductList = commonQueryService.getActivityProductListByProductIdList(productIdList);

        if(product == null || activityProductList == null || activityProductList.isEmpty()) {
            return null;
        }

        ActivityProducts activityProduct = activityProductList.stream()
                .filter(a-> a.getActivityId() == activityId).findFirst().orElse(null);

        if(activityProduct == null || activityProduct.getCatalogs() == null || activityProduct.getCatalogs().isEmpty()) {
            return null;
        }

        activityProduct.getCatalogs().stream().forEach(c->{
            SecKillProductActivityStockDto dto = new SecKillProductActivityStockDto();
            dto.setProductId(activityProduct.getProductId());
            dto.setActivityId(activityProduct.getActivityId());
            dto.setProductActivityId(activityProduct.getProductInActivityId());
            dto.setCatalogId(c.getCatalogId());
            dto.setActivityStock(c.getActivityStock());

            stockDtoList.add(dto);
        });

        return stockDtoList;
    }

    /**
     * 取商品图文描述扩展信息
     * @param productId
     * @return
     */
    public ProductDescExtraDto getProductDescExtra(String productId)
    {
        ProductDescExtra descExtra = commonQueryService.getProductDescExtra(productId);
        if(descExtra == null) {
            return null;
        }

        ProductDescExtraDto dto = new ProductDescExtraDto();

        dto.setProductId(descExtra.getProductId());
        dto.setDescText(descExtra.getDescText());
        dto.setDescPicList(descExtra.getDescPicList());
        dto.setSizePicList(descExtra.getSizePicList());
        dto.setNoticeText(descExtra.getNoticeText());
        dto.setNoticePicList(descExtra.getNoticePicList());
        dto.setSellerIntroText(descExtra.getSellerInfoText());
        dto.setSellerIntroPicList(descExtra.getSellerIntroPicList());
        dto.setDescPropertyDtoList(getDescPropertyDtoList(descExtra.getPropertyList()));

        return dto;
    }


    /**
     * 商品属性对象转换
     * @param descPropertyInfoList
     * @return
     */
    private List<DescPropertyDto> getDescPropertyDtoList(List<ProductDescPropertyInfo> descPropertyInfoList)
    {
        if(descPropertyInfoList == null || descPropertyInfoList.isEmpty()) {
            return null;
        }

        List<DescPropertyDto> dtoList = new ArrayList<>();
        descPropertyInfoList.stream().forEach(c->{
            DescPropertyDto dto = new DescPropertyDto();
            dto.setKey(c.getKey());
            dto.setValue(c.getValue());

            dtoList.add(dto);
        });

        return dtoList;
    }

}
