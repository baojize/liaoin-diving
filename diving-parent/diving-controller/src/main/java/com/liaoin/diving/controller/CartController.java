package com.liaoin.diving.controller;

import com.liaoin.diving.common.Cart;
import com.liaoin.diving.common.Result;
import com.liaoin.diving.entity.Goods;
import com.liaoin.diving.entity.User;
import com.liaoin.diving.service.CartService;
import com.liaoin.diving.service.GoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;

@RestController
@RequestMapping("/cart")
@Api(tags = "购物车模块",value = "购物车模块")
public class CartController {

    @Resource
    private GoodsService goodsService;
    @Resource
    private CartService cartService;

    @GetMapping("/getCart")
    @ApiOperation("获取购物车")
    public Result getCart(HttpSession session) {
        // 1.判断用户是否存在
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) {
            return new Result(300, "请登录", null);
        }
        // 2.获取购物车
        Cart cart = cartService.getCart(loginUser);
        if (cart == null) {

            return new Result(300, "购物车空空如也", null);
        }
        return new Result(200, "获取成功", cart);
    }

    @GetMapping("/addToCart")
    @ApiOperation("加入购物车")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "goodsId", value = "商品编号", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "num", value = "商品数量", dataType = "String", paramType = "query", required = true)
    })
    public Result addToCart(HttpSession session, @RequestParam Integer goodsId, @RequestParam BigDecimal num) throws IOException {
        // 1.判断用户是否存在
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) {
            return new Result(300, "请登录", null);
        }
        // 2.判断商品是否存在
        Goods goods = goodsService.findOne(goodsId);
        if (goods == null) {
            return new Result(300, "商品" + goodsId + "不存在", null);
        }
        // 3.加入购物车
        cartService.addToCart(loginUser, goods, num);
        return new Result(200, "加入成功", null);
    }

    /*@PostMapping("/updateCart")
    @ApiOperation("修改购物车")
    public Result updateCart(HttpSession session, @RequestBody Cart cart) {
        // 1.判断用户是否存在
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) {
            return new Result(300, "请登录", null);
        }
        // 2.修改购物车
        cartService.updateCart(loginUser, cart);
        return new Result(200, "修改成功", null);
    }*/
}
