package com.ymatou.productquery.model.res;

import java.util.Date;
import java.util.List;

/**
 * Created by zhangyong on 2017/4/10.
 */
public class ProductInCartDto {
    /// <summary>
    /// 商品编号
    /// </summary>
    private String ProductId;

    /// <summary>
    /// 快照版本
    /// </summary>
    private String Version;

    /// <summary>
    /// 规格编号
    /// </summary>
    private String CatalogId;

    /// <summary>
    /// 商品名称
    /// </summary>
    private String ProductName;

    /// <summary>
    /// 商品主要图片
    /// </summary>
    private String MainPicUrl;

    /// <summary>
    /// 有效开始时间（年月日时分秒）
    /// </summary>

    private Date ValidStart;

    /// <summary>
    /// 有效结束时间（年月日时分秒）
    /// </summary>
    private Date ValidEnd;

    /// <summary>
    /// 品牌编号
    /// </summary>
    private int BrandId;

    /// <summary>
    /// 一级分类
    /// </summary>
    private int MasterCategoryId;

    /// <summary>
    /// 二级分类
    /// </summary>
    private int CategoryId;

    /// <summary>
    /// 三级分类
    /// </summary>
    private int ThirdCategoryId;

    /// <summary>
    /// 买手用户编号
    /// </summary>
    private int SellerId;

    /// <summary>
    /// 买手用户账号
    /// </summary>
    private String SellerName;

    /// <summary>
    /// 发货类型
    /// 1.国内
    /// 2.海外直邮
    /// 3.贝海直邮
    /// 4.商家保税
    /// 5.贝海保税
    /// 6.海外认证直邮
    /// 7.海外拼邮
    /// </summary>
    private int DeliveryMethod;

    /// <summary>
    /// 保税区
    /// 1.广州
    /// 2.宁波
    /// 3.杭州
    /// 4.郑州
    /// 5.天津
    /// 6.重庆
    /// 7.深圳
    /// 8.上海
    /// </summary>
    private int BondedArea;

    /// <summary>
    /// 重量
    /// </summary>
    private Double Weight;

    /// <summary>
    /// 是否包邮商品
    /// </summary>
    private boolean IsFreeShipping;

    /// <summary>
    /// 交税方
    /// 0. 卖家交税
    /// 1. 买家交税
    /// </summary>
    private int TariffType;

    /// <summary>
    /// 状态
    /// -1. 已删除
    ///  0. 有效
    ///  2. 无效
    /// </summary>
    private ProductStatusEnum Status;

    /// <summary>
    /// 商品的限购数量(0 为不限制购买数量)
    /// </summary>
    private int LimitNumber;

    /// <summary>
    /// 限购的起始时间
    /// </summary>
    private Date LimitStartTime;

    /// <summary>
    /// 库存数量（活动中为活动库存）
    /// </summary>
    private int StockNum;

    /// <summary>
    /// 商品备案号（杭保商品）
    /// </summary>
    private String ProductCode;

    /// <summary>
    /// 本土退货
    /// </summary>
    private int LocalReturn;

    /// <summary>
    /// 规格的sku 编号
    /// </summary>
    private String SKU;

    /// <summary>
    /// 备货类型（2- FBX商品）
    /// </summary>
    private int CatalogType;

    /// <summary>
    /// 是否支持7天无理由退货
    /// </summary>
    private boolean IsNoReasonReturn;

    /// <summary>
    /// 商品下有效规格总数
    /// </summary>
    private int CatalogCount;

    /// <summary>
    /// 预订商品
    /// </summary>
    private boolean IsPreSale;

    /// <summary>
    /// 规格属性列表
    /// </summary>
    private List<PropertyDto> Properties;

    /// <summary>
    /// 规格价格
    /// </summary>
    private Double Price;

    /// <summary>
    /// 是否是PSP商品
    /// </summary>
    private boolean IsPspProduct;

    /// <summary>
    /// 商品关联直播
    /// </summary>
    private LiveProductCartDto LiveProduct;

    /// <summary>
    /// 商品活动
    /// </summary>
    private ProductActivityCartDto ProductActivity;

}
