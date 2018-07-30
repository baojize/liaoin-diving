package com.liaoin.diving.controller;

import com.github.pagehelper.PageInfo;
import com.liaoin.diving.common.PageHelp;
import com.liaoin.diving.common.Result;
import com.liaoin.diving.entity.Nav;
import com.liaoin.diving.service.NavService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @author : huqingxi
 * @describe :
 * @date 2018/06/07
 */
@RestController
@RequestMapping("/manager/nav")
@Api(tags = "首页导航模块")
public class NavController {
    @Resource
    private NavService navService;

    @GetMapping("/findOne")
    @ApiImplicitParam(name = "id", value = "编号", required = true)
    @ApiOperation("查询一条记录")
    public Result findOne(Integer id){
        Nav nav = navService.findById(id);
        if (Objects.isNull(nav)){
            return new Result(300, "暂无数据", null);
        }
        return new Result(200, "查询成功", nav);
    }

    @GetMapping("/findAll   (按order排序)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "start", value = "起始页 0开始", required = true),
            @ApiImplicitParam(name = "pageSize", value = "页大小", required = true)
    })
    @ApiOperation("查询所有记录")
    public Result findAll(Integer start, Integer pageSize){
        PageHelp pageHelp = new PageHelp(start, pageSize, null);
        List<Nav> navList = navService.findAll(pageHelp);
        if (navList.isEmpty()){
            return new Result(300, "暂无数据", null);
        }
        return new Result(200, "查询成功", new PageInfo<>(navList));
    }

    @PostMapping("/add")
    @ApiOperation("添加")
    public Result add(@RequestBody Nav nav){
        try {
            navService.add(nav);
            return new Result(200, "添加成功", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(300, "添加失败", null);
        }
    }

    @PostMapping("/update")
    @ApiOperation("修改")
    public Result update(@RequestBody Nav nav){
        try {
            navService.update(nav);
            return new Result(200, "修改成功", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(300, "修改失败", null);
        }
    }

    @PostMapping("/del")
    @ApiOperation("删除")
    public Result del(Integer id){
        try {
            navService.del(id);
            return new Result(200, "删除成功", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(300, "删除失败", null);
        }
    }
}
