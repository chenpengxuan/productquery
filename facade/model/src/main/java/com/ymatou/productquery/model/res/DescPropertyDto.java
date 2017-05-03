package com.ymatou.productquery.model.res;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by zhujinfeng on 2017/5/2.
 */
public class DescPropertyDto {
    @JsonProperty("Key")
    private String key;

    @JsonProperty("Value")
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
