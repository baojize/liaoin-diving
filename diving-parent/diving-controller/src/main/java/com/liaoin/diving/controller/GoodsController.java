package com.liaoin.diving.controller;

import com.liaoin.diving.common.PageBean;
import com.liaoin.diving.common.Result;
import com.liaoin.diving.entity.Goods;
import com.liaoin.diving.entity.Order;
import com.liaoin.diving.entity.OrderOption;
import com.liaoin.diving.entity.User;
import com.liaoin.diving.service.GoodsService;
import com.liaoin.diving.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/goods")
@Api(tags = "商品模块",value = "商品模块")
public class GoodsController {
    @Resource
    private UserService userService;
    @Resource
    private GoodsService goodsService;

    @PostMapping("/insert")
    @ApiOperation("新增")
    public Result save(@RequestBody Goods goods) {
        goodsService.insert(goods);
        return new Result(200, "新增成功", null);
    }

    @PostMapping("/update")
    @ApiOperation("修改")
    public Result update(@RequestBody Goods goods) {
        goodsService.update(goods);
        return new Result(200, "修改成功", null);
    }

    @PostMapping("/delete")
    @ApiOperation("删除")
    public Result delete(@RequestBody Integer[] ids) {
        goodsService.delete(ids);
        return new Result(200, "删除成功", null);
    }

    @GetMapping("/findOne")
    @ApiImplicitParam(name = "id", value = "主键", dataType = "String", paramType = "query", required = true)
    @ApiOperation("根据主键查询")
    public Result findOne(@RequestParam Integer id) {
        Goods goods = goodsService.findOne(id);
        if (goods == null) {
            return new Result(300, "暂无数据", null);
        }
        return new Result(200, "查询成功", goods);
    }

    @PostMapping("/pageQuery")
    @ApiOperation("条件分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页码，第一页从0开始", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "size", value = "分页大小", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "sort", value = "排序，如：id,desc，可以添加多个排序条件", dataType = "String", paramType = "query", required = false)
    })
    public Result pageQuery(@PageableDefault Pageable pageable, @RequestBody Goods goods) {
        PageBean<Goods> pageBean = goodsService.pageQuery(pageable, goods);
        if (pageBean == null) {
            return new Result(300, "暂无数据", null);
        }
        return new Result(200, "查询成功", pageBean);
    }

    /*@PostMapping("/buy")
    @ApiOperation("购买商品")
    public Result buy(HttpSession session, @RequestBody Order order){
        User loginUser = (User)session.getAttribute("loginUser");
        if (Objects.isNull(loginUser)){
            return new Result(300, "请登录", null);
        }

        // 1. 传入商品详情   getOrderOption
        List<OrderOption> orderOptionList = order.getOrderOptionList();

        if (Objects.isNull(orderOptionList) || orderOptionList.size() == 0){
            return new Result(300, "未选择任何商品", null);
        }

        for (OrderOption orderOption : orderOptionList){
            Goods goods = goodsService.findOne(orderOption.getGoods().getId());
            if (Objects.isNull(goods)){
                return new Result(300, "商品不存在", null);
            }
            orderOption.setGoods(goods);
            orderOption.setMoney(goods.getMoney().multiply(orderOption.getNum()));  // orderOption的商品小计    商品现价 * 商品数量

            BigDecimal money = order.getMoney();
            if (Objects.isNull(money)){
                money = new BigDecimal("0");
            }
            BigDecimal money1 = orderOption.getMoney();
            money = money.add(money1); //累加每件商品总价
            order.setMoney(money);
        }



        // 2. 创建订单

        order.setOrderId(new Date().getTime() + ""); //订单编号
        order.setPostage(new BigDecimal("10")); // 订单邮费

        BigDecimal amount = order.getAmount();
        BigDecimal money = order.getMoney();
        amount = money.add(order.getPostage()); //(订单总金额)   商品总金额 + 邮费

        return null;
    }*/

    @PostMapping("/buy")
    @ApiOperation("购买商品")
    public Result buy(HttpSession session, @RequestBody OrderOption orderOption){
        User loginUser = (User)session.getAttribute("loginUser");
        if (Objects.isNull(loginUser)){
            return new Result(300, "请登录", null);
        }


        return null;
    }
}

