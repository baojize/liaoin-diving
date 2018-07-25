package com.liaoin.diving.listener;

import com.liaoin.diving.entity.SystemAliyunSms;
import com.liaoin.diving.service.SystemAliyunSmsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * 阿里云短信系统参数加载监听器
 *
 * @author 张权立
 * @date 2018/06/08
 */
@WebListener
public class SystemAliyunSmsListener implements ServletContextListener {

    private static Logger logger = LoggerFactory.getLogger(SystemAliyunSmsListener.class);

    @Resource
    private SystemAliyunSmsService systemAliyunSmsService;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        SystemAliyunSms systemAliyunSms = systemAliyunSmsService.findOne(1);
        if (systemAliyunSms == null) {
            logger.info("阿里云短信系统参数【未设置】");
        } else {
            sce.getServletContext().setAttribute("systemAliyunSms", systemAliyunSms);
            logger.info("加载阿里云短信系统参数");
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        sce.getServletContext().removeAttribute("systemAliyunSms");
        logger.info("销毁阿里云短信系统参数");
    }
}
