package com.liaoin.diving.dao;

import com.liaoin.diving.entity.SystemAliyunSms;
import com.liaoin.diving.entity.SystemSmsTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 短信模板系统参数JPA
 *
 * @author 张权立
 * @date 2018/6/19
 */
public interface SystemSmsTemplateRepository extends JpaRepository<SystemSmsTemplate, Integer>, JpaSpecificationExecutor<SystemSmsTemplate> {

    /**
     * 根据阿里云短信系统参数，删除短信模板
     *
     * @param systemAliyunSms 阿里云短信系统参数
     */
    void deleteBySystemAliyunSms(SystemAliyunSms systemAliyunSms);
}
