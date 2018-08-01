package com.liaoin.diving.controller;

import com.github.pagehelper.PageInfo;
import com.liaoin.diving.common.PageHelp;
import com.liaoin.diving.common.Result;
import com.liaoin.diving.entity.Activity;
import com.liaoin.diving.entity.Content;
import com.liaoin.diving.entity.Goods;
import com.liaoin.diving.entity.Nav;
import com.liaoin.diving.service.ActivityService;
import com.liaoin.diving.service.ContentService;
import com.liaoin.diving.service.GoodsService;
import com.liaoin.diving.service.NavService;
import com.liaoin.diving.view.ActivityConditionView;
import com.liaoin.diving.view.EqConditionView;
import com.liaoin.diving.view.RecoAcView;
import com.liaoin.diving.view.RecoContentView;
import io.swagger.annotations.*;
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
@Api(tags = "[后台]门户管理")
public class PortalManagerController {
    @Resource
    private NavService navService;
    @Resource
    private ContentService contentService;
    @Resource
    private ActivityService activityService;
    @Resource
    private GoodsService goodsService;

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

    @GetMapping("/findAllNav")
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



/*********************************************************装备推荐curd******************************************************************/

    @PostMapping("/setEqReco")
    @ApiImplicitParam(name = "id", value = "主键", required = true)
    @ApiOperation("设置推荐装备 [推荐装备] ")
    public Result setEqReco(Integer id){
        //return goodsService;
        try {
            goodsService.setReco(id);
            return new Result(200, "设置成功", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(300, "设置失败", null);
        }
    }

    @GetMapping("/getAllEq")
    @ApiOperation("查询所有装备 ")
    public Result getAllEq(@ApiParam("") Integer start, Integer pageSize){
        return null;
    }

    @PostMapping("/delEqreco")
    @ApiOperation("删除推荐装备 [装备推荐]")
    public Result delEqReco(Integer id){
        Goods goods = goodsService.findOne(id);
        if (Objects.isNull(goods)){
            return new Result(300, "商品不存在", null);
        }
        goods.setIsDelete("1");
        goodsService.update(goods);
        return new Result(200, "删除成功", null);
    }

    @GetMapping("/getEqReco")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "start", value = "起始页 1开始", required = true),
            @ApiImplicitParam(name = "pageSize", value = "页大小", required = true)
    })
    @ApiOperation("查询所有推荐装备 [推荐装备]")
    public Result getEqReco(Integer start, Integer pageSize){
        PageHelp pageHelp = new PageHelp(start, pageSize, null);
        List<Goods> goodsList = goodsService.getReco(pageHelp);
        if (goodsList.isEmpty()){
            return new Result(300, "暂无数据", null);
        }
        return new Result(200, "查询成功", new PageInfo<>(goodsList));
    }

    @PostMapping("/cancelEqReco")
    @ApiImplicitParam(name = "id", value = "主键", required = true)
    @ApiOperation("取消推荐装备 [推荐装备]")
    public Result cancelEqReco(@ApiParam(value = "主键") @RequestParam Integer id){
        try {
            goodsService.cancelReco(id);
            return new Result(200, "取消成功", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(300, "取消失败", null);
        }
    }

    @PostMapping("/eqCondition")
    @ApiOperation("推荐装备条件查询 [推荐装备]")
    public Result eqCondition(@ApiParam(value = "起始页 1开始", required = true) @RequestParam(required = true) Integer start,
                              @ApiParam(value = "页大小", required = true) @RequestParam(required = true) Integer pageSize,
                              @RequestBody EqConditionView condition){
        PageHelp pageHelp = new PageHelp(start, pageSize, null);
        List<Goods> goodsList = goodsService.condition(pageHelp, condition);
        if (goodsList.isEmpty()){
            return new Result(300, "暂无数据", null);
        }
        return new Result(200, "查询成功", new PageInfo<>(goodsList));
    }



    @PostMapping("/setEqHome")
    @ApiOperation("设置装备首页 [装备推荐]")
    public Result  setEqHome(@ApiParam(value = "主键", required = true) @RequestParam Integer id){
        Goods goods = goodsService.findOne(id);
        if ("1".equals(goods.getIsDelete())){
            return new Result(300, "装备不存在", null);
        }
        try {
            goodsService.setHome(id);
            return new Result(200, "设置成功", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(300, "设置失败", null);
        }
    }

    @PostMapping("/cancelEqHome")
    @ApiOperation("取消装备首页 [装备推荐]")
    public Result  cancelEqHome(@ApiParam(value = "主键") @RequestParam Integer id){
        Goods goods = goodsService.findOne(id);
        if ("1".equals(goods.getIsDelete())){
            return new Result(300, "装备不存在", null);
        }
        if ("1".equals(goods.getIsHome())){
            goods.setIsHome("0");
            goodsService.update(goods);
        }
        return new Result(200, "取消成功", null);
    }

    @GetMapping("/getEqHome")
    @ApiOperation("获取首页装备 [装备推荐]")
    public Result getEqHome(@ApiParam(value = "起始页 1开始") @RequestParam Integer start,
                            @ApiParam(value = "页大小") @RequestParam Integer pageSize){
        PageHelp pageHelp = new PageHelp(start, pageSize, null);
        List<Goods> goodsList = goodsService.getEqHome(pageHelp);
        if (goodsList.isEmpty()){
            return new Result(300, "暂无数据", null);
        }
        return new Result(200, "查询成功", new PageInfo<>(goodsList));
    }


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

    @PostMapping("/setConReco")
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

    @PostMapping("/cancelConReco")
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

    @PostMapping("/setAcReco")
    @ApiImplicitParam(name = "id", value = "编号", required = true)
    @ApiOperation("设置推荐活动[活动推荐]")
    public Result setAc(Integer id ){

        Integer back = activityService.setAc(id);
        if (back != 1){
            return new Result(300, "设置失败", null);
        }
        return new Result(200, "设置成功", null);
    }

    @PostMapping("/acCondition")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "start", value = "起始页 1开始", required = true),
            @ApiImplicitParam(name = "pageSize", value = "页大小", required = true)
    })
    @ApiOperation("条件查询[活动推荐]")
    public Result condition(Integer start, Integer pageSize,@RequestBody ActivityConditionView activity){
        PageHelp pageHelp = new PageHelp(start, pageSize, null);
        List<Activity> activities = activityService.condition(pageHelp, activity);
        if (activities.isEmpty()){
            return new Result(300, "暂无数据", null);
        }
        return new Result(200, "查询成功", new PageInfo<>(activities));
    }

    @PostMapping("/cancelAcReco")
    @ApiOperation("取消活动推荐[活动推荐]")
    @ApiImplicitParam(name = "id", value = "主键", required = true)
    public Result cancelAcReco(Integer id){
        RecoAcView reco = activityService.getOneReco(id);
        if (Objects.isNull(reco)){
            return new Result(300, "该活动不是推荐活动,无法取消", null);
        }
        activityService.cancelReco(id);
        return new Result(200, "操作成功", null);
    }

    @PostMapping("/setAcHome")
    @ApiOperation("设置/取消活动首页 [活动推荐]")
    public Result setAcHome(@ApiParam(value = "主键") @RequestParam Integer id){
        Activity activity = activityService.findById(id);
        if (Objects.isNull(activity)){
            return new Result(300, "活动不存在", null);
        }
        if ("1".equals(activity.getIsHome())){
            activity.setIsHome("0");
            activityService.update(activity);
            return new Result(200, "取消成功", null);
        }else {
            activity.setIsHome("1");
            activityService.update(activity);
            return new Result(200, "设置成功", null);
        }
    }

    @GetMapping("/getAcHome")
    @ApiOperation("获取活动首页 [活动推荐]")
    public Result getAcHome(@ApiParam(value = "起始页 1开始") @RequestParam Integer start,
                            @ApiParam(value = "页大小") @RequestParam Integer pageSize){
        PageHelp pageHelp = new PageHelp(start, pageSize, null);
        List<Activity> activityList = activityService.getAcHome(pageHelp);
        if (activityList.isEmpty()){
            return new Result(300, "暂无数据", null);
        }
        return new Result(200, "查询成功", new PageInfo<>(activityList));
    }


}
