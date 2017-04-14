package com.ymatou.productquery.domain.service;

import com.ymatou.productquery.domain.model.*;
import com.ymatou.productquery.domain.repo.Repository;
import com.ymatou.productquery.infrastructure.util.LogWrapper;
import com.ymatou.productquery.model.res.ProductInCartDto;
import com.ymatou.productquery.model.res.ProductStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by zhangyong on 2017/4/10.
 */
@Component
public class ListQueryService {

    @Autowired
    private Repository repository;

    public List<ProductInCartDto> getProductListFromShoppingCart(List<String> catalogIds, boolean tradeIsolation) {
        List<ProductInCartDto> productInCartDtoList = new ArrayList<>();
        List<String> pids = repository.getProductIdsByCatalogIds(catalogIds);
        if (pids == null || pids.isEmpty()) {
            return null;
        }

        List<Products> productsList = repository.getProductsByProductIds(pids);
        if (productsList == null || productsList.isEmpty()) {
            return null;
        }

        List<Catalogs> catalogsList = repository.getCatalogsByProductIds(pids);
        if (catalogsList == null || catalogsList.isEmpty()) {
            return null;
        }

        List<LiveProducts> liveProductsList = repository.getLiveProductList(pids);
        List<ActivityProducts> activityProductsList = repository.getActivityProductList(pids);
        ProductInCartDto productInCartDto = new ProductInCartDto();
        for (String catalogId : catalogIds) {
            Catalogs catalog = catalogsList.stream().filter(t -> t.getCid().equals(catalogId)).findFirst().orElse(null);
            String productid = catalog.getSpid();
            Products product = productsList.stream().filter(t -> t.getSpid().equals(productid)).findFirst().orElse(null);

            List<ActivityProducts> activityProductses = activityProductsList.stream().filter(t -> t.getSpid().equals(productid)).collect(Collectors.toList());
            ActivityProducts activityProduct = ProductActivityService.getValidProductActivity(activityProductses, catalog);
            if (activityProduct != null && (!activityProduct.isolation()|| tradeIsolation) && (activityProduct.getCatalogs() != null)) {
                ActivityCatalogInfo activityCatalogInfo = activityProduct.getCatalogs().stream().filter(t -> t.getCid().equals(catalogId)).findFirst().orElse(null);
                if (activityCatalogInfo != null) {
                    if (activityCatalogInfo.getStock() > 0) {
                        productInCartDto = DtoMapper.toProductInCartDto(product, catalog, activityProduct, catalogsList);
                        productInCartDto.setProductActivity(DtoMapper.toProductActivityCartDto(activityProduct));
                        productInCartDto.setValidStart(activityProduct.getStart());
                        productInCartDto.setValidEnd(activityProduct.getEnd());
                    }
                } else {
                    productInCartDto = DtoMapper.toProductInCartDto(product, catalog, null, catalogsList);
                }
            } else {
                productInCartDto = DtoMapper.toProductInCartDto(product, catalog, null, catalogsList);
            }
            LiveProducts liveProduct = liveProductsList.stream().filter(t -> t.getSpid().equals(productid)).findFirst().orElse(null);
            if (liveProduct != null) {
                productInCartDto.setLiveProduct(DtoMapper.toLiveProductCartDto(liveProduct));
                productInCartDto.setValidStart(liveProduct.getStart());
                productInCartDto.setValidEnd(liveProduct.getEnd());
            }
            productInCartDto.setStatus(ProductStatusService.getProductStatus(product.getAction(), product.getStart(), product.getEnd(), liveProduct, activityProduct));
            productInCartDtoList.add(productInCartDto);
        }
        return productInCartDtoList;
    }

}
