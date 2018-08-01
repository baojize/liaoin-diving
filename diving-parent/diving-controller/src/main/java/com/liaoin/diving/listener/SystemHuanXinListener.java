package com.liaoin.diving.listener;

import com.liaoin.diving.entity.SystemHuanXin;
import com.liaoin.diving.service.SystemHuanXinService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * 环信系统参数加载监听器
 *
 * @author 张权立
 * @date 2018/6/19 15:08
 */
@WebListener
public class SystemHuanXinListener implements ServletContextListener {

    private static Logger logger = LoggerFactory.getLogger(SystemHuanXinListener.class);

    @Resource
    private SystemHuanXinService systemHuanXinService;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        SystemHuanXin systemHuanXin = systemHuanXinService.findOne(1);
        if (systemHuanXin == null) {
            logger.info("环信系统参数【未设置】");
        } else {
            sce.getServletContext().setAttribute("systemHuanXin", systemHuanXin);
            logger.info("加载环信系统参数");
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        sce.getServletContext().removeAttribute("systemHuanXin");
        logger.info("销毁环信系统参数");
    }
}
