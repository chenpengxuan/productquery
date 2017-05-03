package com.ymatou.productquery.test.facade;

import com.ymatou.productquery.facade.ProductQueryFacade;
import com.ymatou.productquery.model.req.GetProductDetailListByProductIdListRequest;
import com.ymatou.productquery.model.req.GetProductDetailListByTradeIsolationRequest;
import com.ymatou.productquery.model.res.BaseResponseNetAdapter;
import com.ymatou.productquery.web.ProductQueryApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by zhangyong on 2017/5/2.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ProductQueryApplication.class)// 指定我们SpringBoot工程的Application启动类
public class GetProductDetailListByProductIdListTest {
    @Autowired
    private ProductQueryFacade productQueryFacade;

    @Test
    public void testWithoutActivityOrLive() {
        List<String> products = new ArrayList<>();
        products.add("c1ba2ba5-ee5b-4139-8731-99127715ffb0");
        products.add("ce4fed93-0e50-4595-a8c2-5adf9d99725e");

        GetProductDetailListByProductIdListRequest request = new GetProductDetailListByProductIdListRequest();
        request.setProductIdList(products);
        BaseResponseNetAdapter response = productQueryFacade.getProductDetailListByProductIdList(request);

        assertEquals(200, response.getCode());
    }

    @Test
    public void testWithActivityAndLive() {
        List<String> products = new ArrayList<>();
        products.add("773517b4-d3bd-415e-a745-07e37a5ae398");
        products.add("ff6963d3-84b9-4efb-b767-60480de89f50");

        GetProductDetailListByProductIdListRequest request = new GetProductDetailListByProductIdListRequest();
        request.setProductIdList(products);
        BaseResponseNetAdapter response = productQueryFacade.getProductDetailListByProductIdList(request);

        assertEquals(200, response.getCode());
    }

    @Test
    public void testWithoutActivityOrLiveTradeIsolation() {
        List<String> products = new ArrayList<>();
        products.add("c1ba2ba5-ee5b-4139-8731-99127715ffb0");
        products.add("ce4fed93-0e50-4595-a8c2-5adf9d99725e");

        GetProductDetailListByTradeIsolationRequest request = new GetProductDetailListByTradeIsolationRequest();
        request.setProductIdList(products);
        BaseResponseNetAdapter response = productQueryFacade.getProductDetailListByTradeIsolation(request);

        assertEquals(200, response.getCode());
    }

    @Test
    public void testWithActivityOrLiveTradeIsolation() {
        List<String> products = new ArrayList<>();
        products.add("773517b4-d3bd-415e-a745-07e37a5ae398");
        products.add("ff6963d3-84b9-4efb-b767-60480de89f50");

        GetProductDetailListByTradeIsolationRequest request = new GetProductDetailListByTradeIsolationRequest();
        request.setProductIdList(products);
        BaseResponseNetAdapter response = productQueryFacade.getProductDetailListByTradeIsolation(request);

        assertEquals(200, response.getCode());
    }
}
