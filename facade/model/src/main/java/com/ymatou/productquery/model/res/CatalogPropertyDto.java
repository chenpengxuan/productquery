package com.ymatou.productquery.model.res;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by zhangyong on 2017/4/20.
 */
public class CatalogPropertyDto {

    // 属性名称
    @JsonProperty("Name")
    private String name;

    // 属性值
    @JsonProperty("Value")
    private String value;

    // 规格图片
    @JsonProperty("PicUrl")
    private String picUrl;

    // 排序
    @JsonProperty("Sort")
    private int sort;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }
}
