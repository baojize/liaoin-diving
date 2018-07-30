package com.liaoin.diving.controller;

import com.github.pagehelper.PageInfo;
import com.liaoin.diving.common.PageHelp;
import com.liaoin.diving.common.Result;
import com.liaoin.diving.entity.Activity;
import com.liaoin.diving.entity.Content;
import com.liaoin.diving.entity.Nav;
import com.liaoin.diving.service.ActivityService;
import com.liaoin.diving.service.ContentService;
import com.liaoin.diving.service.NavService;
import com.liaoin.diving.view.RecoAcView;
import com.liaoin.diving.view.RecoContentView;
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
    @Resource
    private ContentService contentService;
    @Resource
    private ActivityService activityService;

    @GetMapping("/findOneNav")
    @ApiImplicitParam(name = "id", value = "编号", required = true)
    @ApiOperation("查询一条记录[首页导航]")
    public Result findOneNav(Integer id){
        Nav nav = navService.findById(id);
        if (Objects.isNull(nav)){
            return new Result(300, "暂无数据", null);
        }
        return new Result(200, "查询成功", nav);
    }

    @GetMapping("/findAllNav   (按order排序)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "start", value = "起始页 1开始", required = true),
            @ApiImplicitParam(name = "pageSize", value = "页大小", required = true)
    })
    @ApiOperation("查询所有记录[首页导航]")
    public Result findAllNav(Integer start, Integer pageSize){
        PageHelp pageHelp = new PageHelp(start, pageSize, null);
        List<Nav> navList = navService.findAll(pageHelp);
        if (navList.isEmpty()){
            return new Result(300, "暂无数据", null);
        }
        return new Result(200, "查询成功", new PageInfo<>(navList));
    }

    @PostMapping("/addNav")
    @ApiOperation("添加[首页导航]")
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
    @ApiOperation("修改[首页导航]")
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
    @ApiOperation("删除[首页导航]")
    public Result delNav(Integer id){
        try {
            navService.del(id);
            return new Result(200, "删除成功", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(300, "删除失败", null);
        }
    }

/*********************************************************论坛推荐curd******************************************************************/

    public Result findOne(){
        return null;
    }
/*********************************************************推荐装备curd******************************************************************/


/*********************************************************论坛推荐curd******************************************************************/
    @GetMapping("/findOneForump")
    @ApiImplicitParam(name = "id", value = "主键", required = true)
    @ApiOperation("查询一条记录[论坛推荐]")
    public Result findOneForum(Integer id){
        RecoContentView reco =  contentService.getRecommendById(id);
        if (Objects.isNull(reco)){
            return new Result(300, "暂无数据", null);
        }
        return new Result(200, "查询成功", reco);
    }

    @GetMapping("/findAllForum")
    @ApiOperation("查询所有推荐论坛[论坛推荐]")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "start", value = "起始页 1开始", required = true),
            @ApiImplicitParam(name = "pageSize", value = "页大小", required = true)
    })
    public Result findAllForum(Integer start, Integer pageSize){
        PageHelp pageHelp = new PageHelp(start, pageSize, null);
        List<RecoContentView> recommends = contentService.getRecommend(pageHelp);
        if (recommends.isEmpty()){
            return new Result(300, "暂无数据", null);
        }
        return new Result(200, "查询成功", new PageInfo<>(recommends));
    }

    @PostMapping("/setReco")
    @ApiImplicitParam(name = "id", value = "主键", required = true)
    @ApiOperation("设置推荐论坛[论坛推荐]")
    public Result setReco(Integer id){
        Content content = contentService.queryById(id);
        if (Objects.isNull(content)) {
            return new Result(300, "内容不存在", null);
        }
        if (!"2".equals(content.getType())) {
            return new Result(300, "只能设置[动态]为推荐", null);
        }
        if ("1".equals(content.getIsRecommend())) {
            return new Result(300, "该内容已设置推荐", null);
        }
        Integer integer = contentService.setReco(id);
        if (integer != 1){
            return new Result(300,"推荐失败",null);
        }
        return new Result(200,"推荐成功",null);
    }

    @PostMapping("/cancelReco")
    @ApiImplicitParam(name = "id", value = "主键", required = true)
    @ApiOperation("取消推荐 [论坛推荐]")
    public Result cancelReco(Integer id){
        Content content = contentService.queryById(id);
        if (Objects.isNull(content)) {
            return new Result(300, "内容不存在", null);
        }
        if ("1".equals(content.getIsRecommend())) {
             content.setIsRecommend("0");
             contentService.update(content);
        }
        return new Result(200,"取消成功",null);
    }

/*********************************************************活动模块推荐curd******************************************************************/
    @GetMapping("/findOneAc")
    @ApiImplicitParam(name = "id", value = "编号", required = true)
    @ApiOperation("查询一条记录 [活动推荐]")
    public Result findOneAc(Integer id){
        RecoAcView reco= activityService.getOneReco(id);
        if (Objects.isNull(reco)){
            return new Result(300, "暂无数据", null);
        }
        return new Result(200, "查询成功", reco);
    }

    @GetMapping("/findAllRecoAc")
    @ApiOperation("查询所有推荐的活动  [活动推荐]")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "start", value = "起始页 1开始", required = true),
            @ApiImplicitParam(name = "pageSize", value = "页大小", required = true)
    })
    public Result findAllRecoAc(Integer start, Integer pageSize){
        PageHelp pageHelp = new PageHelp(start, pageSize, null);
        List<RecoAcView> activities = activityService.getReco(pageHelp);
        if (activities.isEmpty()){
            return new Result(300, "暂无数据", null);
        }
        return new Result(300, "查询成功", activities);
    }

    @ApiOperation("查询所有活动(推荐的和未推荐的)  [活动推荐]")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "start", value = "起始页 1开始", required = true),
            @ApiImplicitParam(name = "pageSize", value = "页大小", required = true)
    })
    @GetMapping("/findAllAc")
    public Result findAll(Integer start, Integer pageSize){
        PageHelp pageHelp = new PageHelp(start, pageSize, null);
        List<RecoAcView> acViewList = activityService.findAll(pageHelp);
        if (acViewList.isEmpty()){
            return new Result(300, "暂无数据", null);
        }
        return new Result(200, "查询成功", new PageInfo<>(acViewList));
    }

    @PostMapping("/setAc")
    @ApiImplicitParam(name = "id", value = "编号", required = true)
    @ApiOperation("设置推荐活动[活动推荐]")
    public Result setAc(Integer id ){
        Integer back = activityService.setAc(id);
        if (back != 1){
            return new Result(300, "设置失败", null);
        }
        return new Result(200, "设置成功", null);
    }
}
