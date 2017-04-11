package com.ymatou.productquery.model.res;

import java.util.Date;

/**
 * Created by zhangyong on 2017/4/10.
 */
/// <summary>
/// 直播商品
/// </summary>
public class LiveProductCartDto
{
    /// <summary>
    /// 直播编号
    /// </summary>
    private int LiveId ;

    /// <summary>
    /// 直播名称
    /// </summary>
    private String LiveName ;

    /// <summary>
    /// 直播开始时间
    /// </summary>
    private Date StartTime ;

    /// <summary>
    /// 直播结束时间
    /// </summary>
    private Date EndTime ;

    /// <summary>
    /// 直播商品售卖状态
    /// </summary>
    private int SellStatus ;
}
