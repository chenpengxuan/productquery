package com.ymatou.productquery.domain.model;

import org.bson.types.ObjectId;

import java.util.Date;
import java.util.List;

/**
 * Created by zhangyong on 2017/4/6.
 */
public class ActivityProducts {

    private ObjectId _id;
    private String spid;
    private int aid;
    private String aname;
    private int inaid;
    private Date start;
    private Date end;
    private Double market;
    private int alimit;
    private int plimit;
    private boolean isolation;
    private boolean nbuyer;
    private boolean part;
    private List<ActivityCatalogInfo> catalogs;

    public ObjectId getId() {
        return _id;
    }

    public void setId(ObjectId _id) {
        this._id = _id;
    }

    public String getSpid() {
        return spid;
    }

    public void setSpid(String spid) {
        this.spid = spid;
    }

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public String getAname() {
        return aname;
    }

    public void setAname(String aname) {
        this.aname = aname;
    }

    public int getInaid() {
        return inaid;
    }

    public void setInaid(int inaid) {
        this.inaid = inaid;
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

    public Double getMarket() {
        return market;
    }

    public void setMarket(Double market) {
        this.market = market;
    }

    public int getAlimit() {
        return alimit;
    }

    public void setAlimit(int alimit) {
        this.alimit = alimit;
    }

    public int getPlimit() {
        return plimit;
    }

    public void setPlimit(int plimit) {
        this.plimit = plimit;
    }

    public boolean isolation() {
        return isolation;
    }

    public void setIsolation(boolean isolation) {
        this.isolation = isolation;
    }

    public boolean isNbuyer() {
        return nbuyer;
    }

    public void setNbuyer(boolean nbuyer) {
        this.nbuyer = nbuyer;
    }

    public boolean isPart() {
        return part;
    }

    public void setPart(boolean part) {
        this.part = part;
    }

    public List<ActivityCatalogInfo> getCatalogs() {
        return catalogs;
    }

    public void setCatalogs(List<ActivityCatalogInfo> catalogs) {
        this.catalogs = catalogs;
    }


}

