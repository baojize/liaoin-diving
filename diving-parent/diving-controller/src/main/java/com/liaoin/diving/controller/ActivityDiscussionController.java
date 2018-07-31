package com.liaoin.diving.controller;

import com.liaoin.diving.common.PageBean;
import com.liaoin.diving.common.Result;
import com.liaoin.diving.entity.ActivityDiscussion;
import com.liaoin.diving.entity.User;
import com.liaoin.diving.service.ActivityDiscussionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/activityDiscussion")
@Api(tags = "活动评论")
public class ActivityDiscussionController {

    @Resource
    private ActivityDiscussionService activityDiscussionService;

    @Resource
    private HttpSession session;

    @PostMapping("/insert")
    @ApiOperation("新增评论")
    public Result save(@RequestBody ActivityDiscussion activityDiscussion) {
        // 1.用户是否登录
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) {
            return new Result(300, "请登录", null);
        }
        //设置评论的人
        activityDiscussion.setCreateUser(loginUser);
        activityDiscussionService.insert(activityDiscussion);
        return new Result(200, "新增成功", null);
    }

    @PostMapping("/update")
    @ApiOperation("修改")
    public Result update(@RequestBody ActivityDiscussion activityDiscussion) {
        activityDiscussionService.update(activityDiscussion);
        return new Result(200, "修改成功", null);
    }

    @PostMapping("/delete")
    @ApiOperation("删除")
    public Result delete(@RequestBody Integer[] ids) {
        activityDiscussionService.delete(ids);
        return new Result(200, "删除成功", null);
    }

    @GetMapping("/findOne")
    @ApiImplicitParam(name = "id", value = "主键", dataType = "String", paramType = "query", required = true)
    @ApiOperation("根据主键查询")
    public Result findOne(@RequestParam Integer id) {
        ActivityDiscussion activityDiscussion = activityDiscussionService.findOne(id);
        if (activityDiscussion == null) {
            return new Result(300, "暂无数据", null);
        }
        return new Result(200, "查询成功", activityDiscussion);
    }

    @PostMapping("/pageQuery")
    @ApiOperation("条件分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页码，第一页从0开始", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "size", value = "分页大小", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "sort", value = "排序，如：id,desc，可以添加多个排序条件", dataType = "String", paramType = "query", required = false)
    })
    public Result pageQuery(@PageableDefault Pageable pageable, @RequestBody ActivityDiscussion activityDiscussion) {
        PageBean<ActivityDiscussion> pageBean = activityDiscussionService.pageQuery(pageable, activityDiscussion);
        if (pageBean == null) {
            return new Result(300, "暂无数据", null);
        }
        return new Result(200, "查询成功", pageBean);
    }
}

