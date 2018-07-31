package com.liaoin.diving.controller;

import com.github.pagehelper.PageInfo;
import com.liaoin.diving.common.PageBean;
import com.liaoin.diving.common.PageHelp;
import com.liaoin.diving.common.Result;
import com.liaoin.diving.entity.Activity;
import com.liaoin.diving.entity.ActivityProducts;
import com.liaoin.diving.entity.Banner;
import com.liaoin.diving.entity.User;
import com.liaoin.diving.service.ActivityLableService;
import com.liaoin.diving.service.ActivityProductsService;
import com.liaoin.diving.service.ActivityService;
import com.liaoin.diving.service.BannerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/activity")
@Api(tags = "活动模块")
public class ActivityController {

    @Resource
    private ActivityService activityService;
    @Resource
    private ActivityLableService activityLableService;
    @Resource
    private BannerService bannerService;
    /**
     * 活动产品
     */
    @Resource
    private ActivityProductsService activityProductsService;

    @PostMapping("/insert")
    @ApiOperation("新增")
    public Result save(@RequestBody Activity activity) {
        activityService.insert(activity);
        return new Result(200, "新增成功", null);
    }

    @PostMapping("/update")
    @ApiOperation("修改")
    public Result update(@RequestBody Activity activity) {
       Activity fActivity =  activityService.findOne(activity.getId());

        activityService.update(activity);
        return new Result(200, "修改成功", null);
    }

    @PostMapping("/delete")
    @ApiOperation("删除")
    public Result delete(@RequestBody Integer[] ids) {
        activityService.delete(ids);
        return new Result(200, "删除成功", null);
    }

    @GetMapping("/findOne")
    @ApiImplicitParam(name = "id", value = "主键", dataType = "String", paramType = "query", required = true)
    @ApiOperation("根据主键查询")
    public Result findOne(@RequestParam Integer id) {
        Activity activity = activityService.findOne(id);
        if (activity == null) {
            return new Result(300, "暂无数据", null);
        }
        return new Result(200, "查询成功", activity);
    }

    @PostMapping("/pageQuery")
    @ApiOperation("条件分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页码，第一页从0开始", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "size", value = "分页大小", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "sort", value = "排序，如：id,desc，可以添加多个排序条件", dataType = "String", paramType = "query", required = false)
    })
    public Result pageQuery(@PageableDefault Pageable pageable, @RequestBody Activity activity) {
        PageBean<Activity> pageBean = activityService.pageQuery(pageable, activity);
        List<Activity> activities = pageBean.getRows();
        for (Activity innerAc : activities){
            innerAc.setActivityLabels(activityLableService.findByActivityId(innerAc.getId()));
        }

        if (pageBean == null) {
            return new Result(300, "暂无数据", null);
        }
        return new Result(200, "查询成功", pageBean);
    }

    /*@PostMapping("/delProducts")
    public Result delProducts(Integer id,@RequestBody Integer[] ids){
        Activity activity = activityService.findOne(id);
        List<ActivityProducts> activityProductsList = activity.getActivityProductsList();
        //解除关联
        for (Integer pid : ids){
            for (ActivityProducts activityProducts : activityProductsList){
                if (pid == activityProducts.getId()){
                    activityProducts.setActivity(null);
                }
            }
        }
        activityService.update(activity);
        return new Result(200,"删除成功",null);
    }*/
    @PostMapping("/recommend")
    @ApiOperation("获取活动推荐")
    //@ApiImplicitParam(name = "type",value = "1首页 其他非首页",dataType = "Integer",paramType = "query",required = true)
    public Result recommend(){
        //List<Activity> reco = activityService.recommend(type);
        List<Activity> reco = activityService.findRecommend();
        if (reco.size() > 0) {
            return new Result(200, "查询成功", reco);
        }
        return new Result(200,"无数据",null);
    }

    @PostMapping("/setBroadcast")
    @ApiOperation("设置轮播图")
    public Result setBroadcast(HttpSession session, Integer id){
        User loginUser = (User)session.getAttribute("loginUser");
        if (Objects.isNull(loginUser)){
           return new Result(300, "请登录", null);
        }
        Activity activity = activityService.findOne(id);
        if (Objects.isNull(activity)){
            return new Result(300, "该活动不存在", null);
        }
        Banner banner = new Banner();
        banner.setCreateTime(new Date());
        banner.setActivityId(activity.getId());
        banner.setType("活动");
        bannerService.add(banner);
        return new Result(200, "设置成功", null);
    }

    @GetMapping("/getOther")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "start", value = "起始页 0 开始"),
            @ApiImplicitParam(name = "pageSize", value = "页大小"),
            @ApiImplicitParam(name = "id", value = "活动类别id"),
    })
    @ApiOperation("根据活动类别获得活动")
    public Result getOther(Integer start, Integer pageSize, Integer id){
        PageHelp pageHelp = new PageHelp(start, pageSize, null);
        List<Activity> activities = activityService.findByCategory(pageHelp, id);
        if (activities.size() > 0){
            return new Result(200, "查询成功", new PageInfo<>(activities));
        }
        return new Result(200, "暂无数据", null);
    }
}

