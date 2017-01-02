package com.aop.config;

import com.aop.dto.RequestLogDTO;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.mobile.device.DeviceUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

/**
 * Created by dongsilin on 2016/12/29.
 * 访问日志AOP
 */
@Slf4j
@Aspect
@Component
@EnableAsync
public class WebRequestLogAspect {


    @Pointcut("execution(* com.aop.controller..*.*(..))")
    public void webLog(){}
    
    
    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        log.debug("CLASS_METHOD={}", joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        log.debug("ARGS={}", Arrays.toString(joinPoint.getArgs()));

        // 记录下请求内容
        saveLog(attributes.getRequest());
    }

    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
        log.debug("RESPONSE={}", ret);
    }

    /** 异步执行 */
    @Async
    private void saveLog(HttpServletRequest request){
        HttpSession session = request.getSession();
        RequestLogDTO requestLog = new RequestLogDTO();
        requestLog.setCreateTime(new Date().getTime() / 1000);
        requestLog.setDeviceName(DeviceUtils.getCurrentDevice(request).toString());
        requestLog.setSessionId(session.getId());
        requestLog.setRequestUrl(request.getRequestURL().toString());
        String requestURI = request.getRequestURI();
        requestURI = requestURI.substring(1, requestURI.length());
        if(requestURI.contains("/")) requestURI = requestURI.substring(0, requestURI.indexOf("/"));
        requestLog.setAppAlias(requestURI);
        requestLog.setRemoteIp(request.getRemoteAddr());// request.getRemoteHost()
        String loginUser = (String) session.getAttribute("loginUser");
        if(null != loginUser)  requestLog.setClientUser(loginUser);
        String uuid = (String) session.getAttribute("call_uuid");
        if(null == uuid) {
            uuid = UUID.randomUUID().toString().replaceAll("-", "");
            session.setAttribute("call_uuid", uuid);
        }
        requestLog.setCallUuid(uuid);
        log.debug("requestLog={} " , requestLog);
    }
    
}
