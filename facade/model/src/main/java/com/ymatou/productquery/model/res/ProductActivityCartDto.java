package com.ymatou.productquery.model.res;

import java.util.Date;
import java.util.List;

/**
 * Created by zhangyong on 2017/4/10.
 */
/// <summary>
/// 商品活动
/// </summary>
public class ProductActivityCartDto
{
    /// <summary>
    /// 活动编号
    /// </summary>
    private int ActivityId ;

    /// <summary>
    /// 活动名称
    /// </summary>
    private String ActivityName ;

    /// <summary>
    /// 活动商品编号
    /// </summary>
    private int ProductInActivityId ;

    /// <summary>
    /// 商品在活动中的开始时间
    /// </summary>
    private Date ProductActivityStartTime ;

    /// <summary>
    /// 商品在活动中的结束时间
    /// </summary>
    private Date ProductActivityEndTime ;

    /// <summary>
    /// 活动的限购数量
    /// </summary>
    private int ActivityLimitNumber ;

    /// <summary>
    /// 活动商品的限购数量
    /// </summary>
    private int ProductActivityLimitNumber ;

    /// <summary>
    /// 促销类型
    /// </summary>
    private int PromotionType ;

    /// <summary>
    /// 活动规格列表
    /// </summary>
    private List<String> ActivityCatalogList ;

    /// <summary>
    /// 是否仅限码头新人
    /// </summary>
    private boolean IsNewBuyer ;
}
