package com.liaoin.diving.controller;

import com.liaoin.diving.common.PageBean;
import com.liaoin.diving.common.Result;
import com.liaoin.diving.entity.Order;
import com.liaoin.diving.entity.User;
import com.liaoin.diving.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * 订单模块
 *
 * @author 张权立
 * @date 2018/06/07
 */
@RestController
@RequestMapping("/order")
@Api(tags = "订单模块",value = "订单模块")
public class OrderController {

    private static final String NOT_PAY = "1";

    @Resource
    private OrderService orderService;

    @PostMapping("/insert")
    @ApiOperation("新增")
    public Result save(@RequestBody Order order) {
        orderService.insert(order);
        return new Result(200, "新增成功", null);
    }

    @PostMapping("/update")
    @ApiOperation("修改")
    public Result update(@RequestBody Order order) {
        orderService.update(order);
        return new Result(200, "修改成功", null);
    }

    @PostMapping("/delete")
    @ApiOperation("删除")
    public Result delete(@RequestBody String[] orderIds) {
        orderService.delete(orderIds);
        return new Result(200, "删除成功", null);
    }

    @GetMapping("/findOne")
    @ApiImplicitParam(name = "orderId", value = "主键", dataType = "String", paramType = "query", required = true)
    @ApiOperation("根据主键查询")
    public Result findOne(@RequestParam String orderId) {
        Order order = orderService.findOne(orderId);
        if (order == null) {
            return new Result(300, "暂无数据", null);
        }
        return new Result(200, "查询成功", order);
    }

    @PostMapping("/pageQuery")
    @ApiOperation("条件分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页码，第一页从0开始", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "size", value = "分页大小", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "sort", value = "排序，如：id,desc，可以添加多个排序条件", dataType = "String", paramType = "query")
    })
    public Result pageQuery(@PageableDefault Pageable pageable, @RequestBody Order order) {
        PageBean<Order> pageBean = orderService.pageQuery(pageable, order);
        if (pageBean == null) {
            return new Result(300, "暂无数据", null);
        }
        return new Result(200, "查询成功", pageBean);
    }

    @PostMapping("/submit")
    @ApiOperation("提交订单")
    public Result submit(HttpSession session, @RequestBody Order order) {
        // 1.用户是否登录
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) {
            return new Result(300, "请登录", null);
        }
        return orderService.submit(loginUser, order);
    }

    @GetMapping("/check")
    @ApiOperation("校验订单状态")
    @ApiImplicitParam(name = "orderId", value = "订单编号", dataType = "String", paramType = "query", required = true)
    public Result check(HttpSession session, @RequestParam String orderId) {
        // 1.校验用户是否登录
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) {
            return new Result(300, "请登录", null);
        }
        // 2.校验订单编号是否合法
        Order order = orderService.findOne(orderId);
        if (order == null) {
            return new Result(300, "订单不存在", null);
        }
        // 3.校验订单状态是否合法
        if (NOT_PAY.equals(order.getStatus())) {
            return new Result(300, "订单未付款", null);
        }
        // 4.校验用户和订单是否匹配
        if (!order.getUser().equals(loginUser)) {
            return new Result(300, "非当前用户订单", null);
        }
        return new Result(200, "订单已付款", null);
    }
}
