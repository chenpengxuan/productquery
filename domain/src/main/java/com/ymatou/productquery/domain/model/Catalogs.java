package com.ymatou.productquery.domain.model;

import java.util.List;

/**
 * Created by zhangyong on 2017/4/6.
 */
public class Catalogs {
    private	String	spid	;//	商品编号
    private	String	cid	;//	规格编号
    private	int	sid	;//	买手编号
    private	String	sku	;//	sku
    private	boolean	presale	;//	是否预售规格
    private	Double	earnest	;//	定金
    private	Double	price	;//	原价
    private	Double	newp	;//	新客价
    private	Double	vip	;//	VIP价
    private	int	stock	;//	规格库存
    private List<PropertyInfo> props	;//	规格属性列表

    public String getSpid() {
        return spid;
    }

    public void setSpid(String spid) {
        this.spid = spid;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public boolean isPresale() {
        return presale;
    }

    public void setPresale(boolean presale) {
        this.presale = presale;
    }

    public Double getEarnest() {
        return earnest;
    }

    public void setEarnest(Double earnest) {
        this.earnest = earnest;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getNewp() {
        return newp;
    }

    public void setNewp(Double newp) {
        this.newp = newp;
    }

    public Double getVip() {
        return vip;
    }

    public void setVip(Double vip) {
        this.vip = vip;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public List<PropertyInfo> getProps() {
        return props;
    }

    public void setProps(List<PropertyInfo> props) {
        this.props = props;
    }
}
