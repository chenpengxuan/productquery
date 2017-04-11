package com.ymatou.productquery.test;

import com.ymatou.productquery.domain.model.ActivityProducts;
import com.ymatou.productquery.domain.model.ProductDetailModel;
import com.ymatou.productquery.domain.repo.Repository;
import com.ymatou.productquery.web.ProductQueryApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangyong on 2017/4/7.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ProductQueryApplication.class)// 指定我们SpringBoot工程的Application启动类
public class MongoRepositoryTest {

    @Resource(name = "mongoRepository")
    private Repository mongoRepository;

    @Test
    public void testGetActivityProductList() {
        List<String> productids = new ArrayList<>();
        productids.add("72e06e05-028e-468f-bb00-bccb85654aae");
        productids.add("fae31b4f-ec1d-40f2-86f6-fb7b4ccf93d2");
        productids.add("a3c5350d-50df-46fe-aac8-a7d2bf06cd1a");
        List<ActivityProducts> aps = mongoRepository.getActivityProductList(productids);
    }

    @Test
    public void testGetHistoryProductListByProductIdList() {
        List<String> productids = new ArrayList<>();
        productids.add("000005c0-8798-4d50-a713-13cbee9c5b5b");
        productids.add("0000129c-2fac-42ea-8707-4c7acecb34ba");
        productids.add("000012ba-2db5-4f1d-a9a3-ab28d1a8fc41");
        List<ProductDetailModel> pds = mongoRepository.getHistoryProductListByProductIdList(productids);
    }
}
