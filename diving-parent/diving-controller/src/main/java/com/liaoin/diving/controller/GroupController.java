package com.liaoin.diving.controller;

import com.github.pagehelper.PageInfo;
import com.liaoin.diving.common.PageBean;
import com.liaoin.diving.common.PageHelp;
import com.liaoin.diving.common.Result;
import com.liaoin.diving.entity.Content;
import com.liaoin.diving.entity.Group;
import com.liaoin.diving.entity.User;
import com.liaoin.diving.entity.Ware;
import com.liaoin.diving.service.GroupService;
import com.liaoin.diving.view.ContentView;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/group")
@Api(tags = "俱乐部模块",value = "俱乐部模块")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @PostMapping("/insert")
    @ApiOperation("新增")
    public Result save(@RequestBody Group group) {
        groupService.insert(group);
        return new Result(200, "新增成功", null);
    }

    @PostMapping("/update")
    @ApiOperation("修改")
    public Result update(@RequestBody Group group) {
        groupService.update(group);
        return new Result(200, "修改成功", null);
    }

    @PostMapping("/delete")
    @ApiOperation("删除")
    public Result delete(@RequestBody Integer[] ids) {
        groupService.delete(ids);
        return new Result(200, "删除成功", null);
    }

    @GetMapping("/findOne")
    @ApiImplicitParam(name = "id", value = "主键", dataType = "String", paramType = "query", required = true)
    @ApiOperation("根据主键查询")
    public Result findOne(@RequestParam Integer id) {
        Group group = groupService.findOne(id);
        if (group == null) {
            return new Result(300, "暂无数据", null);
        }
        return new Result(200, "查询成功", group);
    }

    @GetMapping("/findAll")
    @ApiOperation("查询所有俱乐部")
    public Result findAll(HttpSession session, Integer start, Integer pageSize){
        User loginUser = (User)session.getAttribute("loginUser");
        if (Objects.isNull(loginUser)){
            return new Result(300, "请登录", null);
        }
        PageHelp pageHelp = new PageHelp(start, pageSize, null);
        List<Group> groupList = groupService.findList(pageHelp);
        if (Objects.isNull(groupList) || groupList.size() == 0){
            return new Result(200, "什么都没有", null);
        }
        return new Result(200, "查询成功", new PageInfo<>(groupList));
    }

    /*******************************************    app     ********************************************/

    /**
     * 查询俱乐部详情
     * @param id 俱乐部ID
     * @return
     */
    @GetMapping("mobile/findOne")
    @ApiOperation("前端查询俱乐部详情")
    public Result mobileFindOne(@ApiParam(value = "俱乐部ID",required = true) @RequestParam Integer id){
        Group group = groupService.mobileFindOne(id);
        if (group == null) {
            return new Result(300, "暂无数据", null);
        }
        return new Result(200, "查询成功", group);
    }

    @GetMapping("mobile/findAll")
    @ApiOperation("前端查询所有内容")
    public Result mobileFindAll(HttpSession session, Integer start, Integer pageSize,
                                @ApiParam(value = "俱乐部ID",required = true) @RequestParam Integer id){
        User loginUser = (User)session.getAttribute("loginUser");
        if (Objects.isNull(loginUser)){
            return new Result(300, "请登录", null);
        }
        PageHelp pageHelp = new PageHelp(start, pageSize, id);
        List<ContentView> contents = groupService.mobileFindList(pageHelp);
        if (Objects.isNull(contents) || contents.size() == 0){
            return new Result(200, "什么都没有", null);
        }
        return new Result(200, "查询成功", new PageInfo<>(contents));
    }





}
