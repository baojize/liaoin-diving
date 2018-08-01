package com.liaoin.diving.service;

import com.alipay.api.AlipayApiException;
import com.liaoin.diving.entity.Order;
import com.liaoin.diving.entity.SystemAlipay;
import com.liaoin.diving.entity.SystemWxpay;

/**
 * 支付服务
 *
 * @author 张权立
 * @date 2018/6/8 9:58
 */
public interface PayService {

    /**
     * 支付宝支付
     *
     * @param systemAlipay 支付宝支付系统参数
     * @param order        订单
     * @return 支付订单信息
     * @throws AlipayApiException 支付出错抛出异常
     */
    String alipay(SystemAlipay systemAlipay, Order order) throws AlipayApiException;

    /**
     * 微信支付
     *
     * @param systemWxpay 微信支付系统参数
     * @param order       订单
     * @return 预支付的微信订单号
     * @throws Exception 支付出错抛出异常
     */
    String wxpay(SystemWxpay systemWxpay, Order order) throws Exception;
}
