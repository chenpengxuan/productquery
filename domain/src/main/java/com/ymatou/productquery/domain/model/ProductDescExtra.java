package com.ymatou.productquery.domain.model;

import java.util.List;

/**
 * Created by zhangyong on 2017/4/6.
 */
public class ProductDescExtra {
    private	String	spid	;//	商品编号
    private	String	desc	;//	图文描述 - 文字部分
    private	List<String>	descpics	;//	图文描述 - 图片部分
    private	List<String>	sizepics	;//	尺码表
    private	String	notice	;//	买家须知文本
    private	List<String>	notipics	;//	买家须知图片列表
    private	String	intro	;//	买手介绍文本
    private	List<String>	intropics	;//	买家须知图片列表
    private List<ProductDescPropertyInfo> props	;//	商品属性列表

    public String getSpid() {
        return spid;
    }

    public void setSpid(String spid) {
        this.spid = spid;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<String> getDescpics() {
        return descpics;
    }

    public void setDescpics(List<String> descpics) {
        this.descpics = descpics;
    }

    public List<String> getSizepics() {
        return sizepics;
    }

    public void setSizepics(List<String> sizepics) {
        this.sizepics = sizepics;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public List<String> getNotipics() {
        return notipics;
    }

    public void setNotipics(List<String> notipics) {
        this.notipics = notipics;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public List<String> getIntropics() {
        return intropics;
    }

    public void setIntropics(List<String> intropics) {
        this.intropics = intropics;
    }

    public List<ProductDescPropertyInfo> getProps() {
        return props;
    }

    public void setProps(List<ProductDescPropertyInfo> props) {
        this.props = props;
    }
}
