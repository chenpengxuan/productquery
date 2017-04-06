package com.ymatou.productquery.domain.model;

import java.util.Date;
import java.util.List;
/**
 * Created by zhangyong on 2017/4/6.
 */
public class Products {
    private	String	spid	;//	商品编号
    private	int	ipid	;//	商品数字编号
    private	String	title	;//	商品标题
    private	String	intro	;//	商品简介
    private	List<String>	pics	;//	商品主图列表
    private	Date	start	;//	商品开始有效期
    private Date end	;//	商品结束有效期
    private	String	minp	;//	最低规格价(包括原价、新客价、vip价，如果为0也要填)数据示例：30.00,28.00,29.00
    private	Double	maxp	;//	最高规格价(包括原价、新客价、vip价，如果为0也要填)数据示例：30.00,28.00,29.00
    private	int	sid	;//	买手编号
    private	String	sname	;//	买手账号
    private	int	bid	;//	品牌编号
    private	String	brand	;//	品牌中文名称
    private	String	ebrand	;//	品牌英文名称
    private	int	mcatid	;//	一级分类编号
    private	String	mcatname	;//	一级分类名称
    private	int	scatid	;//	二级分类编号
    private	String	scatname	;//	二级分类名称
    private	int	tcatid	;//	三级分类编号
    private	String	tcatname	;//	三级分类名称
    private	int	ctype	;//	备货方式
    private	int	deliv	;//	发货方式
    private	int	bonded	;//	保税区
    private	int	tariffy	;//	是否包税
    private	int	shipping	;//	是否包邮
    private	int	country	;//	国家编号
    private	boolean	newdesc	;//	是否新图文描述
    private	int	localr	;//	本土退货
    private	boolean	noreason	;//	7天无理由退货
    private	int	action	;//	商品状态
    private	Date	addtime	;//	发布时间
    private	boolean	risk	;//	砍单风险提醒
    private	String	pcode	;//	商品备案号（保税商品）

    public String getSpid() {
        return spid;
    }

    public void setSpid(String spid) {
        this.spid = spid;
    }

    public int getIpid() {
        return ipid;
    }

    public void setIpid(int ipid) {
        this.ipid = ipid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public List<String> getPics() {
        return pics;
    }

    public void setPics(List<String> pics) {
        this.pics = pics;
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

    public String getMinp() {
        return minp;
    }

    public void setMinp(String minp) {
        this.minp = minp;
    }

    public Double getMaxp() {
        return maxp;
    }

    public void setMaxp(Double maxp) {
        this.maxp = maxp;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
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

    public int getCtype() {
        return ctype;
    }

    public void setCtype(int ctype) {
        this.ctype = ctype;
    }

    public int getDeliv() {
        return deliv;
    }

    public void setDeliv(int deliv) {
        this.deliv = deliv;
    }

    public int getBonded() {
        return bonded;
    }

    public void setBonded(int bonded) {
        this.bonded = bonded;
    }

    public int getTariffy() {
        return tariffy;
    }

    public void setTariffy(int tariffy) {
        this.tariffy = tariffy;
    }

    public int getShipping() {
        return shipping;
    }

    public void setShipping(int shipping) {
        this.shipping = shipping;
    }

    public int getCountry() {
        return country;
    }

    public void setCountry(int country) {
        this.country = country;
    }

    public boolean isNewdesc() {
        return newdesc;
    }

    public void setNewdesc(boolean newdesc) {
        this.newdesc = newdesc;
    }

    public int getLocalr() {
        return localr;
    }

    public void setLocalr(int localr) {
        this.localr = localr;
    }

    public boolean isNoreason() {
        return noreason;
    }

    public void setNoreason(boolean noreason) {
        this.noreason = noreason;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public Date getAddtime() {
        return addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    public boolean isRisk() {
        return risk;
    }

    public void setRisk(boolean risk) {
        this.risk = risk;
    }

    public String getPcode() {
        return pcode;
    }

    public void setPcode(String pcode) {
        this.pcode = pcode;
    }

    public boolean isnew() {
        return isnew;
    }

    public void setIsnew(boolean isnew) {
        this.isnew = isnew;
    }

    public Date getNewstart() {
        return newstart;
    }

    public void setNewstart(Date newstart) {
        this.newstart = newstart;
    }

    public Date getNewend() {
        return newend;
    }

    public void setNewend(Date newend) {
        this.newend = newend;
    }

    public boolean istop() {
        return istop;
    }

    public void setIstop(boolean istop) {
        this.istop = istop;
    }

    public String getVer() {
        return ver;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }

    public boolean ispsp() {
        return ispsp;
    }

    public void setIspsp(boolean ispsp) {
        this.ispsp = ispsp;
    }

    public Date getVerupdate() {
        return verupdate;
    }

    public void setVerupdate(Date verupdate) {
        this.verupdate = verupdate;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public List<String> getSizepics() {
        return sizepics;
    }

    public void setSizepics(List<String> sizepics) {
        this.sizepics = sizepics;
    }

    private	boolean	isnew	;//	是否新品
    private	Date	newstart	;//	新品有效开始时间
    private	Date	newend	;//	新品有效结束时间
    private	boolean	istop	;//	是否买手热推商品
    private	String	ver	;//	最新快照版本号
    private	boolean	ispsp	;//	是否psp商品 0不是 1是
    private	Date	verupdate	;//	快照更新时间
    private	String	grade	;//	商品等级
    private	List<String>	sizepics	;//	尺码表
}
