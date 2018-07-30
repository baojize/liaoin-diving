package com.liaoin.diving.controller;

import com.liaoin.diving.common.Result;
import com.liaoin.diving.entity.manager.Admin;
import com.liaoin.diving.service.UserManagerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;

/**
 * @author : huqingxi
 * @describe :
 * @date 2018/06/07
 */
@RestController
@RequestMapping("/manager/user")
@Api(tags = "[后台]用户模块")
public class UserManagerController {
    @Resource
    private UserManagerService userManagerService;

    @PostMapping("/login")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "account", value = "账号 copy:778439", required = true),
            @ApiImplicitParam(name = "password", value = "密码 copy:123123", required = true)
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
            @ApiImplicitParam(name = "account", value = "账号", required = true),
            @ApiImplicitParam(name = "password", value = "密码 ", required = true)
    })
    @ApiOperation("添加管理员")
    public Result add(String account, String password){
        Integer check = userManagerService.checkAccount(account);
        if (check != 0){
            return new Result(300, "账号冲突", null);
        }
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
        }else if (status == 0){
            return new Result(300, "内容不存在", null);
        }else {
            return new Result(300, "删除失败", null);
        }
    }
    @PostMapping("/update")
    @ApiOperation("修改")
    public Result update(@RequestBody Admin admin){
        Integer status = userManagerService.update(admin);
        if (status == 1){
           return new Result(200, "更新成功", null);
        }
        return new Result(300, "更新失败", null);
    }
    @GetMapping("/findOne")
    @ApiImplicitParam(name = "id", value = "编号", required = true)
    @ApiOperation("查询一条记录")
    public Result findOne(Integer id){
        Admin admin = userManagerService.findById(id);
        if (Objects.isNull(admin)){
            return new Result(300, "该管理员不存在", null);
        }
        return new Result(200, "查询成功", admin);
    }
    @GetMapping("/findAll")
    @ApiOperation("查询所有记录")
    public Result findAll(){
        List<Admin> adminList = userManagerService.findAll();
        if (adminList.isEmpty()){
            return new Result(300, "暂无数据", null);
        }
        return new Result(200, "查询成功", adminList);
    }
}
