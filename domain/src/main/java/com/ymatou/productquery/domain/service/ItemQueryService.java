package com.ymatou.productquery.domain.service;

import com.ymatou.productquery.domain.repo.mongorepo.ProductRepository;
import com.ymatou.productquery.domain.repo.mongorepo.ProductTimeStampRepository;
import com.ymatou.productquery.infrastructure.config.props.BizProps;
import com.ymatou.productquery.model.res.ProductDetailDto;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 单品查询相关
 * Created by zhangyong on 2017/4/10.
 */
public class ItemQueryService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductTimeStampRepository productTimeStampRepository;

    @Autowired
    BizProps bizProps;

    /**
     * 取复杂结构商品列表
     *
     * @param productId
     * @param nextActivityExpire
     * @param tradeIsolation
     * @return
     */
    public ProductDetailDto GetProductDetailInfo(String productId, int nextActivityExpire, boolean tradeIsolation) {
        //从缓存中获取商品信息
        if(bizProps.isUseCache()){

        }else{

        }
        //从缓存中获取规格信息
        //从缓存中获取活动商品信息
        //前置条件检查
        //组装数据
        return null;
    }
}
