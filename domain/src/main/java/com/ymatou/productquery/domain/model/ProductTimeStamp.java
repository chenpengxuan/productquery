package com.ymatou.productquery.domain.model;

import java.util.Date;

/**
 * Created by zhangyong on 2017/4/21.
 */
public class ProductTimeStamp {
    private Date spid;
    private Date cut;
    private Date sut;
    private Date aut;
    private Date lut;

    public Date getSpid() {
        return spid;
    }

    public void setSpid(Date spid) {
        this.spid = spid;
    }

    public Date getCut() {
        return cut;
    }

    public void setCut(Date cut) {
        this.cut = cut;
    }

    public Date getSut() {
        return sut;
    }

    public void setSut(Date sut) {
        this.sut = sut;
    }

    public Date getAut() {
        return aut;
    }

    public void setAut(Date aut) {
        this.aut = aut;
    }

    public Date getLut() {
        return lut;
    }

    public void setLut(Date lut) {
        this.lut = lut;
    }
}
