package com.ymatou.productquery.test.facade;

import com.ymatou.productquery.facade.ProductQueryFacade;
import com.ymatou.productquery.model.req.GetProductDetailListByProductIdListRequest;
import com.ymatou.productquery.model.req.GetProductInfoByProductIdRequest;
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
public class GetProductInfoByProductIdTest {
    @Autowired
    private ProductQueryFacade productQueryFacade;

    @Test
    public void testWithoutActivityOrLive() {
        String productid = "c1ba2ba5-ee5b-4139-8731-99127715ffb0";
        GetProductInfoByProductIdRequest request = new GetProductInfoByProductIdRequest();
        request.setProductId(productid);
        BaseResponseNetAdapter response = productQueryFacade.getProductInfoByProductId(request);
        assertEquals(200, response.getCode());
    }
}
