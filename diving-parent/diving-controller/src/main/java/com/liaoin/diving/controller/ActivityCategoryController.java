package com.liaoin.diving.controller;

import com.liaoin.diving.common.PageBean;
import com.liaoin.diving.common.Result;
import com.liaoin.diving.entity.ActivityCategory;
import com.liaoin.diving.service.ActivityCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 *  date : 7.25  17.30  1121 11 2 2 3
 */
@RestController
@RequestMapping("/activityCategory")
@Api(tags = "活动分类")
public class ActivityCategoryController {


    @Resource
    private ActivityCategoryService activityCategoryService;

    @PostMapping("/insert")
    @ApiOperation("新增")
    public Result save(@RequestBody ActivityCategory activityCategory) {
        activityCategoryService.insert(activityCategory);
        return new Result(200, "新增成功", null);
    }

    @PostMapping("/update")
    @ApiOperation("修改")
    public Result update(@RequestBody ActivityCategory activityCategory) {
        activityCategoryService.update(activityCategory);
        return new Result(200, "修改成功", null);
    }

    @PostMapping("/delete")
    @ApiOperation("删除")
    public Result delete(@RequestBody Integer[] ids) {
        activityCategoryService.delete(ids);
        return new Result(200, "删除成功", null);
    }

    @GetMapping("/findOne")
    @ApiImplicitParam(name = "id", value = "主键", dataType = "String", paramType = "query", required = true)
    @ApiOperation("根据主键查询")
    public Result findOne(@RequestParam Integer id) {
        ActivityCategory activityCategory = activityCategoryService.findOne(id);
        if (activityCategory == null) {
            return new Result(300, "暂无数据", null);
        }
        return new Result(200, "查询成功", activityCategory);
    }

    @PostMapping("/pageQuery")
    @ApiOperation("条件分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页码，第一页从0开始", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "size", value = "分页大小", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "sort", value = "排序，如：id,desc，可以添加多个排序条件", dataType = "String", paramType = "query", required = false)
    })
    public Result pageQuery(@PageableDefault Pageable pageable, @RequestBody ActivityCategory activityCategory) {
        PageBean<ActivityCategory> pageBean = activityCategoryService.pageQuery(pageable, activityCategory);
        if (pageBean == null) {
            return new Result(300, "暂无数据", null);
        }
        return new Result(200, "查询成功", pageBean);
    }
}

