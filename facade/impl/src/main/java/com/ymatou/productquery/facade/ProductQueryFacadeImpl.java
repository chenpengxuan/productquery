package com.ymatou.productquery.facade;

import com.alibaba.dubbo.config.annotation.Service;
import com.ymatou.productquery.domain.service.ItemQueryService;
import com.ymatou.productquery.domain.service.ListQueryService;
import com.ymatou.productquery.model.req.*;
import com.ymatou.productquery.model.res.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangyong on 2017/4/10.
 */
@Service(protocol = {"rest", "dubbo"})
@Component
@Path("")
public class ProductQueryFacadeImpl implements ProductQueryFacade {

    @Autowired
    private ListQueryService listQueryService;

    @Autowired
    private ItemQueryService itemQueryService;

    @GET
    @Path("/warmup")
    @Produces(MediaType.TEXT_HTML)
    @Override
    public String warmup() {
        return "ok";
    }

    /**
     * 购物车中商品列表（普通购物车中用）
     *
     * @param request
     * @return
     */
    @Override
    @POST
    @Path("/{api:(?i:api)}/{Product:(?i:Product)}/{GetCatalogListByCatalogIdList:(?i:GetCatalogListByCatalogIdList)}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes(MediaType.APPLICATION_JSON)
    public BaseResponseNetAdapter getCatalogListByCatalogIdList(GetCatalogListByCatalogIdListRequest request) {
        List<ProductInCartDto> result = listQueryService.getProductListFromShoppingCart(request.getCatalogIdList(), false);

        Map<String, Object> productList = new HashMap<>();
        productList.put("ProductList", result);
        return BaseResponseNetAdapter.newSuccessInstance(productList);
    }

    /**
     * 购物车中商品列表（交易隔离中用）
     *
     * @param request
     * @return
     */
    @Override
    @POST
    @Path("/{api:(?i:api)}/{Product:(?i:Product)}/{GetCatalogListByTradeIsolation:(?i:GetCatalogListByTradeIsolation)}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes(MediaType.APPLICATION_JSON)
    public BaseResponseNetAdapter getCatalogListByTradeIsolation(GetCatalogListByTradeIsolationRequest request) {
        List<ProductInCartDto> result = listQueryService.getProductListFromShoppingCart(request.getCatalogIdList(), true);

        Map<String, Object> productList = new HashMap<>();
        productList.put("ProductList", result);
        return BaseResponseNetAdapter.newSuccessInstance(productList);
    }

    /**
     * 多物流购物车接口（普通购物车中用）
     *
     * @param request
     * @return
     */
    @Override
    @POST
    @Path("/{api:(?i:api)}/{Product:(?i:Product)}/{GetCatalogListByDeliveryExtraIsolation:(?i:GetCatalogListByDeliveryExtraIsolation)}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes(MediaType.APPLICATION_JSON)
    public BaseResponseNetAdapter getCatalogListByDeliveryExtraIsolation(GetCatalogListByDeliveryExtraIsolationRequest request) {
        List<ProductInCartDto> result = listQueryService.getProductListFromShoppingCartDeliveryExtra(request.getCatalogIdList(), false);

        Map<String, Object> productList = new HashMap<>();
        productList.put("ProductList", result);
        return BaseResponseNetAdapter.newSuccessInstance(productList);
    }

    /**
     * 多物流购物车接口（交易隔离中用）
     *
     * @param request
     * @return
     */
    @Override
    @POST
    @Path("/{api:(?i:api)}/{Product:(?i:Product)}/{GetCatalogListByDeliveryExtra:(?i:GetCatalogListByDeliveryExtra)}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes(MediaType.APPLICATION_JSON)
    public BaseResponseNetAdapter getCatalogListByDeliveryExtra(GetCatalogListByDeliveryExtraRequest request) {
        List<ProductInCartDto> result = listQueryService.getProductListFromShoppingCartDeliveryExtra(request.getCatalogIdList(), true);

        Map<String, Object> productList = new HashMap<>();
        productList.put("ProductList", result);
        return BaseResponseNetAdapter.newSuccessInstance(productList);
    }

    /**
     * 商品明细列表
     *
     * @param request
     * @return
     */
    @Override
    @POST
    @Path("/{api:(?i:api)}/{Product:(?i:Product)}/{GetProductDetailListByProductIdList:(?i:GetProductDetailListByProductIdList)}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes(MediaType.APPLICATION_JSON)
    public BaseResponseNetAdapter getProductDetailListByProductIdList(GetProductDetailListByProductIdListRequest request) {
        List<ProductDetailDto> result = listQueryService.getProductDetailList(request.getProductIdList(), request.getNextActivityExpire(), false);

        Map<String, Object> productList = new HashMap<>();
        productList.put("ProductList", result);
        return BaseResponseNetAdapter.newSuccessInstance(productList);
    }

    /**
     * 商品明细列表（交易隔离）
     *
     * @param request
     * @return
     */
    @Override
    @POST
    @Path("/{api:(?i:api)}/{Product:(?i:Product)}/{GetProductDetailListByTradeIsolation:(?i:GetProductDetailListByTradeIsolation)}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes(MediaType.APPLICATION_JSON)
    public BaseResponseNetAdapter getProductDetailListByTradeIsolation(GetProductDetailListByTradeIsolationRequest request) {
        List<ProductDetailDto> result = listQueryService.getProductDetailList(request.getProductIdList(), request.getNextActivityExpire(), true);

        Map<String, Object> productList = new HashMap<>();
        productList.put("ProductList", result);
        return BaseResponseNetAdapter.newSuccessInstance(productList);
    }

    /**
     * 根据商品id获取商品详情
     *
     * @param request
     * @return
     */
    @Override
    @GET
    @Path("/{api:(?i:api)}/{Product:(?i:Product)}/{GetProductInfoByProductId:(?i:GetProductInfoByProductId)}")
    @Produces({MediaType.APPLICATION_JSON})
    public BaseResponseNetAdapter getProductInfoByProductId(@BeanParam GetProductInfoByProductIdRequest request) {
        ProductDetailDto result = itemQueryService.getProductDetail(request.getProductId(), request.getNextActivityExpire(), false);
        Map<String, Object> productList = new HashMap<>();
        productList.put("Product", result);
        return BaseResponseNetAdapter.newSuccessInstance(productList);
    }

    /**
     * 根据商品编号取交易隔离的商品信息
     *
     * @param request
     * @return
     */
    @Override
    @GET
    @Path("/{api:(?i:api)}/{Product:(?i:Product)}/{GetProductInfoByTradeIsolation:(?i:GetProductInfoByTradeIsolation)}")
    @Produces({MediaType.APPLICATION_JSON})
    public BaseResponseNetAdapter getProductInfoByTradeIsolation(@BeanParam GetProductInfoByTradeIsolationRequest request) {
        ProductDetailDto result = itemQueryService.getProductDetail(request.getProductId(), request.getNextActivityExpire(), true);
        Map<String, Object> productList = new HashMap<>();
        productList.put("Product", result);
        return BaseResponseNetAdapter.newSuccessInstance(productList);
    }

    /**
     * 历史商品列表
     *
     * @param request
     * @return
     */
    @Override
    @POST
    @Path("/{api:(?i:api)}/{Product:(?i:Product)}/{GetProductListByHistoryProductIdList:(?i:GetProductListByHistoryProductIdList)}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes(MediaType.APPLICATION_JSON)
    public BaseResponseNetAdapter getProductListByHistoryProductIdList(GetProductListByHistoryProductIdListRequest request) {
        List<ProductHistoryDto> productHistoryDtoList = listQueryService.getProductListByHistoryProductIdList(request.getProductIdList());
        Map<String, Object> productList = new HashMap<>();
        productList.put("ProductList", productHistoryDtoList);
        return BaseResponseNetAdapter.newSuccessInstance(productList);
    }

    /**
     * 商品简化列表服务(交易隔离)
     *
     * @param request
     * @return
     */
    @Override
    @POST
    @Path("/{api:(?i:api)}/{Product:(?i:Product)}/{GetProductListByTradeIsolation:(?i:GetProductListByTradeIsolation)}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes(MediaType.APPLICATION_JSON)
    public BaseResponseNetAdapter getProductListByTradeIsolation(GetProductListByTradeIsolationRequest request) {
        List<ProductInListDto> productDtoList = listQueryService.getProductList(request.getProductIdList(), true);
        Map<String, Object> productList = new HashMap<>();
        productList.put("ProductList", productDtoList);
        return BaseResponseNetAdapter.newSuccessInstance(productList);
    }

    /**
     * 商品简化列表服务
     *
     * @param request
     * @return
     */
    @Override
    @POST
    @Path("/{api:(?i:api)}/{Product:(?i:Product)}/{GetProductListByProductIdList:(?i:GetProductListByProductIdList)}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes(MediaType.APPLICATION_JSON)
    public BaseResponseNetAdapter getProductListByProductIdList(GetProductListByProductIdListRequest request) {
        List<ProductInListDto> productDtoList = listQueryService.getProductList(request.getProductIdList(), false);
        Map<String, Object> productList = new HashMap<>();
        productList.put("ProductList", productDtoList);
        return BaseResponseNetAdapter.newSuccessInstance(productList);
    }

    /**
     * 取直播中置顶商品列表
     *
     * @param request
     * @return
     */
    @Override
    @GET
    @Path("/{api:(?i:api)}/{Product:(?i:Product)}/{GetTopProductListByLiveId:(?i:GetTopProductListByLiveId)}")
    @Produces({MediaType.APPLICATION_JSON})
    public BaseResponseNetAdapter getTopProductListByLiveId(@BeanParam GetTopProductListByLiveIdRequest request) {
        List<TopProductInLiveDto> productDtoList = listQueryService.getTopProductListByLiveId(request.getLiveId());
        Map<String, Object> productList = new HashMap<>();
        productList.put("ProductList", productDtoList);
        return BaseResponseNetAdapter.newSuccessInstance(productList);
    }

    /**
     * 取买手新品列表
     *
     * @param request
     * @return
     */
    @Override
    @GET
    @Path("/{api:(?i:api)}/{Product:(?i:Product)}/{GetNewestProductList:(?i:GetNewestProductList)}")
    @Produces({MediaType.APPLICATION_JSON})
    public BaseResponseNetAdapter getNewestProductList(GetNewestProductListBySellerIdRequest request) {
        List<ProductInListDto> productDtoList = listQueryService
                .getNewestProductListBySellerId(request.getSellerId(), request.getCurPage(), request.getPageSize());
        Map<String, Object> productList = new HashMap<>();
        productList.put("ProductList", productDtoList);
        return BaseResponseNetAdapter.newSuccessInstance(productList);
    }

    /**
     * 买手热推商品列表
     *
     * @param request
     * @return
     */
    @Override
    @GET
    @Path("/{api:(?i:api)}/{Product:(?i:Product)}/{GetHotRecmdProductListBySellerId:(?i:GetHotRecmdProductListBySellerId)}")
    @Produces({MediaType.APPLICATION_JSON})
    public BaseResponseNetAdapter getHotRecmdProductListBySellerId(GetHotRecmdProductListBySellerIdRequest request) {
        List<ProductInListDto> productDtoList = listQueryService.getHotRecmdProductListBySellerId(request.getSellerId());
        Map<String, Object> productList = new HashMap<>();
        productList.put("ProductList", productDtoList);
        return BaseResponseNetAdapter.newSuccessInstance(productList);
    }

    /**
     * 买手推荐的商品列表
     *
     * @param request
     * @return
     */
    @Override
    @POST
    @Path("/{api:(?i:api)}/{Product:(?i:Product)}/{GetSellerRecommendProductList:(?i:GetSellerRecommendProductList)}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes(MediaType.APPLICATION_JSON)
    public BaseResponseNetAdapter getSellerRecommendProductList(GetSellerRecommendProductListRequest request) {
        List<RecmdProductIdDto> productDtoList = listQueryService.getSellerRecommendProductList(request.getSellerIdList());
        Map<String, Object> productList = new HashMap<>();
        productList.put("ProductList", productDtoList);
        return BaseResponseNetAdapter.newSuccessInstance(productList);
    }

    /**
     * 取秒杀商品的活动库存量
     *
     * @param request
     * @return
     */
    @Override
    @GET
    @Path("/{api:(?i:api)}/{Product:(?i:Product)}/{GetSecKillProductActivityStock:(?i:GetSecKillProductActivityStock)}")
    @Produces({MediaType.APPLICATION_JSON})
    public BaseResponseNetAdapter getSecKillProductActivityStock(GetSecKillProductActivityStockRequest request) {
        List<SecKillProductActivityStockDto> stockDtoList = itemQueryService.getSecKillProductActivityStockList(request.getProductId(), request.getActivityId());
        Map<String, Object> stockList = new HashMap<>();
        stockList.put("StockList", stockDtoList);
        return BaseResponseNetAdapter.newSuccessInstance(stockList);
    }

    /**
     * 取商品新图文描述
     *
     * @param request
     * @return
     */
    @Override
    @GET
    @Path("/{api:(?i:api)}/{Product:(?i:Product)}/{GetProductDescExtraByProductId:(?i:GetProductDescExtraByProductId)}")
    @Produces({MediaType.APPLICATION_JSON})
    public BaseResponseNetAdapter getProductDescExtraByProductId(GetProductDescExtraByProductIdRequest request) {
        ProductDescExtraDto descExtraDto = itemQueryService.getProductDescExtra(request.getProductId());
        Map<String, Object> descExtra = new HashMap<>();
        descExtra.put("ProductDescExtra", descExtraDto);
        return BaseResponseNetAdapter.newSuccessInstance(descExtra);
    }
}
