package com.ymatou.productquery.model.req;

import com.ymatou.productquery.model.PrintFriendliness;

/**
 * Created by zhangyong on 2017/5/3.
 */
public class CatalogDeliveryDto extends PrintFriendliness {
    /// <summary>
    /// 规格编号
    /// </summary>
    private String CatalogId;

    /// <summary>
    /// 多物流类型
    /// </summary>
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
