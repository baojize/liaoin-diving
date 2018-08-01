package com.liaoin.diving.controller;

import com.github.pagehelper.PageInfo;
import com.liaoin.diving.common.PageHelp;
import com.liaoin.diving.common.Result;
import com.liaoin.diving.entity.Activity;
import com.liaoin.diving.entity.Banner;
import com.liaoin.diving.entity.Content;
import com.liaoin.diving.entity.User;
import com.liaoin.diving.service.ActivityService;
import com.liaoin.diving.service.BannerService;
import com.liaoin.diving.service.ContentService;
import com.liaoin.diving.view.BannerView;
import com.sun.org.apache.regexp.internal.RE;
import io.swagger.annotations.*;
import org.hibernate.annotations.Parameter;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author : huqingxi
 * @describe : 轮播图控制器
 * @date 2018/06/07
 */
@RestController
@RequestMapping("/manager/banner")
@Api(tags = "[后台]轮播图",value = "轮播图")
public class BannerController {
    @Resource
    private ContentService contentService;
    @Resource
    private BannerService bannerService;
    @Resource
    private ActivityService activityService;

    @PostMapping("/contentBanner")
    @ApiOperation("设置内容轮播图")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "contentId", value = "内容id", required = true),
            @ApiImplicitParam(name = "isHidden", value = "是否显示 0:不显示(默认), 1:显示", required = true)
    })
    public Result contentBanner(Integer contentId, Integer isHidden){
        Content content = null;
        if (!Objects.isNull(contentId)){
            content = contentService.findOther(contentId);
        }
        if (Objects.isNull(content)){
            return new Result(300, "内容不存在", null);
        }
        if (!"2".equals(content.getType())){
            return new Result(300, "只能设置[动态][活动]为轮播图", null);
        }
        Banner dbBanner = bannerService.findByContentId(content.getId());
        if (!Objects.isNull(dbBanner)){
            return new Result(300, "该动态已设置轮播图", null);
        }
        Banner banner = new Banner();
        banner.setContent(content);
        banner.setCreateTime(new Date());
        banner.setTitle(content.getTitle());
        banner.setType(1);
        if (Objects.isNull(isHidden)){
            isHidden = 0;
        }
        banner.setIsHidden(isHidden); // 轮播图显示状态
        bannerService.add(banner);
        return new Result(200, "设置成功", null);
    }


    @PostMapping("/activityBanner")
    @ApiOperation("设置活动轮播图")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "activityId", value = "活动id", required = true),
            @ApiImplicitParam(name = "isHidden", value = "是否显示 0:不显示(默认), 1:显示", required = true)
    })
    public Result activityBanner(Integer activityId, Integer isHidden){
        Activity activity = activityService.findOne(activityId);
        if (Objects.isNull(activity)){
            return new Result(300, "活动不存在", null);
        }
        if (!"0".equals(activity.getIsDelete())){
            return new Result(300, "活动不存在", null);
        }
        Banner dbActivity = bannerService.findByActivityId(activity.getId());
        if (!Objects.isNull(dbActivity)){
            return new Result(300, "该活动已设置轮播图", null);
        }
        Banner banner = new Banner();
        banner.setActivityId(activity.getId());
        banner.setTitle(activity.getName());
        banner.setCreateTime(new Date());
        banner.setType(2);
        if(Objects.isNull(isHidden)){
            isHidden = 0;
        }
        banner.setIsHidden(isHidden); // 轮播图显示状态
        bannerService.add(banner);
        return new Result(200, "设置成功", null);
    }


    @GetMapping("/getAll")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "start", value = "起始页"),
            @ApiImplicitParam(name = "pageSize", value = "页大小")
    })
    @ApiOperation("获取轮播图")
    public Result findAll(Integer start, Integer pageSize){

        /*List<BannerView> activitys = bannerService.findToActivity();*/
        PageHelp pageHelp = new PageHelp(start, pageSize, null);
        List<BannerView> bannerViews = bannerService.findAllBanner(pageHelp);
        //List<BannerView> bannerList = contentService.findAllBanner(pageHelp);
        return new Result(200, "查询成功", new PageInfo<>(bannerViews));
    }


    @GetMapping("/delBanner")
    @ApiOperation("删除轮播图 (轮播图id)") // 物理删
    public Result delBanner(Integer id){
        try {
            bannerService.delBanner(id);
            return new Result(200, "删除成功", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(300, "删除失败", null);
        }
    }

    @PostMapping("/updateBanner")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "bannerId", required = true),
            @ApiImplicitParam(name = "isHidden", value = "是否显示", required = true)
    })
    @ApiOperation("修改轮播图显示状态")
    public Result updateBanner(Integer id, Integer isHidden){
        if (isHidden == 1){
            Banner banner = bannerService.findBannerById(id);
            if (Objects.isNull(banner)){
                return new Result(300, "轮播图不存在", null);
            }
            if (banner.getIsHidden() == 1 ){
                return new Result(300, "该轮播图已为显示状态", null);
            }
        }
        bannerService.updateBanner(id, isHidden);
        return new Result(200, "设置成功", null);
    }

    @PostMapping("/condition")
    @ApiOperation("条件模糊查询  提供查询字段 title type isHidden")
    public Result condition(@ApiParam(value = "起始页 1开始", required = true) @RequestParam Integer start,
                            @ApiParam(value = "页大小", required = true) @RequestParam Integer pageSize,
                            @RequestBody BannerView bannerView){
        PageHelp pageHelp = new PageHelp(start, pageSize, null);
        List<BannerView> bannerViewList = bannerService.condition(bannerView, pageHelp);
        if (bannerViewList.isEmpty()){
            return new Result(300, "暂无数据", null);
        }
        return new Result(200, "查询成功", new PageInfo<>(bannerViewList));
    }

}
