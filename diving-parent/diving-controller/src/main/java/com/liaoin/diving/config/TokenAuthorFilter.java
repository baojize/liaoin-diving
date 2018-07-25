package com.liaoin.diving.config;


import com.liaoin.diving.common.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author : huqingxi
 * @describe : token验证拦截
 * @date 2018/06/07
 */
//@Component
public class TokenAuthorFilter /*implements Filter*/ {
  /*  private static Logger logger = LoggerFactory.getLogger(TokenAuthorFilter.class);
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse resp = (HttpServletResponse)response;

        // 允许跨域的主机 (online set domain and IP)
        resp.setHeader("Access-Control-Allow-Origin", "*");
       // 允许访问的方法
        resp.setHeader("Access-Control-Allow-Method", "POST, GET, PUT, OPTIONS, DELETE, PATCH");
        // Access-Control-Max-Age 用于 CORS 相关配置的缓存
        resp.setHeader("Access-Control-Max-Age", "3600");
        resp.setHeader("Access-Control-Allow-Headers","token,Origin, X-Requested-With, Content-Type, Accept");

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        String token = req.getHeader("token"); //header方式
        Result result = new Result(null,null, null);
        boolean isFilter = false;

        String method = req.getMethod();
        if ("OPTIONS".equals(method)){
            resp.setStatus(200);
        }else {

        }
    }

    @Override
    public void destroy() {

    }*/
}
