package com.liaoin.diving.dao;

import com.liaoin.diving.entity.SystemWxpay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 微信支付系统参数JPA
 *
 * @author 张权立
 * @date 2018/6/19 15:38
 */
public interface SystemWxpayRepository extends JpaRepository<SystemWxpay, Integer>, JpaSpecificationExecutor<SystemWxpay> {

}
