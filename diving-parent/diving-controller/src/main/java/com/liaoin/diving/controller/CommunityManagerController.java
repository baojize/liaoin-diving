package com.liaoin.diving.controller;

import com.github.pagehelper.PageInfo;
import com.liaoin.diving.common.PageHelp;
import com.liaoin.diving.common.Result;
import com.liaoin.diving.entity.CommunityNav;
import com.liaoin.diving.service.CommunityService;
import com.liaoin.diving.service.UserService;
import com.sun.org.apache.regexp.internal.RE;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.models.auth.In;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author : huqingxi
 * @describe :
 * @date 2018/06/07
 */
@Data
@RestController
@RequestMapping("/manager/community")
@Api(tags = "[后台]社区管理")
public class CommunityManagerController {
    @Resource
    private CommunityService communityService;

    @PostMapping("/addNav")
    @ApiOperation("添加社区导航")
    public Result addCommunityNav(@ApiParam(value = "名称", required = true) @RequestParam String name,
                                  @ApiParam(value = "排序", required = true) @RequestParam Integer order){
        CommunityNav nav = new CommunityNav();
        nav.setIsHidden(0);
        nav.setCreateTime(new Date());
        nav.setName(name);
        nav.setOrder(order);
        if (communityService.addCommunityNav(nav) != 1){
            return new Result(300, "添加失败", null);
        }
        return new Result(200, "添加成功", null);
    }
    @PostMapping("/delNav")
    @ApiOperation("删除社区导航") // 物理删
    public Result delCommunityNav(Integer id){
        if (communityService.delCommunityNav(id) != 1){
            return new Result(300, "删除失败", null);
        }
        return new Result(200, "删除成功", null);
    }

    @PostMapping("/updateNav")
    @ApiOperation("修改社区导航")
    public Result updateCommunityNav(@RequestBody CommunityNav nav){
        if (Objects.isNull(nav)){
            return new Result(300, "未传入任何参数", null);
        }
        if (communityService.updateCommunityNav(nav) != 1){
            return new Result(300, "修改失败", null);
        }
        return new Result(200, "修改成功", null);
    }

    @PostMapping("/conditionNav")
    @ApiOperation("导航栏条件查询")
    public Result conditionNav(@ApiParam(value = "起始页 1开始", required = true) @RequestParam Integer start,
                               @ApiParam(value = "页大小", required = true) @RequestParam Integer pageSize,
                               @RequestBody CommunityNav nav){
        PageHelp pageHelp = new PageHelp(start, pageSize, null);
        List<CommunityNav> navList = communityService.conditionNav(pageHelp, nav);
        if (navList.isEmpty()){
            return new Result(300, "暂无数据", null);
        }
        return new Result(200, "查询成功", new PageInfo<>(navList));
    }
}
