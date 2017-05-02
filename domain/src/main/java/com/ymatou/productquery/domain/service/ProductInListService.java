package com.ymatou.productquery.domain.service;

import com.ymatou.productquery.domain.mapper.ProductInListMapper;
import com.ymatou.productquery.domain.model.*;
import com.ymatou.productquery.model.res.ProductInListDto;
import com.ymatou.productquery.model.res.ProductStatusEnum;
import com.ymatou.productquery.model.res.TopProductInLiveDto;
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
        return null;
    }
}
