package com.ymatou.productquery.test.facade;

import com.ymatou.productquery.facade.ProductQueryFacade;
import com.ymatou.productquery.model.req.GetProductListByProductIdListRequest;
import com.ymatou.productquery.model.res.BaseResponseNetAdapter;
import com.ymatou.productquery.web.ProductQueryApplication;
import org.apache.http.util.Asserts;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by zhujinfeng on 2017/5/3.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ProductQueryApplication.class)
public class GetProductListByProductIdListTest {
    @Autowired
    private ProductQueryFacade productQueryFacade;

    /**
     * 测试获取在售中商品简化列表，无活动无直播。
     */
    @Test
    public void testOnSaleProductListWithoutActivityAndLive()
    {
        List<String> productIdList = new ArrayList();
        productIdList.add("88fb7ab8-eb2c-48c7-836a-e9912d343fcb");
        productIdList.add("8fd566c0-1b5f-48d1-8ac4-f4ee07a4d942");

        GetProductListByProductIdListRequest request = new GetProductListByProductIdListRequest();
        request.setProductIdList(productIdList);
        BaseResponseNetAdapter response = productQueryFacade.getProductListByProductIdList(request);
        assertEquals(200, response.getCode());
        Asserts.notNull(response.getData(), "ProductList");
    }
}
