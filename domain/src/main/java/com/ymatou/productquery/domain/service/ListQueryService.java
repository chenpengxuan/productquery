package com.ymatou.productquery.domain.service;

import com.ymatou.productquery.domain.model.*;
import com.ymatou.productquery.domain.repo.mongorepo.*;
import com.ymatou.productquery.infrastructure.config.props.BizProps;
import com.ymatou.productquery.infrastructure.util.Tuple;
import com.ymatou.productquery.model.res.ProductDetailDto;
import com.ymatou.productquery.model.res.ProductHistoryDto;
import com.ymatou.productquery.model.res.ProductInCartDto;
import com.ymatou.productquery.model.res.ProductStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.ymatou.productquery.domain.cache.Cache;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by zhangyong on 2017/4/10.
 */
@Component
public class ListQueryService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private LiveProductRepository liveProductRepository;

    @Autowired
    private ActivityProductRepository activityProductRepository;

    @Autowired
    private ProductTimeStampRepository productTimeStampRepository;

    @Autowired
    private HistoryProductRepository historyProductRepository;

    @Autowired
    private Cache cache;

    @Autowired
    private BizProps bizProps;

    /**
     * 购物车中商品列表
     *
     * @param catalogIds
     * @param tradeIsolation
     * @return
     */
    public List<ProductInCartDto> getProductListFromShoppingCart(List<String> catalogIds, boolean tradeIsolation) {
        List<ProductInCartDto> productInCartDtoList = new ArrayList<>();
        List<String> pids = productRepository.getProductIdsByCatalogIds(catalogIds);
        if (pids == null || pids.isEmpty()) {
            return null;
        }

        List<Products> productsList = productRepository.getProductsByProductIds(pids);
        if (productsList == null || productsList.isEmpty()) {
            return null;
        }

        List<Catalogs> catalogsList = productRepository.getCatalogsByProductIds(pids);
        if (catalogsList == null || catalogsList.isEmpty()) {
            return null;
        }

        List<LiveProducts> liveProductsList = liveProductRepository.getLiveProductList(pids);
        List<ActivityProducts> activityProductsList = activityProductRepository.getActivityProductList(pids);
        ProductInCartDto productInCartDto = new ProductInCartDto();
        for (String catalogId : catalogIds) {
            Catalogs catalog = catalogsList.stream().filter(t -> t.getCatalogId().equals(catalogId)).findFirst().orElse(null);
            String productId = catalog.getProductId();
            Products product = productsList.stream().filter(t -> t.getProductId().equals(productId)).findFirst().orElse(null);

            List<ActivityProducts> tempActivityProductList = activityProductsList.stream().filter(t -> t.getProductId().equals(productId)).collect(Collectors.toList());
            ActivityProducts activityProduct = ProductActivityService.getValidProductActivity(tempActivityProductList, catalog);
            if (activityProduct != null && (!activityProduct.isTradeIsolation() || tradeIsolation) && (activityProduct.getCatalogs() != null)) {
                ActivityCatalogInfo activityCatalogInfo = activityProduct.getCatalogs().stream()
                        .filter(t -> t.getCatalogId().equals(catalogId)).findFirst().orElse(null);
                if (activityCatalogInfo != null) {
                    if (activityCatalogInfo.getActivityStock() > 0) {
                        productInCartDto = DtoMapper.toProductInCartDto(product, catalog, activityProduct, catalogsList);
                        productInCartDto.setProductActivity(DtoMapper.toProductActivityCartDto(activityProduct));
                        productInCartDto.setValidStart(activityProduct.getStartTime());
                        productInCartDto.setValidEnd(activityProduct.getEndTime());
                    }
                } else {
                    productInCartDto = DtoMapper.toProductInCartDto(product, catalog, null, catalogsList);
                }
            } else {
                productInCartDto = DtoMapper.toProductInCartDto(product, catalog, null, catalogsList);
            }
            LiveProducts liveProduct = liveProductsList.stream().filter(t -> t.getProductId().equals(productId)).findFirst().orElse(null);
            if (liveProduct != null) {
                productInCartDto.setLiveProduct(DtoMapper.toLiveProductCartDto(liveProduct));
                productInCartDto.setValidStart(liveProduct.getStartTime());
                productInCartDto.setValidEnd(liveProduct.getEndTime());
            }
            productInCartDto.setStatus(ProductStatusService.getProductStatus(product.getAction(), product.getValidStart()
                    , product.getValidEnd(), liveProduct, activityProduct));
            productInCartDtoList.add(productInCartDto);
        }
        return productInCartDtoList;
    }

    /**
     * 取复杂结构商品列表
     *
     * @param productIds
     * @param nextActivityExpire
     * @param tradeIsolation
     * @return
     */
    public List<ProductDetailDto> GetProductDetailList(List<String> productIds, int nextActivityExpire, boolean tradeIsolation) {
        List<ProductDetailDto> productDetailDtoList = new ArrayList<>();
        List<ProductTimeStamp> updateStampMap = productTimeStampRepository
                .getTimeStampByProductIds(productIds, "cut,sut,lut,aut");
        List<Products> productsList;
        List<Catalogs> catalogsList;
        List<LiveProducts> liveProductsList;
        Map<String, Tuple<ActivityProducts, ActivityProducts>> activityProductsList;
        if (bizProps.isUseCache()) {
            productsList = cache.getProductsByProductIds(productIds, updateStampMap);
            catalogsList = cache.getCatalogsByProductIds(productIds, updateStampMap);
            liveProductsList = cache.getLiveProductsByProductIds(productIds, updateStampMap);
            activityProductsList = cache.getActivityProductList(productIds, updateStampMap, nextActivityExpire);

        } else {
            productsList = productRepository.getProductsByProductIds(productIds);
            catalogsList = productRepository.getCatalogsByProductIds(productIds);
            liveProductsList = liveProductRepository.getLiveProductList(productIds);
            activityProductsList = activityProductRepository.getValidAndNextActivityProductByProductId(productIds, nextActivityExpire);
        }
//        for (String pid : productIds) {
//            ProductDetailDto productDetailDto;
//            Products product = productsList.stream().filter(t -> t.getProductId().equals(pid)).findFirst().orElse(null);
//            if (product == null) {
//                continue;
//            }
//            List<ActivityProducts> activityProducts = activityProductsList.stream().filter(t -> t.getProductId().equals(pid)).collect(Collectors.toList());
//            ActivityProducts activityProduct = ProductActivityService.getValidProductActivity(activityProducts, catalog);
//            if (activityProduct != null && (!activityProduct.isTradeIsolation() || tradeIsolation)) {
//                productDetailDto = DtoMapper.
//            }
//        }

        return null;
    }

    /**
     * 历史库中的商品列表
     *
     * @param productIds
     * @return
     */
    public List<ProductHistoryDto> GetProductListByHistoryProductIdList(List<String> productIds) {
        List<ProductHistoryDto> productHistoryDtoList = new ArrayList<>();
        List<String> notHisProductId = new ArrayList<>();
        List<HistoryProductModel> productDetailModelList = historyProductRepository.getHistoryProductListByProductIdList(productIds);
        if (productDetailModelList == null || productDetailModelList.isEmpty()) {
            notHisProductId = productIds;
        } else {
            for (String pid : productIds) {
                HistoryProductModel productDetail = productDetailModelList.stream().filter(t -> t.getProductId().equals(pid)).findFirst().orElse(null);
                if (productDetail == null) {
                    notHisProductId.add(pid);
                    continue;
                }
                ProductHistoryDto productHistoryDto = DtoMapper.toProductHistoryDto(productDetail);
                productHistoryDto.setStatus(ProductStatusEnum.Disable.getCode());
                productHistoryDtoList.add(productHistoryDto);
            }
        }
        if (notHisProductId != null && !notHisProductId.isEmpty()) {
            List<Products> productsList = productRepository.getProductsByProductIds(notHisProductId);
            if (productsList != null) {
                for (String pid : notHisProductId
                        ) {
                    Products product = productsList.stream().filter(t -> t.getProductId().equals(pid)).findFirst().orElse(null);
                    if (product == null) {
                        continue;
                    }
                    ProductHistoryDto pr = DtoMapper.toProductHistoryDto(product);
                    pr.setStatus(ProductStatusService.getProductStatus(product.getAction(), product.getValidStart(), product.getValidEnd(), null, null));
                    productHistoryDtoList.add(pr);
                }
            }
        }
        return productHistoryDtoList;
    }
}
