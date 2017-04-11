package com.ymatou.productquery.facade;

import com.alibaba.dubbo.config.annotation.Service;
import com.ymatou.productquery.model.req.GetCatalogListByCatalogIdListRequest;
import com.ymatou.productquery.model.res.BaseResponseNetAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by zhangyong on 2017/4/10.
 */
@Service(protocol = {"rest", "dubbo"})
@Component
@Path("")
public class ProductQueryFacadeImpl implements ProductQueryFacade {

    @Override
    @GET
    @Path("/{api:(?i:api)}/{Price:(?i:Price)}/{GetPriceByProdId:(?i:GetPriceByProdId)}")
    @Produces({MediaType.APPLICATION_JSON})
    public BaseResponseNetAdapter getCatalogListByCatalogIdList(GetCatalogListByCatalogIdListRequest request) {
        return null;
    }
}
