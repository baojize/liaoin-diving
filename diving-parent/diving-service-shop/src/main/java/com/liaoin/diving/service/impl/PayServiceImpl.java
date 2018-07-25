package com.liaoin.diving.service.impl;

import com.alipay.api.AlipayApiException;
import com.github.wxpay.sdk.WXPayConfig;
import com.liaoin.diving.entity.Order;
import com.liaoin.diving.entity.SystemAlipay;
import com.liaoin.diving.entity.SystemWxpay;
import com.liaoin.diving.service.PayService;
import com.zhangquanli.alipay.AlipayUtils;
import com.zhangquanli.wxpay.MyConfig;
import com.zhangquanli.wxpay.WxpayUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 支付服务实现类
 *
 * @author 张权立
 * @date 2018/6/8 10:03
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class PayServiceImpl implements PayService {

    @Override
    public String alipay(SystemAlipay systemAlipay, Order order) throws AlipayApiException {
        // 订单编号
        String orderId = order.getOrderId();
        // 订单总金额，以元为单位，精确到小数点后2位
        BigDecimal amount = order.getAmount();
        // 支付宝网关
        String serverUrl = systemAlipay.getServerUrl();
        // 应用编号
        String appId = systemAlipay.getAppId();
        // 应用私钥
        String privateKey = systemAlipay.getPrivateKey();
        // 支付宝公钥
        String alipayPublicKey = systemAlipay.getAlipayPublicKey();
        // 回调地址
        String notifyUrl = systemAlipay.getNotifyUrl();
        // 交易标题
        String subject = systemAlipay.getSubject();
        return AlipayUtils.payByApp(serverUrl, appId, privateKey, alipayPublicKey, notifyUrl, subject, orderId, amount.toString());
    }

    @Override
    public String wxpay(SystemWxpay systemWxpay, Order order) throws Exception {
        WXPayConfig config = new MyConfig(systemWxpay.getAppId(), systemWxpay.getMchId(), systemWxpay.getKey());
        Map<String, String> data = new HashMap<>(16);
        data.put("body", "潜水商城-实物商品");
        data.put("out_trade_no", order.getOrderId());
        data.put("total_fee", order.getAmount().doubleValue() * 100 + "");
        data.put("spbill_create_ip", systemWxpay.getSpbillCreateIp());
        data.put("notify_url", systemWxpay.getNotifyUrl());
        data.put("trade_type", "APP");
        return WxpayUtils.payByApp(config, data);
    }
}
