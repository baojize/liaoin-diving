package com.liaoin.diving.controller;

import com.liaoin.diving.common.Result;
import com.liaoin.diving.entity.manager.Admin;
import com.liaoin.diving.service.UserManagerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Objects;

/**
 * @author : huqingxi
 * @describe :
 * @date 2018/06/07
 */
@RestController
@RequestMapping("/manager/user")
@Api(tags = "后台用户模块")
public class UserManagerController {
    @Resource
    private UserManagerService userManagerService;

    @PostMapping("/login")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "account", value = "账号", required = true),
            @ApiImplicitParam(name = "password", value = "密码", required = true)
    })
    @ApiOperation("后台管理员登录")
    public Result login(HttpSession session,String account, String password){
        Admin admin = userManagerService.login(account, password);
        if (Objects.isNull(admin)){
            return new Result(300, "账号或密码错误", null);
        }
        session.setAttribute("admin",admin);
        return new Result(200, "登录成功", null);
    }

    @PostMapping("/add")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "account", value = "账号 copy:778439", required = true),
            @ApiImplicitParam(name = "password", value = "密码 copy:123123", required = true)
    })
    @ApiOperation("添加管理员")
    public Result add(String account, String password){
        try {
            userManagerService.add(account, password);
            return new Result(200, "添加成功", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(300, "添加失败", null);
        }
    }

    @PostMapping("/del")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "编号", required = true)
    })
    @ApiOperation("删除管理员")
    public Result del(Integer id){
        Integer status = userManagerService.del(id);
        if (status == 1){
            return new Result(200, "删除成功", null);
        }else {
            return new Result(300, "删除失败", null);
        }
    }
}
