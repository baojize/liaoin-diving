package com.liaoin.diving.controller;

import com.liaoin.diving.common.Result;
import com.liaoin.diving.entity.Content;
import com.liaoin.diving.entity.User;
import com.liaoin.diving.service.ContentService;
import com.sun.org.apache.regexp.internal.RE;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;

/**
 * @author : huqingxi
 * @describe :
 * @date 2018/06/07
 */
@RequestMapping("/ring")
@RestController
@Api(tags = "混圈子", value = "混圈子")
public class RingController {
    @Resource
    private ContentService contentService;
    @Resource
    private HttpSession session;


    /**
     *  查询  type = 1 (讨论) , isdelete = 0 (未逻辑删除的数据)
     * @param current
     * @param size
     * @return
     */
    @PostMapping("/getAll")
    @ApiOperation("查询所有讨论数据") // 查询所有讨论内容
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current",value = "当前页 0开始" ,dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "size",value = "每页大小" ,dataType = "Integer", paramType = "query")
    })
    public Result ring(Integer current, Integer size){
        Page<Content> contentPage = contentService.ring(current,size);
        return new Result(200,"查询成功",contentPage);
    }


    @GetMapping("/getDiscuss")
    @ApiOperation("获取[讨论]话题列表")
    public Result getDiscussTopic() {
        List<Content> contentList = contentService.findByType("1");
        return new Result(200, "查询成功", contentList);
    }

    @GetMapping("/findById")
    @ApiOperation("根据id查询讨论")
    public Result findById(Integer id){
        try {
            Content content = contentService.findById(id);
            if (Objects.isNull(content)){
                return new Result(200, "暂无数据", null);
            }
            return new Result(200, "查询成功", content);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(300, "查询失败", null);
        }
    }

    @PostMapping("/upDiscuss")
    @ApiOperation("发布讨论")
    public Result upDiscuss(HttpSession session, @RequestBody Content content) {
        // 1.判断是否登录
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) {
            return new Result(300, "请登录", null);
        }
        // 2.封装数据
        content.setType("1");
        content.setUser(loginUser);
        contentService.insert(content);
        return new Result(200, "发布成功", null);
    }

}


