package com.ymatou.productquery.model.res;

import com.ymatou.productquery.model.PrintFriendliness;

/**
 * Created by zhangyong on 2017/4/10.
 */
public class BaseResponse extends PrintFriendliness {

    private static final long serialVersionUID = -5719901720924490738L;

    private boolean isSuccess = true;

    private String errorMessage;

    private int errorCode;

    private String message;

    public BaseResponse(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public BaseResponse() {
    }

    public static BaseResponse newSuccessInstance() {
        BaseResponse result = new BaseResponse();
        result.setSuccess(true);
        result.setMessage("处理成功");
        return result;
    }

    public static BaseResponse newFailInstance(int errorCode,String message) {
        BaseResponse result = new BaseResponse();
        result.setSuccess(false);
        result.setErrorCode(errorCode);
        result.setErrorMessage(message);
        return result;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
