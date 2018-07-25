package com.liaoin.diving.controller;

import com.liaoin.diving.common.Result;
import com.liaoin.diving.entity.SystemAlipay;
import com.liaoin.diving.entity.SystemAliyunSms;
import com.liaoin.diving.entity.SystemHuanXin;
import com.liaoin.diving.entity.SystemWxpay;
import com.liaoin.diving.service.SystemAlipayService;
import com.liaoin.diving.service.SystemAliyunSmsService;
import com.liaoin.diving.service.SystemHuanXinService;
import com.liaoin.diving.service.SystemWxpayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

/**
 * 系统参数模块
 *
 * @author 张权立
 * @date 2018/06/07
 */
@RestController
@RequestMapping("/system")
@Api(tags = "系统参数模块",value = "系统参数模块")
public class SystemController {

    @Resource
    private SystemAliyunSmsService systemAliyunSmsService;
    @Resource
    private SystemAlipayService systemAlipayService;
    @Resource
    private SystemWxpayService systemWxpayService;
    @Resource
    private SystemHuanXinService systemHuanXinService;
    @Resource
    private ServletContext servletContext;

    @PostMapping("/update/aliyun/sms")
    @ApiOperation("修改阿里云短信系统参数")
    public Result updateSystemAliyunSms(@RequestBody SystemAliyunSms systemAliyunSms) {
        // 1.始终都是修改一条数据
        systemAliyunSms.setId(1);
        systemAliyunSmsService.update(systemAliyunSms);
        // 2.更新阿里云短信系统参数，始终获取一条数据
        systemAliyunSms = systemAliyunSmsService.findOne(1);
        servletContext.setAttribute("systemAliyunSms", systemAliyunSms);
        return new Result(200, "修改成功", null);
    }

    @PostMapping("/update/alipay")
    @ApiOperation("修改支付宝支付系统参数")
    public Result updateSystemAlipay(@RequestBody SystemAlipay systemAlipay) {
        // 1.始终都是修改一条数据
        systemAlipay.setId(1);
        systemAlipayService.update(systemAlipay);
        // 2.更新支付宝支付系统参数，始终获取一条数据
        systemAlipay = systemAlipayService.findOne(1);
        servletContext.setAttribute("systemAlipay", systemAlipay);
        return new Result(200, "修改成功", null);
    }

    @PostMapping("/update/wxpay")
    @ApiOperation("修改微信支付系统参数")
    public Result updateSystemWxpay(@RequestBody SystemWxpay systemWxpay) {
        // 1.始终都是修改一条数据
        systemWxpay.setId(1);
        systemWxpayService.update(systemWxpay);
        // 2.更新微信支付系统参数，始终获取一条数据
        systemWxpay = systemWxpayService.findOne(1);
        servletContext.setAttribute("systemWxpay", systemWxpay);
        return new Result(200, "修改成功", null);
    }

    @PostMapping("/update/huanXin")
    @ApiOperation("修改环信系统参数")
    public Result updateSystemHuanXin(@RequestBody SystemHuanXin systemHuanXin) {
        // 1.始终都是修改一条数据
        systemHuanXin.setId(1);
        systemHuanXinService.update(systemHuanXin);
        // 2.更新环信系统参数，始终获取一条数据
        systemHuanXin = systemHuanXinService.findOne(1);
        servletContext.setAttribute("systemHuanXin", systemHuanXin);
        return new Result(200, "修改成功", null);
    }
}
