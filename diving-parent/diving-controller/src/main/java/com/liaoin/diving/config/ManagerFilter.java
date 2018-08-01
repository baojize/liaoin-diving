package com.liaoin.diving.config;

import com.liaoin.diving.entity.manager.Admin;
import org.apache.http.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author : huqingxi
 * @describe : 后台管理过滤器
 * @date 2018/06/07
 */
@WebFilter("/manager/*")
public class ManagerFilter implements Filter {
    private static Logger logger = LoggerFactory.getLogger(ManagerFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse resp = (HttpServletResponse)response;

        List<String> exclude = new ArrayList<>(); // 排除过滤
        exclude.add("/manager/user/login");

        if (exclude.indexOf(req.getRequestURI()) == 0){
            chain.doFilter(req, resp);
            return;
        }

        HttpSession session = req.getSession();
        Object admin =  session.getAttribute("admin");
        if (Objects.isNull(admin)){
            resp.sendError(HttpServletResponse.SC_MOVED_PERMANENTLY, new String("非法访问".getBytes("UTF-8")) );
            return;
        }
        chain.doFilter(req, resp);
    }

    @Override
    public void destroy() {

    }
}
