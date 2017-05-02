package com.ymatou.productquery.model.req;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by zhujinfeng on 2017/5/2.
 */
public class GetTopProductListByLiveIdRequest extends BaseRequest {

    @JsonProperty("LiveId")
    private int liveId;

    @JsonProperty("TopCount")
    private int topCount;

    public int getLiveId() {
        return liveId;
    }

    public void setLiveId(int liveId) {
        this.liveId = liveId;
    }

    public int getTopCount() {
        return topCount;
    }

    public void setTopCount(int topCount) {
        this.topCount = topCount;
    }
}
