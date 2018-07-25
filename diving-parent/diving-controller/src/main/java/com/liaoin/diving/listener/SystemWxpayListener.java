package com.liaoin.diving.listener;

import com.liaoin.diving.entity.SystemWxpay;
import com.liaoin.diving.service.SystemWxpayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * 微信支付系统参数加载监听器
 *
 * @author 张权立
 * @date 2018/6/19 15:08
 */
@WebListener
public class SystemWxpayListener implements ServletContextListener {

    private static Logger logger = LoggerFactory.getLogger(SystemWxpayListener.class);

    @Resource
    private SystemWxpayService systemWxpayService;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        SystemWxpay systemWxpay = systemWxpayService.findOne(1);
        if (systemWxpay == null) {
            logger.info("微信支付系统参数【未设置】");
        } else {
            sce.getServletContext().setAttribute("systemWxpay", systemWxpay);
            logger.info("加载微信支付系统参数");
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        sce.getServletContext().removeAttribute("systemWxpay");
        logger.info("销毁微信支付系统参数");
    }
}
