package com.ymatou.productquery.test.facade;

import com.ymatou.productquery.facade.ProductQueryFacade;
import com.ymatou.productquery.model.req.GetProductListByHistoryProductIdListRequest;
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
 * Created by zhangyong on 2017/4/26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ProductQueryApplication.class)// 指定我们SpringBoot工程的Application启动类
public class GetProductListByHistoryProductIdListTest {
    @Autowired
    private ProductQueryFacade productQueryFacade;

    @Test
    public void testAllHistoryProduct() {
        List<String> productids = new ArrayList<>();
        productids.add("02AF38B2-851C-4BD3-8DC7-F8A5CA0CC7EA");
        productids.add("02aeb9f1-eca4-4492-97a2-5e20a69f1468");
        productids.add("027f7fb8-17e4-4733-a4c1-549cdb70979d");
        GetProductListByHistoryProductIdListRequest request = new GetProductListByHistoryProductIdListRequest();
        request.setProductIdList(productids);
        BaseResponseNetAdapter response = productQueryFacade.getProductListByHistoryProductIdList(request);
        assertEquals(200, response.getCode());
    }
}
