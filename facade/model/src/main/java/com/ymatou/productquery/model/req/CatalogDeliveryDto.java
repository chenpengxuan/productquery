package com.ymatou.productquery.model.req;

import com.ymatou.productquery.model.PrintFriendliness;

/**
 * Created by zhangyong on 2017/5/3.
 */
public class CatalogDeliveryDto extends PrintFriendliness {
    /**
     *  规格编号
     */
    private String CatalogId;

    /**
     * 多物流类型
     */
    private int DeliveryType;

    public String getCatalogId() {
        return CatalogId;
    }

    public void setCatalogId(String catalogId) {
        CatalogId = catalogId;
    }

    public int getDeliveryType() {
        return DeliveryType;
    }

    public void setDeliveryType(int deliveryType) {
        DeliveryType = deliveryType;
    }
}
