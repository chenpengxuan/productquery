package com.ymatou.productquery.domain.service;

import com.ymatou.productquery.domain.mapper.ProductInListMapper;
import com.ymatou.productquery.domain.model.*;
import com.ymatou.productquery.model.res.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by zhujinfeng on 2017/4/28.
 */
@Component
public class ProductInListService {

    @Autowired
    private CommonQueryService commonQueryService;

    /**
     * 取简单结构商品列表，（搜索商品， 大首页商品列表场景用）
     * @param productIdList
     * @param tradeIsolation
     * @return
     */
    public List<ProductInListDto> getProductList(List<String> productIdList, boolean tradeIsolation) {
        List<Products> productList = commonQueryService.getProductListByProductIdList(productIdList);
        if(productList == null || productList.isEmpty()){
            return null;
        }

        List<ProductInListDto> productDtoList = new ArrayList<>();

        List<Catalogs> catalogList = commonQueryService.getCatalogListByProductIdList(productIdList);
        List<ActivityProducts> activityProductList = commonQueryService.getActivityProductListByProductIdList(productIdList);
        List<LiveProducts> liveProductList = commonQueryService.getLiveProductListByProductId(productIdList);

        for (String productId:productIdList) {
            Products product = productList.stream().filter(c -> c.getProductId().equals(productId)).findFirst().orElse(null);
            List<Catalogs> catalogs = catalogList.stream().filter(c -> c.getProductId().equals(productId)).collect(Collectors.toList());
            if (product == null || catalogs == null || catalogs.isEmpty()) {
                //// FIXME: 2017/4/28 log 记录无商品日志
                continue;
            }

            // 组装列表商品对象
            ProductInListDto productDto = ProductInListMapper.toProductInListDto(product, catalogs);

            // 取活动商品
            List<ActivityProducts> activityProducts = activityProductList.stream().filter(c -> c.getProductId().equals(productId)).collect(Collectors.toList());
            ActivityProducts activityProduct = getValidActivityProduct(activityProducts);

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
            }

            // 取直播商品
            LiveProducts liveProduct = getValidLiveProduct(productId, liveProductList);
            if (liveProduct != null) {
                productDto.setLiveId(liveProduct.getLiveId());
                productDto.setValidStart(liveProduct.getStartTime());
                productDto.setValidEnd(liveProduct.getEndTime());
            }

            // 验证商品售卖状态
            productDto.setStatus(getProductStatus(product, activityProduct, liveProduct));

            productDtoList.add(productDto);
        }


        return null;
    }

    /**
     * 取有效的活动商品
     * @param activityProductList
     * @return
     */
    private ActivityProducts getValidActivityProduct(List<ActivityProducts> activityProductList)
    {
        if(activityProductList == null || activityProductList.isEmpty()) {
            return null;
        }
        Date now = new Date();
        ActivityProducts activityProduct = activityProductList.stream()
                .filter(c->c.getStartTime().getTime()<= now.getTime() && c.getEndTime().getTime() >= now.getTime()).findFirst().orElse(null);

        if(activityProduct != null && activityProduct.getCatalogs() != null && !activityProduct.getCatalogs().isEmpty()) {
            int activityStock = activityProduct.getCatalogs().stream().collect(Collectors.summingInt(ActivityCatalogInfo::getActivityStock));
            if(activityStock == 0) {
                return null;
            }
        }
        return activityProduct;
    }

    /**
     * 取活动价
     * @param quotePrice
     * @param activityProduct
     * @param priceType
     * @return
     */
    private double getActivityPrice(double quotePrice, ActivityProducts activityProduct, String priceType)
    {
        if(activityProduct == null || activityProduct.getCatalogs() == null || activityProduct.getCatalogs().isEmpty()) {
            return Math.round(quotePrice);
        }

        List<Double> priceList = activityProduct.getCatalogs().stream().map(t->t.getActivityPrice()).collect(Collectors.toList());
        if(priceType.equals("min"))
        {
            return Collections.min(priceList);
        }
        else
        {
            return Collections.max(priceList);
        }
    }

    /**
     * 取直播商品
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

    /**
     * 取商品的销售状态
     * @param product
     * @param activityProduct
     * @param liveProduct
     * @return
     */
    private ProductStatusEnum getProductStatus(Products product, ActivityProducts activityProduct, LiveProducts liveProduct) {
        if (product.getAction() == -1) {
            return ProductStatusEnum.Deleted;
        }

        if (activityProduct != null) {
            return ProductStatusEnum.Available;
        }

        if (liveProduct != null) {
            return ProductStatusEnum.Available;
        }

        Date date = new Date();
        return (product.getValidStart().getTime() <= date.getTime() && product.getValidEnd().getTime() >= date.getTime())
                ? ProductStatusEnum.Available : ProductStatusEnum.Disable;
    }

    /**
     * 取直播中置顶商品列表
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
     * @param sellerId
     * @param curPage
     * @param pageSize
     * @return
     */
    public List<ProductInListDto> getNewestProductListBySellerId(int sellerId, int curPage, int pageSize) {
        List<String> newestProductIdList = commonQueryService.getNewestProductIdList(sellerId, curPage, pageSize);
        if(newestProductIdList == null || newestProductIdList.isEmpty()) {
            return null;
        }

        return getProductList(newestProductIdList, false);
    }

    /**
     * 取买手热推商品列表
     * @param sellerId
     * @return
     */
    public List<ProductInListDto> getHotRecmdProductListBySellerId(int sellerId) {
        List<String> hotRecmdProductIdList = commonQueryService.getHotRecmdProductIdListBySellerId(sellerId);
        if(hotRecmdProductIdList == null || hotRecmdProductIdList.isEmpty()) {
            return null;
        }

        return getProductList(hotRecmdProductIdList, false);
    }

    /**
     * 取买手置顶商品和活动商品编号列表
     * @param sellerIdList
     * @return
     */

    public List<RecmdProductIdDto> getSellerRecommendProductList(List<Integer> sellerIdList) {
        List<String> topLiveProductIdList = commonQueryService.getTopLiveProductIdListBySellerIdList(sellerIdList);
        List<String> activityProductIdList = commonQueryService.getActivityProductIdListBySellerIdList(sellerIdList);

        List<RecmdProductIdDto> recmdProductList = new ArrayList<>();

        if(topLiveProductIdList != null && !topLiveProductIdList.isEmpty()) {
            topLiveProductIdList.stream().forEach(id -> {
                RecmdProductIdDto dto = new RecmdProductIdDto();
                dto.setProductId(id);
                dto.isTopProduct(true);
                recmdProductList.add(dto);
            });
        }

        if(activityProductIdList != null && !activityProductIdList.isEmpty()) {
            activityProductIdList.stream().forEach(id->{
                RecmdProductIdDto dto = recmdProductList.stream().filter(c->c.getProductId().equals(id)).findFirst().orElse(null);
                if(dto == null)
                {
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
}
