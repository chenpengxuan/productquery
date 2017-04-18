package com.ymatou.productquery.domain.model;

import java.util.Date;

/**
 * Created by zhangyong on 2017/4/6.
 */
public class LiveProducts {
    private	String spid;
    private int lid;
    private int sid;
    private Date start;
    private Date end;
    private Date add;
    private String brand;
    private String ebrand;
    private int bid;
    private int mcatid;
    private String mcatname;
    private int scatid;
    private String scatname;
    private int tcatid;
    private String tcatname;
    private int status;
    private boolean istop;
    private int sort;

    public String getSpid() {
        return spid;
    }

    public void setSpid(String spid) {
        this.spid = spid;
    }

    public int getLid() {
        return lid;
    }

    public void setLid(int lid) {
        this.lid = lid;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Date getAdd() {
        return add;
    }

    public void setAdd(Date add) {
        this.add = add;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getEbrand() {
        return ebrand;
    }

    public void setEbrand(String ebrand) {
        this.ebrand = ebrand;
    }

    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    public int getMcatid() {
        return mcatid;
    }

    public void setMcatid(int mcatid) {
        this.mcatid = mcatid;
    }

    public String getMcatname() {
        return mcatname;
    }

    public void setMcatname(String mcatname) {
        this.mcatname = mcatname;
    }

    public int getScatid() {
        return scatid;
    }

    public void setScatid(int scatid) {
        this.scatid = scatid;
    }

    public String getScatname() {
        return scatname;
    }

    public void setScatname(String scatname) {
        this.scatname = scatname;
    }

    public int getTcatid() {
        return tcatid;
    }

    public void setTcatid(int tcatid) {
        this.tcatid = tcatid;
    }

    public String getTcatname() {
        return tcatname;
    }

    public void setTcatname(String tcatname) {
        this.tcatname = tcatname;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean istop() {
        return istop;
    }

    public void setIstop(boolean istop) {
        this.istop = istop;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }
}
