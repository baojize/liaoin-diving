package com.liaoin.diving.controller;

import com.liaoin.diving.common.PageBean;
import com.liaoin.diving.common.Result;
import com.liaoin.diving.entity.Topic;
import com.liaoin.diving.service.TopicService;
import com.sun.org.apache.regexp.internal.RE;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.apache.http.HttpStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @describe
 * @author huqingxi
 * @date 2018/07/09
 */
@RestController
@RequestMapping("/topic")
@Api(tags = "话题标签模块")
public class TopicController {

    @Resource
    private TopicService topicService;


    @PostMapping("/insert")
    @ApiOperation("新增")
    public Result save(@RequestBody Topic topic) {
        try {
            topic.setIsColumn("0");
            topicService.insert(topic);
            return new Result(200, "新增成功", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(300, "新增失败", null);
        }

    }

    @PostMapping("/delete")
    @ApiOperation("删除")
    public Result delete(@RequestBody Integer[] ids) {
        try {
            topicService.delete(ids);
            return new Result(200, "删除成功", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(300, "删除失败", null);
        }

    }

    @PostMapping("/update")
    //@ApiImplicitParam(name = "id", value = "修改主键必填", dataType = "String", paramType = "query", required = true)
    @ApiOperation("修改")
    public Result update(@RequestBody Topic topic) {
        try {
            topicService.update(topic);
            return new Result(200, "修改成功", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(300, "修改失败,可能是内容不存在", null);
        }

    }

    @GetMapping("/findOne")
    @ApiImplicitParam(name = "id", value = "主键", dataType = "String", paramType = "query", required = true)
    @ApiOperation("根据主键查询")
    public Result findOne(@RequestParam Integer id) {
        Topic topic = topicService.findOne(id);
        if (topic == null) {
            return new Result(300, "暂无数据", null);
        }
        return new Result(200, "查询成功", topic);
    }

    @PostMapping("/pageQuery")
    @ApiOperation("条件分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页码，第一页从0开始", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "size", value = "分页大小", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "sort", value = "排序，如：id,desc，可以添加多个排序条件", dataType = "String", paramType = "query", required = false)
    })
    public Result pageQuery(@PageableDefault Pageable pageable, @RequestBody Topic topic) {
        try {
            PageBean<Topic> pageBean = topicService.pageQuery(pageable, topic);
            return new Result(200, "查询成功", pageBean);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(300, "查询失败", null);
        }
    }

    // 将话题设为专栏,  根据创建时间排序
    @PostMapping("/setColumn")
    @ApiOperation("设置专栏")
    @ApiImplicitParam(name = "ids", value = "编号,如需批量设置,例:1,2,3, 用逗号隔开")
    public Result setColumn(Integer[] ids){
        try {
            topicService.setColumn(ids);
            return new Result(200, "设置成功", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(300, "设置失败", null);
        }
    }

    @GetMapping("/findColumn")
    @ApiOperation("获取专栏话题")
    public Result findColumn(){
        try {
            List<Topic> topics = topicService.findByColunm();
            return new Result(200, "查询成功", topics);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(300, "查询失败", null);
        }
    }

}

