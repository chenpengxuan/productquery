package com.ymatou.productquery.model.res;

import com.fasterxml.jackson.annotation.JsonProperty;

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
    @JsonProperty("PropertyName")
    private String propertyName;

    /// <summary>
    /// 属性值
    /// </summary>
    @JsonProperty("PropertyValue")
    private String propertyValue;

    /// <summary>
    /// 规格图片
    /// </summary>
    @JsonProperty("PropertyPictureUrl")
    private String propertyPictureUrl;

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyValue() {
        return propertyValue;
    }

    public void setPropertyValue(String propertyValue) {
        this.propertyValue = propertyValue;
    }

    public String getPropertyPictureUrl() {
        return propertyPictureUrl;
    }

    public void setPropertyPictureUrl(String propertyPictureUrl) {
        this.propertyPictureUrl = propertyPictureUrl;
    }
}
