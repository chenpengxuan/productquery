package com.ymatou.productquery.facade;

import com.alibaba.dubbo.config.annotation.Service;
import com.ymatou.productquery.domain.service.ListQueryService;
import com.ymatou.productquery.model.req.GetCatalogListByCatalogIdListRequest;
import com.ymatou.productquery.model.res.BaseResponseNetAdapter;
import com.ymatou.productquery.model.res.ProductInCartDto;
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

    @Override
    @POST
    @Path("/{api:(?i:api)}/{Product:(?i:Product)}/{GetCatalogListByCatalogIdList:(?i:GetCatalogListByCatalogIdList)}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes(MediaType.APPLICATION_JSON)
    public BaseResponseNetAdapter getCatalogListByCatalogIdList(GetCatalogListByCatalogIdListRequest request) {
//        List<ProductInCartDto> result = listQueryService.getProductListFromShoppingCart(request.getCatalogIdList(), false);
//
//        Map<String, Object> productList = new HashMap<>();
//        productList.put("ProductList", result);
//        return BaseResponseNetAdapter.newSuccessInstance(productList);
        return null;
    }
}
