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
@RequestMapping("/manager/portal/")
@Api(tags = "后台门户管理")
public class PortalManagerController {
    @Resource
    private NavService navService;

    @GetMapping("/findOneNav")
    @ApiImplicitParam(name = "id", value = "编号", required = true)
    @ApiOperation("查询一条记录")
    public Result findOneNav(Integer id){
        Nav nav = navService.findById(id);
        if (Objects.isNull(nav)){
            return new Result(300, "暂无数据", null);
        }
        return new Result(200, "查询成功", nav);
    }

    @GetMapping("/findAllNav   (按order排序)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "start", value = "起始页 0开始", required = true),
            @ApiImplicitParam(name = "pageSize", value = "页大小", required = true)
    })
    @ApiOperation("查询所有记录")
    public Result findAllNav(Integer start, Integer pageSize){
        PageHelp pageHelp = new PageHelp(start, pageSize, null);
        List<Nav> navList = navService.findAll(pageHelp);
        if (navList.isEmpty()){
            return new Result(300, "暂无数据", null);
        }
        return new Result(200, "查询成功", new PageInfo<>(navList));
    }

    @PostMapping("/addNav")
    @ApiOperation("添加")
    public Result addNav(@RequestBody Nav nav){
        try {
            navService.add(nav);
            return new Result(200, "添加成功", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(300, "添加失败", null);
        }
    }

    @PostMapping("/updateNav")
    @ApiOperation("修改")
    public Result updateNav(@RequestBody Nav nav){
        try {
            navService.update(nav);
            return new Result(200, "修改成功", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(300, "修改失败", null);
        }
    }

    @PostMapping("/delNav")
    @ApiOperation("删除")
    public Result delNav(Integer id){
        try {
            navService.del(id);
            return new Result(200, "删除成功", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(300, "删除失败", null);
        }
    }

    @GetMapping("/findOneActivity")
    public Result findOneActivity(Integer id){
        return null;
    }

}
