package com.liaoin.diving.dao;

import com.liaoin.diving.entity.SystemAliyunSms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 阿里云短信系统参数JPA
 *
 * @author 张权立
 * @date 2018/6/19
 */
public interface SystemAliyunSmsRepository extends JpaRepository<SystemAliyunSms, Integer>, JpaSpecificationExecutor<SystemAliyunSms> {

}
