package com.liaoin.diving.controller;

import com.github.pagehelper.PageInfo;
import com.liaoin.diving.common.PageHelp;
import com.liaoin.diving.common.Result;
import com.liaoin.diving.entity.*;

import com.liaoin.diving.service.WareService;
import com.liaoin.diving.utils.IdUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author : huqingxi
 * @describe :
 * @date 2018/06/07
 */
/*@RestController
@RequestMapping("/ware")
@Api(tags = "商品模块new",value = "商品模块")*/
public class WareController {
    /*@Resource
    private WareService wareService;



    @PostMapping("/add")
    @ApiOperation("添加商品")
    public Result add(HttpSession session, @RequestBody Ware ware){
        User loginUser = (User)session.getAttribute("loginUser");
        if (Objects.isNull(loginUser)){
            return new Result(300, "请登录", null);
        }
        wareService.add(ware);
        return new Result(200, "添加成功", null);
    }
    @PostMapping("/del") // 物理删除
    @ApiImplicitParam(name = "id", value = "商品id", required = true)
    @ApiOperation("删除商品")
    public Result delete(HttpSession session, Integer id){
        User loginUser = (User)session.getAttribute("loginUser");
        if (Objects.isNull(loginUser)){
            return new Result(300, "请登录", null);
        }
        wareService.del(id);
        return new Result(200, "删除成功", null);
    }
    @PostMapping("/update")
    @ApiOperation("修改商品")
    public Result update(HttpSession session, @RequestBody Ware ware){
        User loginUser = (User)session.getAttribute("loginUser");
        if (Objects.isNull(loginUser)){
            return new Result(300, "请登录", null);
        }
        wareService.upload(ware);
        return new Result(200, "修改成功", null);
    }
    @GetMapping("/findById")
    @ApiImplicitParam(name = "id", value = "商品id")
    @ApiOperation("根据主键查询")
    public Result findById(HttpSession session, Integer id){
        User loginUser = (User)session.getAttribute("loginUser");
        if (Objects.isNull(loginUser)){
            return new Result(300, "请登录", null);
        }
        Ware ware = wareService.findById(id);
        if (Objects.isNull(ware)){
            return new Result(200, "什么都没有", null);
        }
        return new Result(200, "查询成功", ware);
    }

    @GetMapping("/findAll")
    @ApiOperation("查询所有商品")
    public Result findAll(HttpSession session,Integer start, Integer pageSize){
        User loginUser = (User)session.getAttribute("loginUser");
        if (Objects.isNull(loginUser)){
            return new Result(300, "请登录", null);
        }
        PageHelp pageHelp = new PageHelp(start, pageSize, null);
        List<Ware> wareList = wareService.findAll(pageHelp);
        if (Objects.isNull(wareList) || wareList.size() == 0){
            return new Result(200, "什么都没有", null);
        }
        return new Result(200, "查询成功", new PageInfo<>(wareList));
    }
    @GetMapping("/relation")
    @ApiOperation("查询尺寸类型所对应的库存")
    public Result findRelation(HttpSession session, Integer typeId, Integer sizeId){
        User loginUser = (User)session.getAttribute("loginUser");
        if (Objects.isNull(loginUser)){
            return new Result(300, "请登录", null);
        }
        Integer stock = wareService.findStock4TypeAndSize(typeId, sizeId);
        if (Objects.isNull(stock)){
            stock = 0;
        }
        return new Result(200, "查询成功", stock);
    }

    @PostMapping("/buy")
    @ApiOperation("购买商品")
    public Result buy(HttpSession session, @RequestBody WareOrder order){
        User loginUser = (User)session.getAttribute("loginUser");
        if (Objects.isNull(loginUser)){
            return new Result(300, "请登录", null);
        }
        BigDecimal orderMoney = new BigDecimal("0"); // order金额
        List<WareOption> wareOptionList = order.getWareOptionList();
        for (WareOption wareOption: wareOptionList){
            Ware ware = wareOption.getWare();
            Ware dbWare = wareService.findById(ware.getId());
            if (ware.getId() != dbWare.getId()){
                return new Result(300, "交易错误", null);
            }
            BigDecimal totalMoney = dbWare.getPrice().multiply(wareOption.getNum()); // 计算出的总价
            if (wareOption.getMoney().compareTo(totalMoney) != 0){
                return new Result(300, "交易错误", null);
            }
            orderMoney = orderMoney.add(totalMoney); // 累加总价
        }
        order.setOrderId(IdUtils.makeOrderNum()); // 设置订单号
        order.setNum(new BigDecimal(wareOptionList.size() + "")); // 商品数量
        order.setMoney(orderMoney); // 订单金额

        BigDecimal amount = order.getAmount(); // .总金额
        BigDecimal postage = new BigDecimal("10");// .邮费
        amount = orderMoney.add(postage);

        order.setAmount(amount); // 设置总金额 (总价 + 邮费 )
        order.setPostage(postage);// 设置邮费
        order.setStatus("1"); // 设置状态 1、未付款，2、未发货，3、待收货，4、已完成
        order.setCreateTime(new Date()); // 设置创建时间
        order.setIsDelete("0"); // 设置是否删除
        order.setUser(loginUser.getId()); // 设置用户id

        try {
            wareService.save(order);  // 保存订单
            return new Result(200,"操作成功", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(200,"操作失败", null);
        }
    }*/

}
