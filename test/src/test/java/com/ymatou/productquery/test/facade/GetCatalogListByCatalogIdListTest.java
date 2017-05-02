package com.ymatou.productquery.test.facade;

import com.ymatou.productquery.facade.ProductQueryFacade;
import com.ymatou.productquery.model.req.GetCatalogListByCatalogIdListRequest;
import com.ymatou.productquery.model.req.GetCatalogListByTradeIsolationRequest;
import com.ymatou.productquery.model.res.BaseResponseNetAdapter;
import com.ymatou.productquery.web.ProductQueryApplication;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by zhangyong on 2017/4/13.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ProductQueryApplication.class)// 指定我们SpringBoot工程的Application启动类
public class GetCatalogListByCatalogIdListTest {
    @Autowired
    private ProductQueryFacade productQueryFacade;

    @Test
    public void testWithoutActivityOrLive() {
        List<String> catalogs = new ArrayList<>();
        catalogs.add("41e135e7-536c-46b2-9f89-8ce199f82517");
//        catalogs.add("58aa4923-c05f-45f4-b9ee-dfb409eb709c");
//        catalogs.add("673db9d9-1e5e-447e-b7df-935bf7d4c47a");


        GetCatalogListByCatalogIdListRequest request = new GetCatalogListByCatalogIdListRequest();
        request.setCatalogIdList(catalogs);
        BaseResponseNetAdapter response = productQueryFacade.getCatalogListByCatalogIdList(request);
        assertEquals(200, response.getCode());
    }

    @Test
    public void testWithActivity() {
        List<String> catalogs = new ArrayList<>();
        catalogs.add("c2bade23-9557-4fd1-9c3e-603ea0739f7a");
        catalogs.add("f1b461f9-b2be-465b-8ece-ee490a8751e6");
        catalogs.add("4250cdbb-bc8e-4379-b0e2-8165fa4e5133");

        GetCatalogListByCatalogIdListRequest request = new GetCatalogListByCatalogIdListRequest();
        request.setCatalogIdList(catalogs);
        BaseResponseNetAdapter response = productQueryFacade.getCatalogListByCatalogIdList(request);
        assertEquals(200, response.getCode());
    }

    @Test
    public void testWithLive() {
        List<String> catalogs = new ArrayList<>();
        catalogs.add("dc03f90e-1fba-4525-948e-93afd1eced63");
        catalogs.add("7d5aba06-e200-4023-b21c-d421f956e77c");
        catalogs.add("c8b7ac3e-098b-4ecd-871e-a002daacff47");

        GetCatalogListByCatalogIdListRequest request = new GetCatalogListByCatalogIdListRequest();
        request.setCatalogIdList(catalogs);
        BaseResponseNetAdapter response = productQueryFacade.getCatalogListByCatalogIdList(request);
        assertEquals(200, response.getCode());
    }

    @Test
    void testWithoutActivityOrLiveTradeIsolation() {
        List<String> catalogs = new ArrayList<>();
        catalogs.add("dc03f90e-1fba-4525-948e-93afd1eced63");
        catalogs.add("7d5aba06-e200-4023-b21c-d421f956e77c");
        catalogs.add("c8b7ac3e-098b-4ecd-871e-a002daacff47");
        GetCatalogListByTradeIsolationRequest request = new GetCatalogListByTradeIsolationRequest();
        request.setCatalogIdList(catalogs);
        BaseResponseNetAdapter response = productQueryFacade.getCatalogListByTradeIsolation(request);
    }
}
