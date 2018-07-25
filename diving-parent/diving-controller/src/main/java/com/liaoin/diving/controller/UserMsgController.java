package com.liaoin.diving.controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.liaoin.diving.common.PageHelp;
import com.liaoin.diving.common.Result;
import com.liaoin.diving.dao.ContentRepository;
import com.liaoin.diving.entity.*;
import com.liaoin.diving.entity.relationship.UserLike;
import com.liaoin.diving.service.*;
import com.liaoin.diving.view.LikeView;
import com.liaoin.diving.view.NoticeView;
import com.liaoin.diving.view.SecondHandView;
import com.liaoin.diving.vo.NoticeVo;
import com.liaoin.diving.vo.UserVo;
import io.swagger.annotations.*;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.spring.web.json.Json;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author : huqingxi
 * @describe :
 * @date 2018/06/07
 */
@RestController
@RequestMapping("/um")
@Api(tags = "我的消息", value = "我的消息")
public class UserMsgController {
    @Resource
    private UserService userService;
    @Resource
    private ContentService contentService;
    @Resource
    private FocusService focusService;
    @Resource
    private DiscussionService discussionService;
    @Resource
    private UserLikeService userLikeService;
    @Resource
    private OfficialService officialService;
    @Resource
    HttpServletRequest httpServletRequest;
    @Resource
    SecondHandService secondHandService;

    @GetMapping("/newFirend")
    @ApiOperation("新的朋友(最新关注我的用户)")
    public Result newFirend(HttpSession session){
        User loginUser = (User)session.getAttribute("loginUser");
        if (Objects.isNull(loginUser)){
            return new Result(300, "请登录", null);
        }
        loginUser =  userService.findById(loginUser.getId());
        List<Integer> fansIds = focusService.findFansIds(loginUser.getId());
        List<User> userList = new ArrayList<>();

        for (int i = fansIds.size(); i > 0; i--){
            Integer id = fansIds.get( i -1);
            User fans = userService.findById(id);
            userList.add(fans);
        }
        loginUser.setFollowNum(0);
        userService.update(loginUser);
        //contentRepository.addCol();
        if (userList.size() == 0){
            return new Result(200, "还没有人关注你", userList);
        }
        return new Result(200, "查询成功", userList);
    }


    @GetMapping("/notice")
    @ApiOperation("最新通知 (我发布内容的最新评论用户)")
    public Result newNotice(HttpSession session, Integer start, Integer pageSize){
        User loginUser = (User)session.getAttribute("loginUser");
        if (Objects.isNull(loginUser)){
            return new Result(300, "请登录", null);
        }
        loginUser = userService.findById(loginUser.getId());  // new date

        if (Objects.isNull(loginUser.getNoticeNum())){
            return new Result(200, "暂无最新", null);
        }
        List<NoticeView> notices = userService.findNewNotice(loginUser.getId(), start, pageSize);

        return new Result(200, "查询成功", new PageInfo<>(notices));
    }

    /****************************       app         ******************************************************/
    @ApiOperation(value = "前端获取点赞列表",notes = "app端获取点赞列表") //type 2. 动态
    @GetMapping("/getLikeList")
    public Result getLikeList(HttpSession session,
                              @ApiParam(value = "起始页",required = true) @RequestParam(required = true) Integer start,
                              @ApiParam(value = "分页尺寸",required = true) @RequestParam(required = true) Integer pageSize){
        User loginUser = (User)session.getAttribute("loginUser");
        if (Objects.isNull(loginUser)){
            return new Result(300, "请登录", null);
        }

        List<LikeView> likeViewList = userLikeService.findLikeViewList(loginUser.getId(),start,pageSize);

        return new Result(200, "查询成功", new PageInfo<>(likeViewList));
    }

    @GetMapping("/getOfficial")
    @ApiOperation("获取官方消息")
    public Result getOfficial(HttpSession session){
        User loginUser = (User)session.getAttribute("loginUser");
        if (Objects.isNull(loginUser)){
            return new Result(300, "请登录", null);
        }
        List<Official> officials = officialService.findAll();
        return new Result(200, "查询成功", officials);
    }

    @GetMapping("/agree")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "start", value = "起始页", required = true),
            @ApiImplicitParam(name = "pageSize", value = "页大小", required = true)
    })
    @ApiOperation("被赞同")  // 给我评论点赞的用户
    public Result agree(Integer start, Integer pageSize, HttpSession session){
        User loginUser = (User)session.getAttribute("loginUser");
        if (Objects.isNull(loginUser)){
            return new Result(300, "请登录", null);
        }
        PageHelp pageHelp = new PageHelp(start, pageSize, null);
        List<User> users = userService.findAgree(pageHelp, loginUser.getId());
        pageHelp.setData(users);
        return new Result(200, "查询成功", new PageInfo<>(users));
    }


}
