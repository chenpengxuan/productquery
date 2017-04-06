package com.ymatou.productquery.domain.model;

/**
 * Created by zhangyong on 2017/4/6.
 */
public class PropertyInfo {
    private	String name	;//	  属性名称
    private	String value	;//	  属性值
    private	String pic	;//	  属性图

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

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
