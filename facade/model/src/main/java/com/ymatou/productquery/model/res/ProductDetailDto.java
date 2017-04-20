package com.ymatou.productquery.model.res;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;

/**
 * Created by zhangyong on 2017/4/20.
 */
public class ProductDetailDto {

    // 商品编号（int）
    @JsonProperty("ProdId")
    private int prodId;

    // 商品编号
    @JsonProperty("ProductId")
    private String productId;

    // 快照版本
    @JsonProperty("Version")
    private String version;

    // 商品名称
    @JsonProperty("Title")
    private String title;

    // 简介
    @JsonProperty("Introduction")
    private String introduction;

    // 介绍图片列表
    @JsonProperty("PicList")
    private List<String> picList;

    // 有效开始时间（年月日时分秒）
    @JsonProperty("ValidStart")
    private Date validStart;

    // 有效结束时间（年月日时分秒）
    @JsonProperty("ValidEnd")
    private Date validEnd;

    // 限购数量(0 为不限购)
    @JsonProperty("LimitNum")
    private int limitNum;

    // 限购开始时间
    @JsonProperty("LimitNumStartTime")
    private Date limitNumStartTime;

    // 卖家用户编号
    @JsonProperty("SellerId")
    private int sellerId;

    // 卖家用户账号
    @JsonProperty("SellerName")
    private String sellerName;

    // 商品品牌编号
    @JsonProperty("BrandId")
    private int brandId;


    // 商品品牌名称（中）
    @JsonProperty("BrandName")
    private String brandName;


    // 商品品牌名称（英）
    @JsonProperty("BrandEnName")
    private String brandEnName;


    // 一级分类编号
    @JsonProperty("MasterCategoryId")
    private int masterCategoryId;


    // 一级分类名称
    @JsonProperty("MasterCategoryName")
    private String masterCategoryName;


    // 二级分类编号
    @JsonProperty("CategoryId")
    private int categoryId;


    // 二级分类名称
    @JsonProperty("CategoryName")
    private String categoryName;


    // 三级分类编号
    @JsonProperty("ThirdCategoryId")
    private int thirdCategoryId;


    // 三级分类名称
    @JsonProperty("ThirdCategoryName")
    private String thirdCategoryName;


    // 备货状态（0-现货，1-代购，2-FBX商品）
    @JsonProperty("CatalogType")
    private int catalogType;


    // 发货类型（1.国内、2.海外直邮、3.贝海直邮、4.商家保税、5.贝海保税、6.海外认证直邮、7.海外拼邮）
    @JsonProperty("DeliveryMethod")
    private int deliveryMethod;


    // 保税区（1.广州，2.宁波，3.杭州，4.郑州，5.天津，6.重庆，7.深圳，8.上海）
    @JsonProperty("BondedArea")
    private int bondedArea;


    // 重量
    @JsonProperty("Weight")
    private double weight;


    // 交税方（0. 卖家交税，1. 买家交税）
    @JsonProperty("TariffType")
    private int tariffType;


    // 是否包邮
    @JsonProperty("IsFreeShipping")
    private boolean isFreeShipping;


    // 商品所属国家
    @JsonProperty("CountryId")
    private int countryId;


    // 商品创建时间
    @JsonProperty("AddTime")
    private Date addTime;


    // 售卖状态
    @JsonProperty("Status")
    private ProductStatusEnum status;


    // 是否新结构化描述
    @JsonProperty("HasTextDescription")
    private boolean hasTextDescription;


    // 本土退货 1，官方本土退货 2
    @JsonProperty("LocalReturn")
    private int localReturn;


    // 砍单风险
    @JsonProperty("NoticeRisk")
    private boolean noticeRisk;


    // FBX商品的商品备案号
    @JsonProperty("ProductCode")
    private String productCode;


    // 七天无理由
    @JsonProperty("NoReasonReturn")
    private boolean noReasonReturn;


    // 是否新品
    @JsonProperty("IsNewProduct")
    private boolean isNewProduct;


    // 新品开始时间
    @JsonProperty("NewStartTime")
    private Date newStartTime;


    // 新品结束时间
    @JsonProperty("NewEndTime")
    private Date newEndTime;


    // 是否买手热推
    @JsonProperty("IsHotRecmd")
    private boolean isHotRecmd;


    // 是否PSP商品
    @JsonProperty("IsPspProduct")
    private boolean isPspProduct;


    // 商品等级
    @JsonProperty("Grade")
    private String grade;


    // 尺码对照表
    @JsonProperty("SizePicList")
    private List<String> sizePicList;


    // 商品规格
    @JsonProperty("CatalogList")
    private List<CatalogDto> catalogList;


    // 活动商品
    @JsonProperty("ProductActivity")
    private ProductActivityDto productActivity;


    // 下一场活动
    @JsonProperty("NextActivity")
    private ProductActivityDto nextActivity;


    // 直播商品
    @JsonProperty("LiveProduct")
    private ProductLiveDto liveProduct;
}
