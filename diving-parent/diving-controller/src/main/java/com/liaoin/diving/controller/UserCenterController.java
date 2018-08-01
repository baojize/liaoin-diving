package com.liaoin.diving.controller;

import com.github.pagehelper.PageInfo;
import com.liaoin.diving.common.Cart;
import com.liaoin.diving.common.CartOption;
import com.liaoin.diving.common.PageHelp;
import com.liaoin.diving.common.Result;
import com.liaoin.diving.entity.*;
import com.liaoin.diving.service.CartService;
import com.liaoin.diving.service.ContentService;
import com.liaoin.diving.service.UserService;
import com.sun.org.apache.regexp.internal.RE;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author : huqingxi
 * @describe :
 * @date 2018/06/07
 */
@RequestMapping("/uc")
@RestController
@Api(tags = "个人中心", value = "个人中心")
public class UserCenterController {

    @Resource
    private UserService userService;
    @Resource
    private CartService cartService;
    @Resource
    private ContentService contentService;


    @PostMapping("/findOrder")
    @ApiOperation("个人中心-查询订单")
    public Result findOrder(HttpSession session){
        // 1. 校验登录状态
        User loginUser = (User)session.getAttribute("loginUser");
        if (Objects.isNull(loginUser)){
            return new Result(300, "请登录", null);
        }
        // 获取最新数据
        loginUser = userService.findOne(loginUser.getId());
        List<Order> orderList = loginUser.getOrderList();
        if (orderList.size() == 0){
            return new Result(300, "暂无数据", null);
        }
        return  new Result(200, "查询成功", orderList);
    }

    @GetMapping("/collect")
    @ApiOperation("个人中心-我的收藏")
    public Result collect(HttpSession session ){
        // 1.判断是否登录
        User loginUser = (User) session.getAttribute("loginUser");

        if (loginUser == null) {
            return new Result(300, "请登录", null);
        }
        loginUser = userService.findOne(loginUser.getId()); // 获取最新数据
        // 查询收藏
        List<Content> collectList = loginUser.getCollectList();

        if (collectList.size() == 0){
            return new Result(200, "暂无数据", null);
        }
        return new Result(200, "查询成功", collectList);
    }


    @GetMapping("/userLv")
    @ApiOperation("个人中心-用户等级")
    public Result userLv(HttpSession session){
        // 1. 校验登录状态
        User loginUser = (User)session.getAttribute("loginUser");
        if (Objects.isNull(loginUser)){
            return new Result(300, "请登录", null);
        }
        // 获取最新数据
        loginUser = userService.findOne(loginUser.getId());
        Level level = loginUser.getLevel();
        return new Result(200, "查询成功", level);

    }
    @GetMapping("/info")
    @ApiOperation("个人中心-个人信息")
    public Result userInfo(HttpSession session){
        User loginUser = (User)session.getAttribute("loginUser");
        if (Objects.isNull(loginUser)){
            return new Result(300, "请登录", null);
        }
        loginUser = userService.findById(loginUser.getId());
        if (Objects.isNull(loginUser)){
            return new Result(300, "暂无数据", null);
        }
        return new Result(200, "查询成功", loginUser);
    }

    @GetMapping("/cart")
    @ApiOperation("个人中心-购物车")
    public Result Cart(HttpSession session){
        User loginUser = (User)session.getAttribute("loginUser");
        if (Objects.isNull(loginUser)){
            return new Result(300, "请登录", null);
        }
        loginUser = userService.findById(loginUser.getId());
        Cart cart = cartService.getCart(loginUser);
        List<CartOption> cartOptionList = cart.getCartOptionList();
        if (cartOptionList.size() == 0){
            return new Result(200, "购物车空空入也", null);
        }
        return new Result(200, "查询成功", cartOptionList);
    }


    /*@GetMapping("/myMsg")
    @ApiOperation("个人中心-我的消息")
    public Result myMsg(HttpSession session){

        return null;
    }*/

    @GetMapping("/downList")
    @ApiOperation("个人中心-下载列表")
    public Result downLoadList(HttpSession session){
        User loginUser = (User)session.getAttribute("loginUser");
        if (Objects.isNull(loginUser)){
            return new Result(300, "请登录", null);
        }
        loginUser = userService.findById(loginUser.getId());
        List<Image> downloVideoList = loginUser.getDownloVideoList();  // 下载列表
        if (Objects.isNull(downloVideoList)){
            return new Result(200, "暂无数据", null);
        }
        return new Result(200, "获取成功", downloVideoList);

    }

    @GetMapping("/upNum")
    @ApiOperation("用户发布内容数量")
    public Result upNum(HttpSession session){
        User loginUser = (User)session.getAttribute("loginUser");
        if (Objects.isNull(loginUser)){
            return new Result(300, "请登录", null);
        }
        Integer upNum = contentService.findUpNum(loginUser.getId());
        return new Result(200, "查询成功", upNum);
    }

    @GetMapping("/getUp")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "start", value = "起始页", required = true),
            @ApiImplicitParam(name = "pageSize", value = "页大小", required = true)
    })
    @ApiOperation("用户发布内容")
    public Result getUpContent(HttpSession session, Integer start, Integer pageSize){
        User loginUser = (User)session.getAttribute("loginUser");
        if (Objects.isNull(loginUser)){
            return new Result(300, "请登录", null);
        }
        PageHelp pageHelp = new PageHelp(start, pageSize, null);
        List<Content> contentList = contentService.findUserUp(pageHelp, loginUser.getId());
        return new Result(200, "查询成功", new PageInfo<>(contentList));
    }
    @GetMapping("/getFans")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "start", value = "起始页", required = true),
            @ApiImplicitParam(name = "pageSize", value = "页大小", required = true)
    })
    @ApiOperation("获取粉丝")
    public Result getFans(HttpSession session, Integer start, Integer pageSize){
        User loginUser = (User)session.getAttribute("loginUser");
        if (Objects.isNull(loginUser)){
            return new Result(300, "请登录", null);
        }
        PageHelp pageHelp = new PageHelp(start, pageSize, null);
        List<User> users = userService.findFans(pageHelp, loginUser.getId());
        List<User> fans = new ArrayList<>();
        for (int i = users.size() ; i > 0; i--){
            User user = users.get(i - 1);
            fans.add(user);
        }
        return new Result(200, "查询成功", new PageInfo<>(fans));
    }

    @GetMapping("/fansNum")
    @ApiOperation("粉丝数量")
    public Result fansNum(HttpSession session){
        User loginUser = (User)session.getAttribute("loginUser");
        if (Objects.isNull(loginUser)){
            return new Result(300, "请登录", null);
        }
        Integer fansNum = userService.findFansNum(loginUser.getId());
        return new Result(200, "查询成功", fansNum);
    }
}
