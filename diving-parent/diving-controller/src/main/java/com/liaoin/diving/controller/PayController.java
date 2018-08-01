package com.liaoin.diving.controller;

import com.alipay.api.AlipayApiException;
import com.liaoin.diving.common.OrderConstants;
import com.liaoin.diving.common.Result;
import com.liaoin.diving.entity.Order;
import com.liaoin.diving.entity.SystemAlipay;
import com.liaoin.diving.entity.SystemWxpay;
import com.liaoin.diving.entity.User;
import com.liaoin.diving.service.OrderService;
import com.liaoin.diving.service.PayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

/**
 * 支付模块
 *
 * @author 张权立
 * @date 2018/6/8 9:44
 */
@RestController
@RequestMapping("/pay")
@Api(tags = "支付模块",value = "支付模块")
public class PayController {

    private static Logger logger = LoggerFactory.getLogger(PayController.class);

    @Resource
    private ServletContext servletContext;
    @Resource
    private OrderService orderService;
    @Resource
    private PayService payService;

    @GetMapping("/alipay")
    @ApiOperation("获取订单支付信息，支付宝支付")
    @ApiImplicitParam(name = "orderId", value = "订单编号", dataType = "String", paramType = "query", required = true)
    public Result alipay(HttpSession session, @RequestParam String orderId) throws AlipayApiException {
        // 1.校验用户是否登录
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) {
            return new Result(300, "请登录", null);
        }
        // 2.校验支付宝支付系统参数是否加载
        SystemAlipay systemAlipay = (SystemAlipay) servletContext.getAttribute("systemAlipay");
        if (systemAlipay == null) {
            logger.info("支付宝支付系统参数异常");
            return new Result(300, "系统参数异常", null);
        }
        // 3.校验订单编号是否合法
        Order order = orderService.findOne(orderId);
        if (order == null) {
            return new Result(300, "订单不存在", null);
        }
        // 4.校验订单状态是否合法
        if (!OrderConstants.NOT_PAY.equals(order.getStatus())) {
            return new Result(300, "订单已付款", null);
        }
        // 5.校验用户和订单是否匹配
        if (!order.getUser().equals(loginUser)) {
            return new Result(300, "非当前用户订单", null);
        }
        String data = payService.alipay(systemAlipay, order);
        return new Result(200, "获取成功", data);
    }

    @GetMapping("/wxpay")
    @ApiOperation("获取订单支付信息，微信支付")
    @ApiImplicitParam(name = "orderId", value = "订单编号", dataType = "String", paramType = "query", required = true)
    public Result wxpay(HttpSession session, @RequestParam String orderId) throws Exception {
        // 1.校验用户是否登录
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) {
            return new Result(300, "请登录", null);
        }
        // 2.校验微信支付系统参数是否加载
        SystemWxpay systemWxpay = (SystemWxpay) servletContext.getAttribute("systemWxpay");
        if (systemWxpay == null) {
            logger.info("微信支付系统参数异常");
            return new Result(300, "系统参数异常", null);
        }
        // 3.校验订单编号是否合法
        Order order = orderService.findOne(orderId);
        if (order == null) {
            return new Result(300, "订单不存在", null);
        }
        // 4.校验订单状态是否合法
        if (!OrderConstants.NOT_PAY.equals(order.getStatus())) {
            return new Result(300, "订单已付款", null);
        }
        // 5.校验用户和订单是否匹配
        if (!order.getUser().equals(loginUser)) {
            return new Result(300, "非当前用户订单", null);
        }
        String data = payService.wxpay(systemWxpay, order);
        return new Result(200, "获取成功", data);
    }
}
