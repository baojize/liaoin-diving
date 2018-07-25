package com.liaoin.diving.dao;

import com.liaoin.diving.entity.SystemAlipay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 支付宝支付系统参数JPA
 *
 * @author 张权立
 * @date 2018/6/19
 */
public interface SystemAlipayRepository extends JpaRepository<SystemAlipay, Integer>, JpaSpecificationExecutor<SystemAlipay> {

}
