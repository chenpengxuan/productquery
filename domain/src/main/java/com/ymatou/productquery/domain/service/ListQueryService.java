package com.ymatou.productquery.domain.service;

import com.ymatou.productquery.domain.mapper.ProductInListMapper;
import com.ymatou.productquery.domain.model.*;
import com.ymatou.productquery.domain.repo.mongorepo.*;
import com.ymatou.productquery.infrastructure.config.props.BizProps;
import com.ymatou.productquery.infrastructure.util.Tuple;
import com.ymatou.productquery.model.BizException;
import com.ymatou.productquery.model.res.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
    private HistoryProductRepository historyProductRepository;

    @Autowired
    private CommonQueryService commonQueryService;

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

        List<Products> productsList = commonQueryService.getProductListByProductIdList(pids);
        if (productsList == null || productsList.isEmpty()) {
            throw new BizException("商品不存在");
        }

        List<Catalogs> catalogsList = commonQueryService.getCatalogListByProductIdList(pids);
        if (catalogsList == null || catalogsList.isEmpty()) {
            throw new BizException("规格不存在");
        }

        List<LiveProducts> liveProductsList = commonQueryService.getLiveProductListByProductId(pids);
        List<ActivityProducts> activityProductsList = commonQueryService.getActivityProductListByProductIdList(pids);

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
    public List<ProductDetailDto> getProductDetailList(List<String> productIds, int nextActivityExpire, boolean tradeIsolation) {
        List<ProductDetailDto> productDetailDtoList = new ArrayList<>();
//        List<ProductTimeStamp> updateStampMap = productTimeStampRepository
//                .getTimeStampByProductIds(productIds, "cut,sut,lut,aut");
        List<Products> productsList;
        List<Catalogs> catalogsList;
        List<LiveProducts> liveProductsList;
        List<ActivityProducts> activityProductsList;
//        if (bizProps.isUseCache()) {
//            productsList = cache.getProductsByProductIds(productIds, updateStampMap);
//            catalogsList = cache.getCatalogsByProductIds(productIds, updateStampMap);
//            liveProductsList = cache.getLiveProductsByProductIds(productIds, updateStampMap);
//            activityProductsList = cache.getActivityProductList(productIds, updateStampMap, nextActivityExpire);
//
//        } else {
//            productsList = productRepository.getProductsByProductIds(productIds);
//            catalogsList = productRepository.getCatalogsByProductIds(productIds);
//            liveProductsList = liveProductRepository.getLiveProductList(productIds);
//            activityProductsList = activityProductRepository.getValidAndNextActivityProductByProductId(productIds, nextActivityExpire);
//        }
        productsList = commonQueryService.getProductListByProductIdList(productIds);
        if (productsList == null || productsList.isEmpty()) {
            throw new BizException("商品不存在");
        }

        catalogsList = commonQueryService.getCatalogListByProductIdList(productIds);
        if (catalogsList == null || catalogsList.isEmpty()) {
            throw new BizException("规格不存在");
        }

        liveProductsList = commonQueryService.getLiveProductListByProductId(productIds);
        activityProductsList = commonQueryService.getActivityProductListByProductIdList(productIds);

        for (String pid : productIds) {
            ProductDetailDto productDetailDto;
            Products product = productsList.stream().filter(t -> t.getProductId().equals(pid)).findFirst().orElse(null);
            if (product == null) {
                continue;
            }

            List<Catalogs> catalogs = catalogsList.stream().filter(t -> t.getProductId().equals(pid)).collect(Collectors.toList());
            List<ActivityProducts> activityProducts = activityProductsList.stream().filter(t -> t.getProductId().equals(pid)).collect(Collectors.toList());
            ActivityProducts activityProduct = ProductActivityService.getValidProductActivity(activityProducts);

            //活动
            if (activityProduct != null && (!activityProduct.isTradeIsolation() || tradeIsolation)) {
                productDetailDto = DtoMapper.toProductDetailDto(product, catalogs, activityProduct);

                productDetailDto.setProductActivity(DtoMapper.toProductActivityDto(activityProduct));
                productDetailDto.setValidStart(activityProduct.getStartTime());
                productDetailDto.setValidEnd(activityProduct.getEndTime());
                Tuple<Double, Double> maxmin = DtoMapper.getMaxMinPrice(productDetailDto.getCatalogList(), activityProduct);
                double max = Math.max(maxmin.first, Double.valueOf(product.getMaxCatalogPrice().split(",")[0]));
                double min = Math.min(maxmin.second, Double.valueOf(product.getMinCatalogPrice().split(",")[0]));
                productDetailDto.getProductActivity().setMaxActivityPrice(max);
                productDetailDto.getProductActivity().setMinActivityPrice(min);
            } else {
                productDetailDto = DtoMapper.toProductDetailDto(product, catalogs, activityProduct);
            }

            //下一场活动
            ActivityProducts nextActivityProduct = ProductActivityService.getNextProductActivity(activityProducts, nextActivityExpire, activityProduct);
            if (nextActivityProduct != null && (!activityProduct.isTradeIsolation() || tradeIsolation)) {
                productDetailDto.setNextActivity(DtoMapper.toProductActivityDto(nextActivityProduct));
                Tuple<Double, Double> maxmin = DtoMapper.getMaxMinPrice(productDetailDto.getCatalogList(), activityProduct);
                double max = Math.max(maxmin.first, Double.valueOf(product.getMaxCatalogPrice().split(",")[0]));
                double min = Math.min(maxmin.second, Double.valueOf(product.getMinCatalogPrice().split(",")[0]));
                productDetailDto.getNextActivity().setMaxActivityPrice(max);
                productDetailDto.getNextActivity().setMinActivityPrice(min);
            }

            //直播
            LiveProducts liveProduct = liveProductsList.stream().filter(t -> t.getProductId().equals(pid)).findFirst().orElse(null);
            productDetailDto.setLiveProduct(DtoMapper.toProductLiveDto(liveProduct));
            if (liveProduct != null) {
                productDetailDto.setValidStart(liveProduct.getStartTime());
                productDetailDto.setValidEnd(liveProduct.getEndTime());
            }

            // 商品的状态
            productDetailDto.setStatus(ProductStatusService.getProductStatus(product.getAction(), product.getValidStart()
                    , product.getValidEnd(), liveProduct, activityProduct));
            productDetailDtoList.add(productDetailDto);
        }

        return productDetailDtoList;
    }

    /**
     * 历史库中的商品列表
     *
     * @param productIds
     * @return
     */
    public List<ProductHistoryDto> getProductListByHistoryProductIdList(List<String> productIds) {
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
            List<Products> productsList = productRepository.getProductListByProductIdList(notHisProductId);
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
