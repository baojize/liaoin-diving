package com.zhangquanli.alipay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;

/**
 * 支付宝支付工具类
 *
 * @author 张权立
 * @date 2018/06/07
 */
public class AlipayUtils {

    /**
     * app支付
     *
     * @param serverUrl       支付宝网关
     * @param appId           应用编号
     * @param privateKey      应用私钥
     * @param alipayPublicKey 支付宝公钥
     * @param notifyUrl       回调地址
     * @param subject         交易标题
     * @param outTradeNo      商户网站唯一订单号
     * @param totalAmount     订单总金额，单位为元，精确到小数点后两位
     * @return 返回支付订单信息的字符串
     * @throws AlipayApiException 支付出错抛出异常
     */
    public static String payByApp(String serverUrl, String appId, String privateKey, String alipayPublicKey,
                                  String notifyUrl, String subject, String outTradeNo, String totalAmount) throws AlipayApiException {
        //实例化客户端
        AlipayClient alipayClient = new DefaultAlipayClient(serverUrl, appId, privateKey, "json", "utf-8", alipayPublicKey, "RSA2");
        //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setSubject(subject);
        model.setOutTradeNo(outTradeNo);
        model.setTimeoutExpress("30m");
        model.setTotalAmount(totalAmount);
        model.setProductCode("QUICK_MSECURITY_PAY");
        request.setBizModel(model);
        request.setNotifyUrl(notifyUrl);
        AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
        return response.getBody();
    }
}
