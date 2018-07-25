package com.liaoin.diving.controller;

import com.liaoin.diving.common.Result;
import com.liaoin.diving.entity.ActivityLabel;
import com.liaoin.diving.entity.User;
import com.liaoin.diving.service.ActivityLableService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apiguardian.api.API;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author : huqingxi
 * @describe :
 * @date 2018/06/07
 */
@RestController
@RequestMapping("/aclabel")
@Api(tags = "活动小标题")
public class ActivityLabelController {
    @Resource
    private ActivityLableService activityLableService;

    @PostMapping("/add")
    @ApiImplicitParam(name = "name", value = "标签名", required = true)
    @ApiOperation("新增")
    public Result add(HttpSession session, String name){
        User loginUser = (User)session.getAttribute("loginUser");
        if (Objects.isNull(loginUser)){
            return new Result(300, "请登录", null);
        }
        ActivityLabel activityLabel = new ActivityLabel();
        activityLabel.setName(name);
        activityLabel.setCreateTime(new Date());
        activityLableService.add(activityLabel);
        return new Result(200, "添加成功", null);
    }
    @PostMapping("/del")
    @ApiOperation("删除")
    public Result del(HttpSession session, Integer id){
        User loginUser = (User)session.getAttribute("loginUser");
        if (Objects.isNull(loginUser)){
            return new Result(300, "请登录", null);
        }
        activityLableService.del(id);
        return new Result(200, "删除成功", null);
    }
    @PostMapping("/update")
    @ApiOperation("修改")
    public Result update(HttpSession session, @RequestBody ActivityLabel activityLabel){
        User loginUser = (User)session.getAttribute("loginUser");
        if (Objects.isNull(loginUser)){
            return new Result(300, "请登录", null);
        }
        activityLableService.update(activityLabel);
        return new Result(200, "修改成功", null);
    }
    @GetMapping("/findById")
    @ApiOperation("根据主键查询")
    @ApiImplicitParam(name = "id", value = "主键")
    public Result findById(HttpSession session, Integer id){
        User loginUser = (User)session.getAttribute("loginUser");
        if (Objects.isNull(loginUser)){
            return new Result(300, "请登录", null);
        }
        ActivityLabel activityLabel = activityLableService.findById(id);
        return new Result(200, "查询成功", activityLabel);
    }

    @GetMapping("/findAll")
    @ApiOperation("查询所有")
    public Result findAll(HttpSession session){
        User loginUser = (User)session.getAttribute("loginUser");
        if (Objects.isNull(loginUser)){
            return new Result(300, "请登录", null);
        }
        List<ActivityLabel> labels = activityLableService.findAll();
        return new Result(200, "查询成功", labels);
    }
}
