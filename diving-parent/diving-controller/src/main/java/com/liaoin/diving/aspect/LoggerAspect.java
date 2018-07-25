package com.liaoin.diving.aspect;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * 日志切面类
 *
 */

@Aspect
@Component
public class LoggerAspect {
    private  final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Pointcut("execution(* com.liaoin.diving.controller.*.*(..))")
    public void log(){

    }
//方法运行前打印
    @Before("log()")
    public void before(JoinPoint joinPoint){
        //获得request
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        //获得类 方法名
        String classMethod = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        RequestLog requestLog = new RequestLog(
                request.getRequestURI().toString(),
                request.getRemoteAddr(),
               classMethod,
                joinPoint.getArgs()
        );
        logger.info("  Request :  {}----------",requestLog);
    }
    //方法运行后打印
    @After("log()")
    public void after(){
//        logger.info("---------after  2------------");
    }
    //获取方法返回值
    @AfterReturning(returning = "result",pointcut = "log()")
    public void doAfterRuntrning(Object result){
        logger.info(" return  -----  : {}",result);
    }

    private class RequestLog{
        private String url;
        private String ip;
        private String classMethod;
        private Object[] args;

        public RequestLog(String url, String ip, String classMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }

        @Override
        public String toString() {
            return "RequestLog{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }
}
