package com.ymatou.productquery.model.req;

import org.hibernate.validator.constraints.NotEmpty;

import javax.ws.rs.QueryParam;
import java.util.List;

/**
 * Created by zhujinfeng on 2017/5/2.
 */
public class GetSellerRecommendProductListRequest extends BaseRequest {

    @QueryParam("SellerIdList")
    @NotEmpty(message="买手ID不能为空")
    private List<Integer> sellerIdList;

    public List<Integer> getSellerIdList(){return sellerIdList;}
    public void setSellerIdList(List<Integer> sellerIdList){this.sellerIdList = sellerIdList;}

}
