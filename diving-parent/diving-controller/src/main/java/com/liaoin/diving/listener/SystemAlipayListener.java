package com.liaoin.diving.listener;

import com.liaoin.diving.entity.SystemAlipay;
import com.liaoin.diving.service.SystemAlipayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * 支付宝支付系统参数加载监听器
 *
 * @author 张权立
 * @date 2018/06/08
 */
@WebListener
public class SystemAlipayListener implements ServletContextListener {

    private static Logger logger = LoggerFactory.getLogger(SystemAlipayListener.class);

    @Resource
    private SystemAlipayService systemAlipayService;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        SystemAlipay systemAlipay = systemAlipayService.findOne(1);
        if (systemAlipay == null) {
            logger.info("支付宝支付系统参数【未设置】");
        } else {
            sce.getServletContext().setAttribute("systemAlipay", systemAlipay);
            logger.info("加载支付宝支付系统参数");
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        sce.getServletContext().removeAttribute("systemAlipay");
        logger.info("销毁支付宝支付系统参数");
    }
}
