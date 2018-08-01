package com.zhangquanli.wxpay;

import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConfig;

import java.util.Map;

/**
 * 微信支付工具类
 *
 * @author 张权立
 * @date 2018/6/11 15:16
 */
public class WxpayUtils {

    /**
     * @param config 微信支付配置
     * @param data   微信支付参数
     * @return 返回XML格式的字符串
     * @throws Exception 抛出异常
     */
    public static String payByApp(WXPayConfig config, Map<String, String> data) throws Exception {
        WXPay wxPay = new WXPay(config);
        Map<String, String> response = wxPay.unifiedOrder(data);
        if (!("SUCCESS".equals(response.get("return_code")) && "SUCCESS".equals(response.get("result_code")))) {
            throw new RuntimeException(response.get("return_code") + "::" + response.get("return_msg"));
        }
        return response.get("prepay_id");
    }
}
