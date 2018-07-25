package com.liaoin.diving.service.impl;

import com.liaoin.diving.dao.SystemAliyunSmsRepository;
import com.liaoin.diving.dao.SystemSmsTemplateRepository;
import com.liaoin.diving.entity.SystemAliyunSms;
import com.liaoin.diving.entity.SystemSmsTemplate;
import com.liaoin.diving.service.SystemAliyunSmsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 阿里云短信服务
 *
 * @author 张权立
 * @date 2018/06/08
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SystemAliyunSmsServiceImpl implements SystemAliyunSmsService {

    private static Logger logger = LoggerFactory.getLogger(SystemAliyunSmsServiceImpl.class);

    @Resource
    private SystemAliyunSmsRepository systemAliyunSmsRepository;
    @Resource
    private SystemSmsTemplateRepository systemSmsTemplateRepository;

    @Override
    public void update(SystemAliyunSms systemAliyunSms) {
        // 1.删除短信模板列表
        systemSmsTemplateRepository.deleteBySystemAliyunSms(systemAliyunSms);
        // 2.修改阿里云短信信息
        for (SystemSmsTemplate systemSmsTemplate : systemAliyunSms.getSystemSmsTemplateList()) {
            systemSmsTemplate.setSystemAliyunSms(systemAliyunSms);
        }
        systemAliyunSmsRepository.save(systemAliyunSms);
        logger.info("修改阿里云短信系统参数");
    }

    @Override
    public SystemAliyunSms findOne(int id) {
        return systemAliyunSmsRepository.findOne(1);
    }
}
