package com.ymatou.productquery.test.facade;

import com.ymatou.productquery.facade.ProductQueryFacade;
import com.ymatou.productquery.model.req.GetTopProductListByLiveIdRequest;
import com.ymatou.productquery.model.res.BaseResponseNetAdapter;
import com.ymatou.productquery.web.ProductQueryApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import static org.junit.Assert.assertEquals;

/**
 * Created by zhangyong on 2017/5/3.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ProductQueryApplication.class)
public class GetTopProductListByLiveIdTest {

    @Autowired
    private ProductQueryFacade productQueryFacade;

    @Test
    public void testWithoutTopProduct() {
        GetTopProductListByLiveIdRequest request = new GetTopProductListByLiveIdRequest();
        request.setLiveId(157293);
        BaseResponseNetAdapter response = productQueryFacade.getTopProductListByLiveId(request);

        assertEquals(200, response.getCode());
    }

    @Test
    public void  testWithTopProduct()
    {
        GetTopProductListByLiveIdRequest request = new GetTopProductListByLiveIdRequest();
        request.setLiveId(157815);
        BaseResponseNetAdapter response = productQueryFacade.getTopProductListByLiveId(request);
        assertEquals(200, response.getCode());
    }
}
