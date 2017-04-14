package com.ymatou.productquery.model.res;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Created by zhangyong on 2017/4/10.
 */
/// <summary>
/// 直播商品
/// </summary>
public class LiveProductCartDto
{
    /// <summary>
    /// 直播编号
    /// </summary>
    @JsonProperty("LiveId")
    private int liveId;

    /// <summary>
    /// 直播名称
    /// </summary>
    @JsonProperty("LiveName")
    private String liveName;

    /// <summary>
    /// 直播开始时间
    /// </summary>
    @JsonProperty("StartTime")
    private Date startTime;

    /// <summary>
    /// 直播结束时间
    /// </summary>
    @JsonProperty("EndTime")
    private Date endTime;

    /// <summary>
    /// 直播商品售卖状态
    /// </summary>
    @JsonProperty("SellStatus")
    private int sellStatus;

    public int getLiveId() {
        return liveId;
    }

    public void setLiveId(int liveId) {
        this.liveId = liveId;
    }

    public String getLiveName() {
        return liveName;
    }

    public void setLiveName(String liveName) {
        this.liveName = liveName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getSellStatus() {
        return sellStatus;
    }

    public void setSellStatus(int sellStatus) {
        this.sellStatus = sellStatus;
    }
}
