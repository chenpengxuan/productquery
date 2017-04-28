package com.ymatou.productquery.facade;

import com.alibaba.dubbo.config.annotation.Service;
import com.ymatou.productquery.domain.service.ListQueryService;
import com.ymatou.productquery.domain.service.ProductInListService;
import com.ymatou.productquery.model.req.*;
import com.ymatou.productquery.model.res.BaseResponseNetAdapter;
import com.ymatou.productquery.model.res.ProductHistoryDto;
import com.ymatou.productquery.model.res.ProductInCartDto;
import com.ymatou.productquery.model.res.ProductInListDto;
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
    private ProductInListService productInListService;

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
        return null;
    }

    /**
     * 根据商品id获取商品详情
     * @param request
     * @return
     */
    /**
     * 商品明细列表（交易隔离）
     *
     * @param request
     * @return
     */
    public BaseResponseNetAdapter GetProductDetailListByTradeIsolation(GetProductDetailListByTradeIsolationRequest request) {
        return null;
    }

    @Override
    @GET
    @Path("/{api:(?i:api)}/{Product:(?i:Product)}/{GetProductInfoByProductId:(?i:GetProductInfoByProductId)}")
    @Produces({MediaType.APPLICATION_JSON})
    public BaseResponseNetAdapter getProductDetailByProductId(@BeanParam GetProductInfoByProductIdRequest request) {
        return null;
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
    public BaseResponseNetAdapter GetProductListByTradeIsolation(GetProductListByTradeIsolationRequest request) {
        List<ProductInListDto> productDtoList = productInListService.getProductList(request.getProductIdList(), true);
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
    public BaseResponseNetAdapter GetProductListByProductIdList(GetProductListByProductIdListRequest request) {
        List<ProductInListDto> productDtoList = productInListService.getProductList(request.getProductIdList(), false);
        Map<String, Object> productList = new HashMap<>();
        productList.put("ProductList", productDtoList);
        return BaseResponseNetAdapter.newSuccessInstance(productList);
    }
}
