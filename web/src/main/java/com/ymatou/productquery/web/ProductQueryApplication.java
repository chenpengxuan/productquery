package com.ymatou.productquery.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.dao.PersistenceExceptionTranslationAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.JpaBaseConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * Created by zhangyong on 2017/4/7.
 */
@Configuration
@EnableAutoConfiguration
        (exclude = {DataSourceAutoConfiguration.class
                , DataSourceTransactionManagerAutoConfiguration.class
                , JpaBaseConfiguration.class, HibernateJpaAutoConfiguration.class, PersistenceExceptionTranslationAutoConfiguration.class
                , MongoAutoConfiguration.class, MongoDataAutoConfiguration.class
        })
//@MapperScan(basePackages = {"com.ymatou.productquery.domain.sqlrepo"})
@ImportResource({"classpath:datasource-disconf.xml"})
@ComponentScan("com.ymatou")
public class ProductQueryApplication {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(com.ymatou.productquery.web.ProductQueryApplication.class);
        app.run(args);
    }
}
