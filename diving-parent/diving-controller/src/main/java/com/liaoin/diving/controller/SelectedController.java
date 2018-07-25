package com.liaoin.diving.controller;

import com.github.pagehelper.PageInfo;
import com.liaoin.diving.common.PageHelp;
import com.liaoin.diving.common.Result;
import com.liaoin.diving.entity.Content;
import com.liaoin.diving.entity.Theme;
import com.liaoin.diving.service.ContentService;
import com.liaoin.diving.service.ThemeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author : huqingxi
 * @describe :
 * @date 2018/06/07
 */
@RestController
@Api(tags = "干货模块")
@RequestMapping("/selected")
public class SelectedController {
    @Resource
    private ContentService contentService;
    @Resource
    private ThemeService themeService;

    @GetMapping("/get")
    @ApiOperation("获取指定主题下的内容")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "start", value = "开始页", required = true),
            @ApiImplicitParam(name = "pageSize", value = "页代销", required = true),
            @ApiImplicitParam(name = "id", value = "主题id", required = true)
    })
    public Result get(Integer start, Integer pageSize, Integer id){
        PageHelp pageHelp = new PageHelp(start, pageSize, null);
        List<Content> contentList =  contentService.findContentByThemeId(pageHelp, id);
        pageHelp.setData(contentList);
        return new Result(200, "查询成功", new PageInfo<>(contentList));
    }

    @GetMapping("/getColumn")
    @ApiOperation("获取专栏")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "start", value = "开始页", required = true),
            @ApiImplicitParam(name = "pageSize", value = "页代销", required = true),
            @ApiImplicitParam(name = "id", value = "话题id", required = true)
    })
    public Result getColumn(Integer start, Integer pageSize, Integer id){
        PageHelp pageHelp = new PageHelp(start, pageSize, null);
        List<Content> contentList = contentService.queryColumnConten(pageHelp, id);
        return new Result(200, "查询成功", new PageInfo<>(contentList));
    }

    @GetMapping("/getTheme")
    @ApiOperation("获取专栏")
    public Result getTheme(){
        List<Theme> all = themeService.findAll();
        return new Result(200, "获取成功", all);
    }

}
