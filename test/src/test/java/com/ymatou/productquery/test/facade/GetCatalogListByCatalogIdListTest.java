package com.ymatou.productquery.test.facade;

import com.ymatou.productquery.facade.ProductQueryFacade;
import com.ymatou.productquery.web.ProductQueryApplication;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by zhangyong on 2017/4/13.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ProductQueryApplication.class)// 指定我们SpringBoot工程的Application启动类
public class GetCatalogListByCatalogIdListTest {
    @Autowired
    private ProductQueryFacade productQueryFacade;

//    @Test
//    public void testWithoutActivityOrLive() {
//        List<String> catalogs = new ArrayList<>();
//        catalogs.add("e5f78f4a-4f45-4bf1-8662-65eb8f8ccbd9");
//        catalogs.add("58aa4923-c05f-45f4-b9ee-dfb409eb709c");
//        catalogs.add("673db9d9-1e5e-447e-b7df-935bf7d4c47a");
//
//        GetCatalogListByCatalogIdListRequest request = new GetCatalogListByCatalogIdListRequest();
//        request.setCatalogIdList(catalogs);
//        BaseResponseNetAdapter response = productQueryFacade.getCatalogListByCatalogIdList(request);
//        assertEquals(200, response.getCode());
//    }
//
//    @Test
//    public void testWithActivity() {
//        List<String> catalogs = new ArrayList<>();
//        catalogs.add("1996c44c-c305-4682-bce4-0bb4bd9b9149");
//        catalogs.add("9c24c428-3cab-45eb-9b6a-2dc914914097");
//        catalogs.add("f5f2729d-0a77-4430-b82e-bbb02c6b7d71");
//
//        GetCatalogListByCatalogIdListRequest request = new GetCatalogListByCatalogIdListRequest();
//        request.setCatalogIdList(catalogs);
//        BaseResponseNetAdapter response = productQueryFacade.getCatalogListByCatalogIdList(request);
//        assertEquals(200, response.getCode());
//    }
}
