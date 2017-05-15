package com.ymatou.productquery.domain.service;

import com.ymatou.productquery.domain.model.*;
import com.ymatou.productquery.domain.model.cache.CacheProductInfo;
import com.ymatou.productquery.domain.repo.mongorepo.*;
import com.ymatou.productquery.model.BizException;
import com.ymatou.productquery.model.req.CatalogDeliveryDto;
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
    private CommonQueryService commonQueryService;

    @Autowired
    private ItemQueryService itemQueryService;

    /**
     * 购物车中商品列表(老版)
     *
     * @param catalogIds
     * @param tradeIsolation
     * @return
     */
    public List<ProductInCartDto> getProductListFromShoppingCart(List<String> catalogIds, boolean tradeIsolation) {
        List<ProductInCartDto> productInCartDtoList = new ArrayList<>();

        List<CacheProductInfo> cacheProductInfoList = commonQueryService.getProductListByCatalogIdList(catalogIds);
        if (cacheProductInfoList == null || cacheProductInfoList.isEmpty()) {
            return null;
        }

        List<String> pids = cacheProductInfoList.stream().map(t -> t.getProductId()).collect(Collectors.toList());
        List<LiveProducts> liveProductsList = commonQueryService.getLiveProductListByProductId(pids);
        List<ActivityProducts> activityProductsList = commonQueryService.getActivityProductListByProductIdList(pids);

        for (String catalogId : catalogIds) {
            CacheProductInfo cacheProductInfo = cacheProductInfoList.stream().filter(t -> t.getCatalogsList().stream().map(s -> s.getCatalogId())
                    .anyMatch(x -> x.equals(catalogId))).findFirst().orElse(null);

            if (cacheProductInfo == null)
                continue;

            ProductInCartDto productInCartDto;
            Map<String,Integer> productCatalogNumMap = commonQueryService.getCatalogNumByCatalogIdList(catalogIds);

            List<ActivityProducts> tempActivityProductList = activityProductsList.stream().filter(t -> t.getProductId()
                    .equals(cacheProductInfo.getProductId())).collect(Collectors.toList());
            ActivityProducts activityProduct = ProductActivityService.getValidProductActivity(tempActivityProductList);

            if (activityProduct != null && (!activityProduct.isTradeIsolation() || tradeIsolation) && (activityProduct.getCatalogs() != null)
                    && activityProduct.getCatalogs().stream().map(p -> p.getCatalogId()).anyMatch(t -> t.equals(catalogId))) {
                ActivityCatalogInfo activityCatalogInfo = activityProduct.getCatalogs().stream()
                        .filter(t -> t.getCatalogId().equals(catalogId)).findFirst().orElse(null);

                if (activityCatalogInfo != null && activityCatalogInfo.getActivityStock() > 0) {
                    productInCartDto = ProductMapperExtension.toProductInCartDto(cacheProductInfo, activityProduct, catalogId,productCatalogNumMap);
                    productInCartDto.setProductActivity(ProductMapperExtension.toProductActivityCartDto(activityProduct));
                    productInCartDto.setValidStart(activityProduct.getStartTime());
                    productInCartDto.setValidEnd(activityProduct.getEndTime());
                } else {
                    productInCartDto = ProductMapperExtension.toProductInCartDto(cacheProductInfo, null, catalogId,productCatalogNumMap);
                }
            } else {
                productInCartDto = ProductMapperExtension.toProductInCartDto(cacheProductInfo, null, catalogId,productCatalogNumMap);
            }

            LiveProducts liveProduct = liveProductsList.stream().filter(t -> t.getProductId().equals(cacheProductInfo.getProductId())).findFirst().orElse(null);
            if (liveProduct != null) {
                productInCartDto.setLiveProduct(ProductMapperExtension.toLiveProductCartDto(liveProduct));
                productInCartDto.setValidStart(liveProduct.getStartTime());
                productInCartDto.setValidEnd(liveProduct.getEndTime());
            }
            productInCartDto.setStatus(ProductStatusService.getProductStatus(cacheProductInfo.getAction(), cacheProductInfo.getValidStart()
                    , cacheProductInfo.getValidEnd(), liveProduct, activityProduct));
            productInCartDtoList.add(productInCartDto);
        }
        return productInCartDtoList;
    }

    /**
     * 多物流的购物车商品列表
     *
     * @param catalogDeliveryDtoList
     * @param tradeIsolation
     * @return
     */
    public List<ProductInCartDto> getProductListFromShoppingCartDeliveryExtra(List<CatalogDeliveryDto> catalogDeliveryDtoList, boolean tradeIsolation) {
        List<String> catalogids = catalogDeliveryDtoList.stream().map(t -> t.getCatalogId()).distinct().collect(Collectors.toList());
        List<ProductInCartDto> productInCartDtoList = getProductListFromShoppingCart(catalogids, tradeIsolation);
        if (productInCartDtoList != null && !productInCartDtoList.isEmpty()) {
            List<String> extraDeliveryCatalogIds = catalogDeliveryDtoList.stream().filter(t -> t.getDeliveryType() > 0)
                    .map(x -> x.getCatalogId()).distinct().collect(Collectors.toList());
            if (extraDeliveryCatalogIds.isEmpty()) {
                return productInCartDtoList;
            }
            productInCartDtoList.stream().forEach(t ->
            {
                CatalogDeliveryDto catalogDeliveryDto = catalogDeliveryDtoList.stream().filter(x -> x.getCatalogId()
                        .equals(t.getCatalogId())).findAny().orElse(null);
                //符合多物流
                if (extraDeliveryCatalogIds.contains(t.getCatalogId()) && catalogDeliveryDto != null) {
                    if (catalogDeliveryDto.getDeliveryType() == t.getExtraDeliveryType()) {
                        t.setDeliveryMethod(t.getExtraDeliveryType());
                        t.setPrice(t.getPrice() + t.getExtraDeliveryFee());
                    } else if (catalogDeliveryDto.getDeliveryType() != t.getDeliveryMethod()) {
                        t.setStatus(ProductStatusEnum.Disable.getCode());
                    }
                }
            });
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

        List<Products> productsList;
        List<Catalogs> catalogsList;
        List<LiveProducts> liveProductsList;
        List<ActivityProducts> activityProductsList;

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
            Products product = productsList.stream().filter(t -> t.getProductId().equals(pid)).findFirst().orElse(null);
            if (product == null) {
                continue;
            }

            List<Catalogs> catalogs = catalogsList.stream().filter(t -> t.getProductId().equals(pid)).collect(Collectors.toList());
            List<ActivityProducts> activityProducts = activityProductsList.stream().filter(t -> t.getProductId().equals(pid)).collect(Collectors.toList());
            ActivityProducts activityProduct = ProductActivityService.getValidProductActivity(activityProducts);

            ProductDetailDto productDetailDto = itemQueryService.setCurrentAndNextActivityProduct(product, catalogs, activityProducts,
                    activityProduct, nextActivityExpire, tradeIsolation);

            //直播
            LiveProducts liveProduct = liveProductsList.stream().filter(t -> t.getProductId().equals(pid)).findFirst().orElse(null);
            productDetailDto.setLiveProduct(ProductMapperExtension.toProductLiveDto(liveProduct));
            // 设置商品的有效期, 直播有效取直播时间， 直播无效活动有效，取活动时间
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
        List<HistoryProductModel> productDetailModelList = commonQueryService.getHistoryProductListByProductIdList(productIds);
        if (productDetailModelList == null || productDetailModelList.isEmpty()) {
            notHisProductId = productIds;
        } else {
            for (String pid : productIds) {
                HistoryProductModel productDetail = productDetailModelList.stream().filter(t -> t.getProductId().equals(pid)).findFirst().orElse(null);
                if (productDetail == null) {
                    notHisProductId.add(pid);
                    continue;
                }
                ProductHistoryDto productHistoryDto = ProductMapperExtension.toProductHistoryDto(productDetail);
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
                    ProductHistoryDto pr = ProductMapperExtension.toProductHistoryDto(product);
                    pr.setStatus(ProductStatusService.getProductStatus(product.getAction(), product.getValidStart(), product.getValidEnd(), null, null));
                    productHistoryDtoList.add(pr);
                }
            }
        }
        return productHistoryDtoList;
    }

    /**
     * 取简单结构商品列表，（搜索商品， 大首页商品列表场景用）
     *
     * @param productIdList
     * @param tradeIsolation
     * @return
     */
    public List<ProductInListDto> getProductList(List<String> productIdList, boolean tradeIsolation) {
        List<Products> productList = commonQueryService.getProductListByProductIdList(productIdList);
        if (productList == null || productList.isEmpty()) {
            return null;
        }

        List<ProductInListDto> productDtoList = new ArrayList<>();

        List<Catalogs> catalogList = commonQueryService.getCatalogListByProductIdList(productIdList);
        List<ActivityProducts> activityProductList = commonQueryService.getActivityProductListByProductIdList(productIdList);
        List<LiveProducts> liveProductList = commonQueryService.getLiveProductListByProductId(productIdList);

        for (String productId : productIdList) {
            Products product = productList.stream().filter(c -> c.getProductId().equals(productId)).findFirst().orElse(null);
            List<Catalogs> catalogs = catalogList.stream().filter(c -> c.getProductId().equals(productId)).collect(Collectors.toList());
            if (product == null || catalogs == null || catalogs.isEmpty()) {
                //// FIXME: 2017/4/28 log 记录无商品日志
                continue;
            }

            // 组装列表商品对象
            ProductInListDto productDto = ProductMapperExtension.toProductInListDto(product, catalogs);

            // 取活动商品
            List<ActivityProducts> activityProducts = activityProductList.stream().filter(c -> c.getProductId().equals(productId)).collect(Collectors.toList());
            ActivityProducts activityProduct = ProductActivityService.getValidProductActivity(activityProducts);

            if (activityProduct != null && (!activityProduct.isTradeIsolation() || tradeIsolation)) {
                double minActivityPrice = getActivityPrice(productDto.getMinPrice(), activityProduct, "min");
                double maxActivityPrice = getActivityPrice(productDto.getMaxPrice(), activityProduct, "max");
                int activityStock = activityProduct.getCatalogs().stream().collect(Collectors.summingInt(ActivityCatalogInfo::getActivityStock));

                productDto.setMinPrice(minActivityPrice);
                productDto.setMaxPrice(maxActivityPrice);
                productDto.setStock(activityStock);
                productDto.setActivityId(activityProduct.getActivityId());
                productDto.setValidStart(activityProduct.getStartTime());
                productDto.setValidEnd(activityProduct.getEndTime());
                productDto.setMarketPrice(activityProduct.getMarketPrice());
            }

            // 取直播商品
            LiveProducts liveProduct = getValidLiveProduct(productId, liveProductList);
            if (liveProduct != null) {
                productDto.setLiveId(liveProduct.getLiveId());
                productDto.setValidStart(liveProduct.getStartTime());
                productDto.setValidEnd(liveProduct.getEndTime());
            }

            // 验证商品售卖状态
            productDto.setStatus(ProductStatusService.getProductStatus(product.getAction(), product.getValidStart(), product.getValidEnd(), liveProduct, activityProduct));

            productDtoList.add(productDto);
        }

        return productDtoList;
    }

    /**
     * 取买手置顶商品和活动商品编号列表
     *
     * @param sellerIdList
     * @return
     */
    public List<RecmdProductIdDto> getSellerRecommendProductList(List<Integer> sellerIdList) {
        List<String> topLiveProductIdList = commonQueryService.getTopLiveProductIdListBySellerIdList(sellerIdList);
        List<String> activityProductIdList = commonQueryService.getActivityProductIdListBySellerIdList(sellerIdList);

        List<RecmdProductIdDto> recmdProductList = new ArrayList<>();

        if (topLiveProductIdList != null && !topLiveProductIdList.isEmpty()) {
            topLiveProductIdList.stream().forEach(id -> {
                RecmdProductIdDto dto = new RecmdProductIdDto();
                dto.setProductId(id);
                dto.isTopProduct(true);
                recmdProductList.add(dto);
            });
        }

        if (activityProductIdList != null && !activityProductIdList.isEmpty()) {
            activityProductIdList.stream().forEach(id -> {
                RecmdProductIdDto dto = recmdProductList.stream().filter(c -> c.getProductId().equals(id)).findFirst().orElse(null);
                if (dto == null) {
                    dto = new RecmdProductIdDto();
                    dto.setProductId(id);
                    dto.isTopProduct(false);
                    recmdProductList.add(dto);
                }
            });
        }
        return recmdProductList;
    }

    /**
     * 取直播中置顶商品列表
     *
     * @param liveId
     * @return
     */
    public List<TopProductInLiveDto> getTopProductListByLiveId(int liveId) {
        List<String> topProductIdList = commonQueryService.getTopProductIdListByLiveId(liveId);
        if (topProductIdList == null || topProductIdList.isEmpty()) {
            return null;
        }

        List<ProductInListDto> productList = getProductList(topProductIdList, false);
        if (productList == null || productList.isEmpty()) {
            return null;
        }

        List<TopProductInLiveDto> topProductList = new ArrayList<>();
        productList.stream().forEach(product -> {
            TopProductInLiveDto topProduct = new TopProductInLiveDto();
            topProduct.setLiveId(product.getLiveId());
            topProduct.setProductId(product.getProductId());
            topProduct.setPicUrl(product.getMainPic());
            topProduct.setPrice(product.getMinPrice());
            topProduct.isPspProduct(product.getIsPspProduct());
            topProductList.add(topProduct);
        });

        return topProductList;
    }

    /**
     * 取买手新品列表
     *
     * @param sellerId
     * @param curPage
     * @param pageSize
     * @return
     */
    public List<ProductInListDto> getNewestProductListBySellerId(int sellerId, int curPage, int pageSize) {
        List<String> newestProductIdList = commonQueryService.getNewestProductIdList(sellerId, curPage, pageSize);
        if (newestProductIdList == null || newestProductIdList.isEmpty()) {
            return null;
        }

        return getProductList(newestProductIdList, false);
    }

    /**
     * 取买手热推商品列表
     *
     * @param sellerId
     * @return
     */
    public List<ProductInListDto> getHotRecmdProductListBySellerId(int sellerId) {
        List<String> hotRecmdProductIdList = commonQueryService.getHotRecmdProductIdListBySellerId(sellerId);
        if (hotRecmdProductIdList == null || hotRecmdProductIdList.isEmpty()) {
            return null;
        }

        return getProductList(hotRecmdProductIdList, false);
    }


    /**
     * 取活动价
     *
     * @param quotePrice
     * @param activityProduct
     * @param priceType
     * @return
     */
    private double getActivityPrice(double quotePrice, ActivityProducts activityProduct, String priceType) {
        if (activityProduct == null || activityProduct.getCatalogs() == null || activityProduct.getCatalogs().isEmpty()) {
            return Math.round(quotePrice);
        }

        List<Double> priceList = activityProduct.getCatalogs().stream().map(t -> t.getActivityPrice()).collect(Collectors.toList());
        if (priceType.equals("min")) {
            return Collections.min(priceList);
        } else {
            return Collections.max(priceList);
        }
    }

    /**
     * 取直播商品
     *
     * @param liveProductList
     * @return
     */
    private LiveProducts getValidLiveProduct(String productId, List<LiveProducts> liveProductList) {
        if (liveProductList == null || liveProductList.isEmpty()) {
            return null;
        }
        Date date = new Date();

        return liveProductList.stream()
                .filter(c -> c.getProductId().equals(productId) && c.getStartTime().getTime() <= date.getTime()
                        && c.getEndTime().getTime() >= date.getTime() && c.getSellStatus() == 1).findFirst().orElse(null);
    }


}
