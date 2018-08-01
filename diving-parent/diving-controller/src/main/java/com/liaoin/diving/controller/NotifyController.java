package com.liaoin.diving.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.github.wxpay.sdk.WXPayUtil;
import com.liaoin.diving.common.OrderConstants;
import com.liaoin.diving.entity.Order;
import com.liaoin.diving.entity.SystemAlipay;
import com.liaoin.diving.entity.SystemWxpay;
import com.liaoin.diving.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 支付通知模块
 *
 * @author 张权立
 * @date 2018/6/13 10:32
 */
@Controller
@RequestMapping("/notify")
@Api(tags = "支付通知模块",value = "支付通知模块")
public class NotifyController {

    private static Logger logger = LoggerFactory.getLogger(NotifyController.class);

    @Resource
    private ServletContext servletContext;
    @Resource
    private OrderService orderService;


    @GetMapping(value = "/alipay", produces = {"text/html;charset=UTF-8"})
    @ResponseBody
    @ApiOperation("接收支付宝通知")
    public String alipay(HttpServletRequest request) throws AlipayApiException, UnsupportedEncodingException {
        // 1.校验支付宝支付系统参数是否加载
        SystemAlipay systemAlipay = (SystemAlipay) servletContext.getAttribute("systemAlipay");
        if (systemAlipay == null) {
            logger.info("支付宝支付参数加载失败");
            return "failure";
        }
        // 2.封装请求参数
        Map<String, String> map = getParameterMap(request);
        // 3.数据校验
        final String failure = "failure";
        final String success = "success";
        // 3.1 校验应用编号
        String appId = map.get("app_id");
        if (!appId.equals(systemAlipay.getAppId())) {
            logger.info("应用编号非法");
            return failure;
        }
        // 3.2 校验签名
        if (!AlipaySignature.rsaCheckV1(map, systemAlipay.getAlipayPublicKey(), "UTF-8", "RSA2")) {
            logger.info("签名验证失败");
            return failure;
        }
        // 3.3 校验商户订单号
        Order order = orderService.findOne(map.get("out_trade_no"));
        if (order == null) {
            logger.info("订单编号非法");
            return failure;
        }
        // 3.4 校验总金额
        double totalAmount = Double.parseDouble(map.get("total_amount"));
        if (order.getAmount().doubleValue() != totalAmount) {
            logger.info("订单金额异常");
            return failure;
        }
        // 3.5 校验订单状态
        if (!OrderConstants.NOT_PAY.equals(order.getStatus())) {
            logger.info("订单已经付款");
            return success;
        }
        // 4.更新订单信息
        String tradeNo = map.get("trade_no");
        order.setPayWay(OrderConstants.ALIPAY);
        order.setThirdPayNo(tradeNo);
        order.setStatus(OrderConstants.NOT_DELIVERY);
        order.setPayTime(new Date());
        orderService.update(order);
        return success;
    }

    private Map<String, String> getParameterMap(HttpServletRequest request) throws UnsupportedEncodingException {
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, String> map = new HashMap<>(16);
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            String key = entry.getKey();
            String[] values = entry.getValue();
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; ; i++) {
                if (i == values.length - 1) {
                    stringBuilder.append(values[i]);
                    break;
                }
                stringBuilder.append(values[i]).append(",");
            }
            // 解决乱码问题
            String value = new String(stringBuilder.toString().getBytes("ISO-8859-1"), "UTF-8");
            map.put(key, value);
        }
        return map;
    }

    @GetMapping(value = "/wxpay", consumes = {"application/xml;charset=UTF-8"}, produces = {"application/xml;charset=UTF-8"})
    @ResponseBody
    @ApiOperation("接收微信通知")
    public Map<String, String> wxpay(HttpServletRequest request) throws Exception {
        // 1.获取请求参数
        BufferedReader reader = request.getReader();
        StringBuilder stringBuilder = new StringBuilder();
        String str;
        while ((str = reader.readLine()) != null) {
            stringBuilder.append(str);
        }
        Map<String, String> data = WXPayUtil.xmlToMap(stringBuilder.toString());
        // 2.获取微信支付系统参数
        SystemWxpay systemWxpay = (SystemWxpay) servletContext.getAttribute("systemWxpay");
        // 3.数据验证
        // 3.1 错误返回参数
        Map<String, String> fail = new HashMap<>(16);
        fail.put("return_code", "FAIL");
        // 3.2 成功返回参数
        Map<String, String> success = new HashMap<>(16);
        success.put("return_code", "SUCCESS");
        success.put("return_msg", "OK");
        // 3.3 校验应用编号
        String appId = data.get("appid");
        if (!appId.equals(systemWxpay.getAppId())) {
            fail.put("return_msg", "应用编号非法");
            logger.info("应用编号非法");
            return fail;
        }
        // 3.4 校验商户编号
        String mchId = data.get("mch_id");
        if (!mchId.equals(systemWxpay.getMchId())) {
            fail.put("return_msg", "商户编号非法");
            logger.info("商户编号非法");
            return fail;
        }
        // 3.5 校验签名
        if (!WXPayUtil.isSignatureValid(data, systemWxpay.getKey())) {
            fail.put("return_msg", "签名验证失败");
            logger.info("签名验证失败");
            return fail;
        }
        // 3.6 校验商户订单号
        String outTradeNo = data.get("out_trade_no");
        Order order = orderService.findOne(outTradeNo);
        if (order == null) {
            fail.put("return_msg", "订单编号非法");
            logger.info("订单编号非法");
            return fail;
        }
        // 3.7 校验总金额
        double totalFee = Double.parseDouble(data.get("total_fee"));
        double amount = order.getAmount().doubleValue() * 100;
        if (totalFee != amount) {
            fail.put("return_msg", "订单金额异常");
            logger.info("订单金额异常");
            return fail;
        }
        // 3.8 校验订单状态
        if (!OrderConstants.NOT_PAY.equals(order.getStatus())) {
            logger.info("订单已经付款");
            return success;
        }
        // 4.更新订单信息
        String transactionId = data.get("transaction_id");
        order.setPayWay(OrderConstants.WXPAY);
        order.setThirdPayNo(transactionId);
        order.setStatus(OrderConstants.NOT_DELIVERY);
        order.setPayTime(new Date());
        orderService.update(order);
        return success;
    }
}
