package com.ymatou.productquery.model.res;

/**
 * Created by zhangyong on 2017/4/10.
 */
/// <summary>
/// 商品规格属性
/// </summary>
public class PropertyDto
{
    /// <summary>
    /// 属性名称
    /// </summary>
    private String PropertyName ;

    /// <summary>
    /// 属性值
    /// </summary>
    private String PropertyValue ;

    /// <summary>
    /// 规格图片
    /// </summary>
    private String PropertyPictureUrl ;

    public String getPropertyName() {
        return PropertyName;
    }

    public void setPropertyName(String propertyName) {
        PropertyName = propertyName;
    }

    public String getPropertyValue() {
        return PropertyValue;
    }

    public void setPropertyValue(String propertyValue) {
        PropertyValue = propertyValue;
    }

    public String getPropertyPictureUrl() {
        return PropertyPictureUrl;
    }

    public void setPropertyPictureUrl(String propertyPictureUrl) {
        PropertyPictureUrl = propertyPictureUrl;
    }
}
