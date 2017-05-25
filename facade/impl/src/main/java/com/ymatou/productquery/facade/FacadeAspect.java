package com.ymatou.productquery.facade;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.ymatou.productquery.infrastructure.config.props.BizProps;
import com.ymatou.productquery.infrastructure.constants.Constants;
import com.ymatou.productquery.infrastructure.util.HttpHelper;
import com.ymatou.productquery.infrastructure.util.LogWrapper;
import com.ymatou.productquery.infrastructure.util.Utils;
import com.ymatou.productquery.model.BizException;
import com.ymatou.productquery.model.req.BaseRequest;
import com.ymatou.productquery.model.res.BaseResponseNetAdapter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.POST;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created by zhangyong on 2017/4/10.
 */
@Aspect
@Component
public class FacadeAspect {
    @Autowired
    private LogWrapper logWrapper;

    @Autowired
    private BizProps bizProps;

    @Pointcut("execution(com.ymatou.productquery.model.res.BaseResponseNetAdapter com.ymatou.productquery.facade.ProductQueryFacade.*(*)) && args(req)")
    public void executeFacade(BaseRequest req) {
    }

    @Around("executeFacade(req)")
    public Object aroundFacadeExecution(ProceedingJoinPoint joinPoint, BaseRequest req)
            throws InstantiationException, IllegalAccessException {
        Signature sig = joinPoint.getSignature();
        MethodSignature msig;
        Boolean isPost = false;
        if (sig instanceof MethodSignature) {
            msig = (MethodSignature) sig;
            Object target = joinPoint.getTarget();
            try {
                Method currentMethod = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());
                if(currentMethod.getDeclaringClass().getName().equals("com.ymatou.productquery.facade.ProductQueryFacadeImpl")){
                    Annotation postAnnotation = currentMethod.getAnnotation(POST.class);
                    isPost = postAnnotation != null;
                    ////// TODO: 2017/5/24 比对逻辑
                }
               
            } catch (NoSuchMethodException e) {

            }
        }


        if (req == null) {
            logWrapper.recordErrorLog("{} Recv: null", joinPoint.getSignature());
            return BaseResponseNetAdapter.newBusinessFailureInstance("request is null");
        }

        if (req.requireRequestId() && StringUtils.isEmpty(req.getRequestId())) {
            return BaseResponseNetAdapter.newBusinessFailureInstance("requestId not provided");
        }

        if (req.requireAppId() && StringUtils.isEmpty(req.getAppId())) {
            return BaseResponseNetAdapter.newBusinessFailureInstance("appId not provided");
        }

        long startTime = System.currentTimeMillis();

        if (StringUtils.isEmpty(req.getRequestId())) {
            req.setRequestId(Utils.uuid());
        }

        // log日志配有"logPrefix"占位符
        MDC.put(Constants.LOG_PREFIX, getRequestFlag(req));

        logWrapper.recordInfoLog("RequestInfo:{}", req);

        Object resp = null;

        try {
            req.validate();
            resp = joinPoint.proceed(new Object[]{req});

            if (bizProps.getOpenComparison()) {

            }
        } catch (IllegalArgumentException e) {
            resp = BaseResponseNetAdapter.newBusinessFailureInstance(e.getLocalizedMessage());
            logWrapper.recordErrorLog("Invalid request: {}", req, e);
        } catch (BizException e) {
            //前端可能将错误msg直接抛给用户
            resp = BaseResponseNetAdapter.newBusinessFailureInstance(e.getLocalizedMessage());
            logWrapper.recordErrorLog("Failed to execute request: {}, Error:{}", req.getRequestId(),
                    e.getMessage(), e);
        } catch (Throwable e) {
            //前端可能将错误msg直接抛给用户
            resp = BaseResponseNetAdapter.newSystemFailureInstance(e.getLocalizedMessage(), e);
            logWrapper.recordErrorLog("Unknown error in executing request:{}", req, e);
        } finally {
            long consumedTime = System.currentTimeMillis() - startTime;

            if (consumedTime >= 300L) {
                logWrapper.recordErrorLog("Slow query({}ms). Req:{}", consumedTime, req);
            }
            MDC.clear();
        }
        logWrapper.recordInfoLog("ResponseInfo:{}", resp);
        return resp;
    }

    private String getRequestFlag(BaseRequest req) {
        return req.getClass().getSimpleName() + "|" + req.getRequestId();
    }

    //新老接口返回结果比对
    private void CompareAsync(BaseRequest request, String javaResult) {
        final Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    String url = "proditem.iapi.ymatou.com";
                    HttpHelper.getURLContent(url);

                } catch (Exception e) {
                }
            }
        }
        );
        t.start();
    }
}
