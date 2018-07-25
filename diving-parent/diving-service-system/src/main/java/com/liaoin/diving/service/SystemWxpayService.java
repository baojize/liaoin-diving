package com.liaoin.diving.service;

import com.liaoin.diving.entity.SystemWxpay;

/**
 * 微信支付系统参数服务接口
 *
 * @author 张权立
 * @date 2018/6/19 15:14
 */
public interface SystemWxpayService {
    void update(SystemWxpay systemWxpay);

    SystemWxpay findOne(int id);
}
