package com.liaoin.diving.controller;

import com.github.pagehelper.PageInfo;
import com.liaoin.diving.common.PageHelp;
import com.liaoin.diving.common.Result;
import com.liaoin.diving.entity.Badge;
import com.liaoin.diving.entity.User;
import com.liaoin.diving.service.BadgeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/badge")
@Api(value = "徽章模块",tags = "徽章模块")
public class BadgeController {

    @Autowired
    private BadgeService badgeService;


    @PostMapping("/insert")
    @ApiOperation("新增")
    public Result save(@RequestBody Badge badge) {
        badgeService.insert(badge);
        return new Result(200, "新增成功", null);
    }

    @PostMapping("/update")
    @ApiOperation("修改")
    public Result update(@RequestBody Badge badge) {
        badgeService.update(badge);
        return new Result(200, "修改成功", null);
    }

    @PostMapping("/delete")
    @ApiOperation("删除")
    public Result delete(@RequestBody Integer[] ids) {
        badgeService.delete(ids);
        return new Result(200, "删除成功", null);
    }

    @GetMapping("/findOne")
    @ApiImplicitParam(name = "id", value = "主键", dataType = "String", paramType = "query", required = true)
    @ApiOperation("根据主键查询")
    public Result findOne(@RequestParam Integer id) {
        Badge badge = badgeService.findOne(id);
        if (badge == null) {
            return new Result(300, "暂无数据", null);
        }
        return new Result(200, "查询成功", badge);
    }

    @GetMapping("/pageQuery")
    @ApiOperation("条件分页查询")
    public Result pageQuery(@RequestParam Integer start, @RequestParam Integer pageSize){
        PageHelp pageHelp = new PageHelp(start, pageSize, null);
        List<Badge> badgeList = badgeService.findList(pageHelp);
        if (Objects.isNull(badgeList) || badgeList.size() == 0){
            return new Result(200, "什么都没有", null);
        }
        return new Result(200, "查询成功", new PageInfo<>(badgeList));
    }

    @ApiOperation("为用户颁发徽章")
    @GetMapping(value = "releaseBadge")
    public Result releaseBadge(HttpSession session,
                               @ApiParam(value = "徽章ID",required = true) @RequestParam(required = true) Integer badgeId,
                               @ApiParam(value = "完成条件",required = true) @RequestParam(required = true) Integer num){
        // 1.用户是否登录
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) {
            return new Result(300, "请登录", null);
        }
        badgeService.releaseBadge(loginUser.getId(),badgeId,num);
        return new Result(200, "添加成功", null);

    }

    /**
     * 查询当前用户拥有的徽章
     * @return
     */
    @ApiOperation("查询当前用户拥有的徽章")
    @GetMapping(value = "findListWithUser")
    public Result findListWithUser(HttpSession session){
        // 1.用户是否登录
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) {
            return new Result(300, "请登录", null);
        }
        List<Badge> list = badgeService.findListWithUser(loginUser.getId());
        return new Result(200,"查询成功",list);
    }
}
