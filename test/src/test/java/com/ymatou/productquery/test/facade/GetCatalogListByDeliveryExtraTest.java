package com.ymatou.productquery.test.facade;

import com.ymatou.productquery.facade.ProductQueryFacade;
import com.ymatou.productquery.model.req.CatalogDeliveryDto;
import com.ymatou.productquery.model.req.GetCatalogListByCatalogIdListRequest;
import com.ymatou.productquery.model.req.GetCatalogListByDeliveryExtraRequest;
import com.ymatou.productquery.model.res.BaseResponseNetAdapter;
import com.ymatou.productquery.model.res.ProductInCartDto;
import com.ymatou.productquery.web.ProductQueryApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by zhangyong on 2017/5/4.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ProductQueryApplication.class)// 指定我们SpringBoot工程的Application启动类
public class GetCatalogListByDeliveryExtraTest {
    @Autowired
    private ProductQueryFacade productQueryFacade;

    @Test
    public void testNoMultiLogistics() {
        List<CatalogDeliveryDto> catalogs = new ArrayList<>();
        CatalogDeliveryDto catalogDeliveryDto1 = new CatalogDeliveryDto();
        catalogDeliveryDto1.setCatalogId("41e135e7-536c-46b2-9f89-8ce199f82517");
        catalogDeliveryDto1.setDeliveryType(3);
        catalogs.add(catalogDeliveryDto1);
        CatalogDeliveryDto catalogDeliveryDto2 = new CatalogDeliveryDto();
        catalogDeliveryDto2.setCatalogId("58aa4923-c05f-45f4-b9ee-dfb409eb709c");
        catalogDeliveryDto2.setDeliveryType(3);
        catalogs.add(catalogDeliveryDto2);

        GetCatalogListByDeliveryExtraRequest request = new GetCatalogListByDeliveryExtraRequest();
        request.setCatalogIdList(catalogs);
        BaseResponseNetAdapter response = productQueryFacade.getCatalogListByDeliveryExtra(request);
        assertEquals(200, response.getCode());
        assertEquals(((List<ProductInCartDto>) ((Map) response.getData()).get("ProductList")).size(), 2);
    }

    @Test
    public void testMultiLogistics() {
        List<CatalogDeliveryDto> catalogs = new ArrayList<>();
        CatalogDeliveryDto catalogDeliveryDto1 = new CatalogDeliveryDto();
        catalogDeliveryDto1.setCatalogId("41e135e7-536c-46b2-9f89-8ce199f82517");
        catalogDeliveryDto1.setDeliveryType(3);
        catalogs.add(catalogDeliveryDto1);
        CatalogDeliveryDto catalogDeliveryDto2 = new CatalogDeliveryDto();
        catalogDeliveryDto2.setCatalogId("58aa4923-c05f-45f4-b9ee-dfb409eb709c");
        catalogDeliveryDto2.setDeliveryType(3);
        catalogs.add(catalogDeliveryDto2);

        GetCatalogListByDeliveryExtraRequest request = new GetCatalogListByDeliveryExtraRequest();
        request.setCatalogIdList(catalogs);
        BaseResponseNetAdapter response = productQueryFacade.getCatalogListByDeliveryExtra(request);
        assertEquals(200, response.getCode());
        assertEquals(((List<ProductInCartDto>) ((Map) response.getData()).get("ProductList")).size(), 2);
    }

}
