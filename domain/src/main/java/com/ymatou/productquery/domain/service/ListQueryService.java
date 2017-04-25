package com.ymatou.productquery.domain.service;

import com.ymatou.productquery.domain.model.*;
import com.ymatou.productquery.domain.repo.mongorepo.*;
import com.ymatou.productquery.infrastructure.config.props.BizProps;
import com.ymatou.productquery.model.res.ProductDetailDto;
import com.ymatou.productquery.model.res.ProductInCartDto;
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
            if (activityProduct != null && (!activityProduct.isTradeIsolation() || tradeIsolation) && (activityProduct.getActivityCatalogList() != null)) {
                ActivityCatalogInfo activityCatalogInfo = activityProduct.getActivityCatalogList().stream().filter(t -> t.getCatalogId().equals(catalogId)).findFirst().orElse(null);
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
            productInCartDto.setStatus(ProductStatusService.getProductStatus(product.getAction(), product.getValidStart(), product.getValidEnd(), liveProduct, activityProduct));
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
                .getTimeStampByProductIds(productIds, "cut,sut");
        List<Products> productsList;
        List<Catalogs> catalogsList;
        if (bizProps.isUseCache()) {
            productsList = cache.getProductsByProductIds(productIds, updateStampMap);
            catalogsList = cache.getCatalogsByProductIds(productIds, updateStampMap);
        } else {
            productsList = productRepository.getProductsByProductIds(productIds);
            catalogsList = productRepository.getCatalogsByProductIds(productIds);
        }

        List<LiveProducts> liveProductsList = liveProductRepository.getLiveProductList(productIds);
        List<ActivityProducts> activityProductsList = activityProductRepository.getActivityProductList(productIds);

        return null;
    }

}
