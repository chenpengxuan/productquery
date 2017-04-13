package com.ymatou.productquery.test.domain;

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
        productids.add("4b37659f-fb97-4a81-8ba6-a6f884a2caa0");
        productids.add("2baf8ca7-34e3-405b-ae54-7cad82430554");
        productids.add("c69abee5-2beb-4e73-b2ea-5236179319de");
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
