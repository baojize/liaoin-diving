package com.liaoin.diving.controller;

import com.liaoin.diving.common.Result;
import com.liaoin.diving.entity.Group;
import com.liaoin.diving.entity.User;
import com.liaoin.diving.service.FocusService;
import com.liaoin.diving.service.GroupService;
import com.liaoin.diving.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@RestController
@Api(tags = "关注模块",value = "关注模块")
public class FocusController {
    @Resource
    private UserService userService;
    @Resource
    private FocusService focusService;
    @Resource
    private GroupService groupService;

    @GetMapping("/focus")
    @ApiImplicitParam(name = "userId", value = "主键", dataType = "String", paramType = "query", required = true)
    @ApiOperation("关注用户或取消关注用户")
    public Result focus(@RequestParam Integer userId, HttpSession session) {
        // 1.获取当前登录的用户
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) {
            return new Result(300, "请登录", null);
        }
        loginUser = userService.findOne(loginUser.getId());//获取最新的用户信息
        // 2.获取被关注用户
        User focusUser = userService.findOne(userId);  // 查询被关注用户是否存在
        if (focusUser == null) {
            return new Result(300, "用户不存在", null);
        }
        // 3.更新关注和粉丝列表
        focusService.focus(loginUser, focusUser);
        return new Result(200, "关注或取消成功", null);
    }

    @GetMapping("/groupFocus")
    @ApiOperation("关注俱乐部或取消关注俱乐部")
    public Result groupFocus(@ApiParam(value = "俱乐部ID",required = true) @RequestParam(required = true) Integer groupId, HttpSession session){
        // 1.获取当前登录的用户
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) {
            return new Result(300, "请登录", null);
        }
        loginUser = userService.findOne(loginUser.getId());//获取最新的用户信息
        // 2.获取被关注用户
        Group group = groupService.findOne(groupId);// 查询被关注俱乐部是否存在
        if (group == null) {
            return new Result(300, "俱乐部不存在", null);
        }
        // 3.更新关注和粉丝列表
        focusService.groupFocus(loginUser, groupId);
        return new Result(200, "关注或取消成功", null);
    }
}
