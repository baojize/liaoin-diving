package com.liaoin.diving.controller;

import com.github.pagehelper.PageInfo;
import com.liaoin.diving.common.PageHelp;
import com.liaoin.diving.common.Result;
import com.liaoin.diving.entity.Content;
import com.liaoin.diving.entity.SecondHand;
import com.liaoin.diving.entity.User;
import com.liaoin.diving.service.ContentService;
import com.liaoin.diving.service.SecondHandService;
import com.liaoin.diving.service.UserService;
import com.liaoin.diving.utils.RandomUtils;
import com.liaoin.diving.view.SecondHandView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @author : huqingxi
 * @describe :
 * @date 2018/06/07
 */
@RequestMapping("/gam")
@RestController
@Api(tags = "社交论坛", value = "社交论坛")
public class GamController {

    @Resource
    private ContentService contentService;
    @Resource
    private UserService userService;
    @Resource
    private HttpSession session;
    @Resource
    private SecondHandService secondHandService;

    @GetMapping("/newest")
    @ApiOperation("社区-最新内容")
    public Result newest(){
        try {
            List<Content> contentList = contentService.findNewest();
            return new Result(200, "查询成功", contentList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(300, "查询失败", null);
        }
    }

    @GetMapping("/getFocus")
    @ApiOperation("社区-关注") // 登录用户所关注的用户的最新内容
    public Result  getFocus(Integer start, Integer pageSize){
        // 1.用户是否登录
        User loginUser = (User) session.getAttribute("loginUser");
        if (Objects.isNull(loginUser)){
            return new Result(200, "您还没有登录", null);
        }
        PageHelp pageHelp = new PageHelp(start, pageSize, null);
        // 2.获取最新的用户信息
        loginUser = userService.findOne(loginUser.getId());
        List<User> focusList = loginUser.getFocusList(); // 获取该登录用户所关注用户列表
        List<Integer> ids = new ArrayList<>();
        for (User user : focusList){
            ids.add(user.getId());
        }

        List<Content> contentList = contentService.findFocusConten(pageHelp, ids);
        //Map<String, Object> focusConten = contentService.findFocusConten(focusList);

        return new Result(200, "查询成功", new PageInfo<>(contentList));
    }

    @GetMapping("/hot")
    @ApiOperation("社区-最热  [条件:点赞>2(后期需修改)]")
    public Result hot(){
        try {
            List<Content> hotContent = contentService.findHotContent();
            return new Result(200, "查询成功", hotContent);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(300, "查询失败", null);
        }
    }

    @GetMapping("/findFried")
    @ApiOperation("社区-找朋友")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "start", value = "当前页码，第一页从0开始", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "pageSize", value = "分页大小", dataType = "String", paramType = "query", required = true)
    })
    public Result findFried(@RequestParam("start")Integer start, @RequestParam("pageSize") Integer pageSize){
        //Page<User> users = userService.findFried(start, pageSize);
        List<User> users = userService.findAllUser(); // 全部用户集合
        List<User> randUser = new ArrayList<>(); // 随机用户集合
        List<Integer> random = RandomUtils.get(users.size(), pageSize); // 随机数
        for (Integer rand: random){
            randUser.add(users.get(rand));
        }
        return new Result(200,"查找成功",randUser);
    }

    /*@GetMapping("/secondHand")
    @ApiOperation("社区-二手")
    public Result secondHand(){
        try {
            List<SecondHand> secondHandList = contentService.findSecondHand();
            return new Result(200, "查询成功", secondHandList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(300, "查询失败", null);
        }
    }*/


    @GetMapping("/getAllSecondHand")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "start",value = "起始页 第一页为0"),
            @ApiImplicitParam(name = "pageSize",value = "页大小")
    })
    @ApiOperation("查询所有二手")
    public Result getAllSecondHand(Integer start, Integer pageSize){
        PageHelp pageHelp = new PageHelp(start, pageSize, null);
        List<SecondHandView> all = secondHandService.findAll(pageHelp);
        return new Result(200, "查询成功", new PageInfo<>(all));
    }

    @GetMapping("/getBigCategory")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "start",value = "起始页 第一页为0"),
            @ApiImplicitParam(name = "pageSize",value = "页大小"),
            @ApiImplicitParam(name = "id",value = "分类id")
    })
    @ApiOperation("根据大分类查询二手")
    public Result getAllByBigCategory(Integer start, Integer pageSize, Integer id){
        PageHelp pageHelp = new PageHelp(start, pageSize, null);
        List<SecondHandView> all = secondHandService.findByBigCategory(pageHelp, id);
        return new Result(200, "查询成功", new PageInfo<>(all));
    }


}
