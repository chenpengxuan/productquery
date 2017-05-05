package com.ymatou.productquery.test.facade;

import com.ymatou.productquery.facade.ProductQueryFacade;
import com.ymatou.productquery.model.req.GetProductDetailListByProductIdListRequest;
import com.ymatou.productquery.model.req.GetProductInfoByProductIdRequest;
import com.ymatou.productquery.model.res.BaseResponseNetAdapter;
import com.ymatou.productquery.model.res.ProductDetailDto;
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
 * Created by zhangyong on 2017/5/2.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ProductQueryApplication.class)// 指定我们SpringBoot工程的Application启动类
/**
 * 商品详情接口测试
 */
public class GetProductInfoByProductIdTest {
    @Autowired
    private ProductQueryFacade productQueryFacade;

    @Test
    public void testWithoutActivityOrLive() {
        String productId = "c1ba2ba5-ee5b-4139-8731-99127715ffb0";
        GetProductInfoByProductIdRequest request = new GetProductInfoByProductIdRequest();
        request.setProductId(productId);
        BaseResponseNetAdapter response = productQueryFacade.getProductInfoByProductId(request);
        assertEquals(200, response.getCode());
        assertEquals(((ProductDetailDto) ((Map) response.getData()).get("Product")).getProductId(), productId);
    }

    @Test
    public void testWithActivityAndLive() {
        String productId = "d70e9919-af08-4dee-8512-ad92dec423f3";
        GetProductInfoByProductIdRequest request = new GetProductInfoByProductIdRequest();
        request.setProductId(productId);
        BaseResponseNetAdapter response = productQueryFacade.getProductInfoByProductId(request);
        assertEquals(200, response.getCode());
        assertEquals(((ProductDetailDto) ((Map) response.getData()).get("Product")).getProductId(), productId);
    }
}
