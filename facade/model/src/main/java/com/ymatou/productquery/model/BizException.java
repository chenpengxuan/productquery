package com.ymatou.productquery.model;

/**
 * 业务异常
 *
 * @author tuwenjie
 */
public class BizException extends RuntimeException {
    private static final long serialVersionUID = 1857440708804128584L;

    public BizException(String msg) {

        this(msg, null);
    }


    public BizException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public static void throwBizException(String message) {
        throw new BizException(message);
    }
}
