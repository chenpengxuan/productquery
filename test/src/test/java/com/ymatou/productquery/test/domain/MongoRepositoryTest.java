package com.ymatou.productquery.test.domain;

import com.ymatou.productquery.domain.model.Products;
import com.ymatou.productquery.domain.repo.mongorepo.ProductRepository;
import com.ymatou.productquery.web.ProductQueryApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangyong on 2017/4/7.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ProductQueryApplication.class)// 指定我们SpringBoot工程的Application启动类
public class MongoRepositoryTest {

    @Resource
    private ProductRepository productRepository;

    @Test
    public void testGetProductsByProductIds() {
        List<String> productids = new ArrayList<>();
        productids.add("4b37659f-fb97-4a81-8ba6-a6f884a2caa0");
        productids.add("2baf8ca7-34e3-405b-ae54-7cad82430554");
        productids.add("c69abee5-2beb-4e73-b2ea-5236179319de");
        List<Products> pds = productRepository.getProductsByProductIds(productids);
    }
}
