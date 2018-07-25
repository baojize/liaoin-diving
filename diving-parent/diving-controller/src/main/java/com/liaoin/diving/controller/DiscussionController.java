package com.liaoin.diving.controller;

import com.github.pagehelper.PageInfo;
import com.liaoin.diving.common.PageBean;
import com.liaoin.diving.common.PageHelp;
import com.liaoin.diving.common.Result;
import com.liaoin.diving.entity.Content;
import com.liaoin.diving.entity.Discussion;
import com.liaoin.diving.entity.User;
import com.liaoin.diving.service.ContentService;
import com.liaoin.diving.service.DiscussionService;
import com.liaoin.diving.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.*;

@RestController
@RequestMapping("/discussion")
@Api(tags = "评论模块",value = "评论模块")
public class DiscussionController {

    @Resource
    private DiscussionService discussionService;
    @Resource
    private ContentService contentService;
    @Resource
    private UserService userService;

    /*@PostMapping("/insert")
    @ApiOperation("新增")
    public Result save(@RequestBody Discussion discussion) {
        discussionService.insert(discussion);
        return new Result(200, "新增成功", null);
    }*/

    /*@PostMapping("/update")
    @ApiOperation("修改")
    public Result update(@RequestBody Discussion discussion) {
        discussionService.update(discussion);
        return new Result(200, "修改成功", null);
    }*/

    @PostMapping("/delete")
    @ApiOperation("删除")
    public Result delete(@RequestBody Integer[] ids) {
        discussionService.delete(ids);
        return new Result(200, "删除成功", null);
    }

    @GetMapping("/findOne")
    @ApiImplicitParam(name = "id", value = "主键", dataType = "String", paramType = "query", required = true)
    @ApiOperation("根据主键查询")
    public Result findOne(@RequestParam Integer id) {
        Discussion discussion = discussionService.findOne(id);
        if (discussion == null) {
            return new Result(300, "暂无数据", null);
        }
        return new Result(200, "查询成功", discussion);
    }

    @PostMapping("/pageQuery")
    @ApiOperation("条件分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页码，第一页从0开始", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "size", value = "分页大小", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "sort", value = "排序，如：id,desc，可以添加多个排序条件", dataType = "String", paramType = "query", required = false)
    })
    public Result pageQuery(@PageableDefault Pageable pageable, @RequestBody Discussion discussion) {
        PageBean<Discussion> pageBean = discussionService.pageQuery(pageable, discussion);
        if (pageBean == null) {
            return new Result(300, "暂无数据", null);
        }
        return new Result(200, "查询成功", pageBean);
    }

    @PostMapping("/discuss")
    /*@ApiImplicitParams({
            @ApiImplicitParam(name = "创建用户", value = "创建用户"),
            @ApiImplicitParam(name = "回复用户", value = "回复用户"),
            @ApiImplicitParam(name = "所属内容", value = "所属内容"),
            @ApiImplicitParam(name = "正文", value = "正文" )
    })*/
    @ApiOperation("添加评论")
    public Result discuss(HttpSession session, @RequestBody Discussion discussion) {
        // 1.判断是否登录
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) {
            return new Result(300, "请登录", null);
        }
        //discussion.setCreateUser(loginUser);
        // 2.获取内容
        Content content = contentService.findOne(discussion.getContent().getId());
        /*User us = userService.findById(content.getUser().getId());*/

        if (content == null) {
            return new Result(300, "内容不存在", null);
        }
        // 3.封装数据
        if (!Objects.isNull(discussion.getAnswerUser())){
            //User answerUser = userService.findById(discussion.getAnswerUser().getId());  // "我" 回复的用户
            User answerUser = userService.findById(discussion.getAnswerUser());  // "我" 回复的用户
            Integer noticeNum = answerUser.getNoticeNum();
            if (Objects.isNull(noticeNum)){
                noticeNum = 0;
            }
            answerUser.setNoticeNum(noticeNum + 1);
            userService.update(answerUser); // 保存
        }
        if (!Objects.isNull(discussion.getContent())){  // 内容不为空
            User author = userService.findById(content.getUser().getId());   // 内容作者
            Integer noticeNum = author.getNoticeNum();
            if (Objects.isNull(noticeNum)){
                noticeNum = 0;
            }
            author.setNoticeNum(noticeNum + 1);
            userService.update(author); // 保存
        }
        discussion.setIsDelete("0");
        discussion.setCreateUser(loginUser.getId());
        discussionService.insert(discussion);
        return new Result(200, "评论成功", null);
    }
    @GetMapping("/getDisForContent")
    @ApiOperation("获取某发布内容下的所有评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "start", value ="当前页码第一页从0开始"),
            @ApiImplicitParam(name = "pageSize", value ="分页大小"),
            @ApiImplicitParam(name = "contentId", value ="内容id")
    })
    public Result getDisForContent(Integer start, Integer pageSize, Integer contentId){
        PageHelp pageHelp = new PageHelp(start, pageSize, null);
        List<Discussion> discussions = discussionService.findDisForContentId(pageHelp, contentId);
        pageHelp.setData(discussions);
        return new Result(200, "查询成功", pageHelp);
    }

    @GetMapping("/like")
    @ApiOperation("评论点赞或取消点赞")
    @ApiImplicitParam(name = "disId", value = "评论ID")
    public Result disLike(HttpSession session, Integer disId){
        User loginUser = (User)session.getAttribute("loginUser");
        if (Objects.isNull(loginUser)){
            return new Result(300, "请登录", null);
        }
        loginUser = userService.findById(loginUser.getId());
        Map<String, Object> map = new HashMap<>();
        map.put("disId", disId);
        map.put("userId", loginUser.getId());
        map.put("createTime", new Date());
        map.put("loginUser", loginUser);
        discussionService.like(map);    // 点赞

        return new Result(200, "操作成功", null);
    }

}

