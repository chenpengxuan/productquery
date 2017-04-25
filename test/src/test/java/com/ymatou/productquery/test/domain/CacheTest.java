package com.ymatou.productquery.test.domain;

import com.google.common.collect.Lists;
import com.ymatou.productquery.domain.cache.Cache;
import com.ymatou.productquery.domain.model.ActivityProducts;
import com.ymatou.productquery.domain.model.ProductTimeStamp;
import com.ymatou.productquery.domain.model.Products;
import com.ymatou.productquery.domain.repo.mongorepo.ActivityProdutRepository;
import com.ymatou.productquery.domain.repo.mongorepo.ProductRepository;
import com.ymatou.productquery.domain.repo.mongorepo.ProductTimeStampRepository;
import com.ymatou.productquery.web.ProductQueryApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangyong on 2017/4/24.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ProductQueryApplication.class)// 指定我们SpringBoot工程的Application启动类
public class CacheTest {

    @Resource
    private Cache cache;

    @Autowired
    private ProductTimeStampRepository productTimeStampRepository;

    @Test
    public void testGetProductsByProductIds() {
        List<String> productids = new ArrayList<>();
        productids.add("4b37659f-fb97-4a81-8ba6-a6f884a2caa0");
        productids.add("2baf8ca7-34e3-405b-ae54-7cad82430554");
        productids.add("c69abee5-2beb-4e73-b2ea-5236179319de");

        List<ProductTimeStamp> updateStampMap = productTimeStampRepository
                .getTimeStampByProductIds(productids, "cut,sut");

        List<Products> pds = cache.getProductsByProductIds(productids, updateStampMap);
    }

    @Test
    public void testGetActivityProductList() {
        List<String> productids = new ArrayList<>();
        productids.add("35aa6d96-a402-4c2e-befd-2b2d39775651");
        productids.add("19d6c5bc-8510-4847-838a-159c598948a9");
        productids.add("19199d7b-2519-491d-b6bb-5390153c7d70");

        List<ProductTimeStamp> updateStampMap = productTimeStampRepository
                .getTimeStampByProductIds(productids, "aut");
        //List<ActivityProducts> activityProductsList = cache.getActivityProductList(productids, updateStampMap);
    }
}
