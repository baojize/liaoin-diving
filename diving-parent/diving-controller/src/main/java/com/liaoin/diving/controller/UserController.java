package com.liaoin.diving.controller;

import com.aliyuncs.exceptions.ClientException;
import com.liaoin.diving.common.PageBean;
import com.liaoin.diving.common.Result;
import com.liaoin.diving.entity.*;
import com.liaoin.diving.service.AddressService;
import com.liaoin.diving.service.ContentService;
import com.liaoin.diving.service.ImageService;
import com.liaoin.diving.service.UserService;
import com.liaoin.diving.utils.MobileUtils;
import io.swagger.annotations.*;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.util.DigestUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/user")
@Api(tags = "用户模块",value = "用户模块")
public class UserController {

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Resource
    private UserService userService;
    @Resource
    private ImageService imageService;
    @Resource
    private ServletContext servletContext;
    @Resource
    private HttpSession session;
    @Resource
    private ContentService contentService;
    @Resource
    private AddressService addressService;

    /*@PostMapping("/insert")
    @ApiOperation("新增")
    public Result save(@RequestBody User user) {
        user.setCreateTime(new Date());
        //表示删除
        user.setIsDelete("0");
        user.setSignature("这个人很懒,Ta什么都没留下");
        //积分为默认0
        user.setPoints(0);
        userService.insert(user);
        return new Result(200, "新增成功", null);
    }*/

    @PostMapping("/update")
    @ApiOperation("修改")
    public Result update(@RequestBody User user) throws UnsupportedEncodingException {

        user.setIsDelete("0");
        //user.setPoints(null);
        if (!Objects.isNull(user.getPassword())){
            user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes("UTF-8")));
        }
        userService.update(user);
        return new Result(200, "修改成功", null);
    }

    @PostMapping("/delete")
    @ApiOperation("删除")
    public Result delete(@RequestBody Integer[] ids) {
        userService.delete(ids);
        return new Result(200, "删除成功", null);
    }

    @GetMapping("/findOne")
    @ApiImplicitParam(name = "id", value = "主键", dataType = "String", paramType = "query", required = true)
    @ApiOperation("根据主键查询")
    public Result findOne(@RequestParam Integer id) {
        User user = userService.findOne(id);
        if (user == null) {
            return new Result(300, "暂无数据", null);
        }
        if ("1".equals(user.getIsDelete()) ){
            return new Result(300, "查无此人", null);
        }
        return new Result(200, "查询成功", user);
    }

    @PostMapping("/pageQuery")
    @ApiOperation("条件分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页码，第一页从0开始", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "size", value = "分页大小", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "sort", value = "排序，如：id,desc，可以添加多个排序条件", dataType = "String", paramType = "query", required = false)
    })
    public Result pageQuery(@PageableDefault Pageable pageable, @RequestBody User user) {
        PageBean<User> pageBean = userService.pageQuery(pageable, user);
        if (pageBean == null) {
            return new Result(300, "暂无数据", null);
        }
        return new Result(200, "查询成功", pageBean);
    }

    @GetMapping("/getCode")
    @ApiOperation("获取验证码，app端请设置获取验证码60秒CD")
    @ApiImplicitParam(name = "mobile", value = "手机号码", dataType = "String", paramType = "query", required = true)
    public Result getCode(@RequestParam String mobile) throws ClientException {
        // 1.检验手机号码
        if (!MobileUtils.isMobileNumber(mobile)) {
            return new Result(300, "请输入正确的手机号码", null);
        }
        // 2.生成短信验证码
        int code = (int) ((Math.random() * 9 + 1) * 100000);
        // 3.获取阿里云短信系统参数
        Object contextAttribute = servletContext.getAttribute("systemAliyunSms");
        SystemAliyunSms systemAliyunSms = (SystemAliyunSms) contextAttribute;
       // SystemAliyunSms systemAliyunSms = new SystemAliyunSms();
        // 3.将短信验证码放入redis中，并发送短信
        userService.sendSms(mobile, code + "", systemAliyunSms);
        logger.info("已为手机" + mobile + "发送短信验证码：" + code);
        return new Result(200, "短信已发送", code);
    }

    @GetMapping("/checkCode")
    @ApiOperation("校验验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mobile", value = "手机号码", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "code", value = "验证码", dataType = "String", paramType = "query", required = true)
    })
    public Result checkCode(@RequestParam String mobile, @RequestParam String code) {
        // 1.检验手机号是否合法
        boolean b = MobileUtils.isMobileNumber(mobile);
        if (!b) {
            return new Result(300, "请输入正确的手机号码", null);
        }
        // 2.校验手机号码是否已经注册
        User user = userService.findByMobile(mobile);
        if (user != null) {
            return new Result(300, "手机已注册", null);
        }
        // 3.校验验证码
        return userService.checkCode(mobile, code);
    }

    @GetMapping("/register")
    @ApiOperation("用户注册")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mobile", value = "手机", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "nickname", value = "昵称", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "sex", value = "性别：0女，1男", dataType = "String", paramType = "query", required = true),
    })
    public Result register(@RequestParam String mobile, @RequestParam String nickname, @RequestParam String sex) throws UnsupportedEncodingException {
        // 1.检验是否是手机号码
        boolean b = MobileUtils.isMobileNumber(mobile);
        if (!b) {
            return new Result(300, "请输入正确的手机号码", null);
        }
        // 2.校验手机是否已经注册
        User user = userService.findByMobile(mobile);
        if (user != null) {
            return new Result(300, "手机已注册", null);
        }
        // 3.校验昵称是否已经存在
        user = userService.findByNickname(nickname);
        if (user != null) {
            return new Result(300, "昵称已存在", null);
        }
        // 4.新增用户信息
        return userService.register(mobile, nickname, sex);
    }

    @PostMapping(value = "/upload", consumes = "multipart/*", headers = "content-type=multipart/form-data")
    @ApiOperation("上传用户头像")
    @ApiImplicitParam(name = "userId", value = "用户编号", dataType = "String", paramType = "query", required = true)
    public Result upload(@ApiParam(value = "上传的文件，此接口只能上传一个文件", required = true) MultipartFile multipartFile, @RequestParam Integer userId, HttpServletRequest request) throws IOException {

      //String path = ClassUtils.getDefaultClassLoader().getResource("").getPath();
        // 1.判断用户是否存在
        User user = userService.findOne(userId);
        if (user == null) {
            return new Result(300, "用户不存在", null);
        }
        // 2.上传文件
        // 2.1 获取文件名
        String originalFilename = multipartFile.getOriginalFilename();
        String filename = UUID.randomUUID().toString() + originalFilename.substring(originalFilename.lastIndexOf("."));
        // 2.2 创建上传文件夹
        String realPath = servletContext.getRealPath("/upload/");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        String datePath = simpleDateFormat.format(new Date());

        //File dir = new File(realPath + datePath);
        File dir = new File("D://upload");

        File dateFile = new File(dir.toString() + "//" + datePath);


        /*if (!dir.exists()) {
            boolean b = dir.mkdirs();
            logger.info("创建上传文件夹" + dir.getPath() + "：" + b);  // 父
        }*/

        if (!dateFile.exists()){
            boolean b = dateFile.mkdirs();
            logger.info("创建上传文件夹" + dateFile.getPath() + ":" + b); // 日期(子)
        }
        // 2.3 上传文件
        //File file = new File(dir, filename);
        File file = new File(dateFile, filename);
        FileCopyUtils.copy(multipartFile.getBytes(), file);
        // 2.4 新增文件信息
        Image image = new Image();
        image.setContent(originalFilename);
        image.setType("1");
        image.setUrl("/upload/" + datePath + "/" + filename);
        imageService.insert(image);
        logger.info("新增文件信息：" + image.getUrl());
        // 3.更新用户信息
        user.setImage(image);
        userService.update(user);
        logger.info("修改用户信息：" + user.getNickname());
        return new Result(200, "上传成功", image);
    }

    @GetMapping("/login")
    @ApiOperation("用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mobile", value = "手机号码, copy:18375701380", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "code", value = "验证码", dataType = "String", paramType = "query", required = true)
    })
    public Result login(@RequestParam String mobile, @RequestParam String code, HttpSession session) {
        // 1.检验是否是手机号码
        boolean b = MobileUtils.isMobileNumber(mobile);
        if (!b) {
            return new Result(300, "请输入正确的手机号码", null);
        }
        // 1.2.校验手机号码是否已经注册
        User user = userService.findByMobile(mobile);
        if (user == null) {
            return new Result(300, "该手机号码尚未注册", null);
        }
        // 2.用户登录 （不用验证码 先注释）
       /* int status = userService.login(mobile, code, user);
        if (status != 200) {
            return new Result(300, "验证码错误", null);
        }*/
        // 3.将用户信息放入session

        session.setAttribute("loginUser", user);
        return new Result(200, "登录成功", null);
    }

    @GetMapping("/getLoginUser")
    @ApiOperation("获取当前登录用户")
    public Result getLoginUser(HttpSession session) {
        // 1.用户是否登录
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) {
            return new Result(300, "请登录", null);
        }
        // 2.获取最新的用户信息
        loginUser = userService.findOne(loginUser.getId());
        return new Result(200, "获取成功", loginUser);
    }

    @GetMapping("findAll")
    @ApiOperation("查询所有用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页码，第一页从0开始", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "size", value = "分页大小", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "sort", value = "排序，如：id,desc，可以添加多个排序条件", dataType = "String", paramType = "query", required = false)
    })
    public Result findAllPage(@PageableDefault Pageable pageable){
        PageBean<User> pageBean = userService.pageQuery(pageable,null);
        if (pageBean == null) {
            return new Result(300, "暂无数据", null);
        }
        return new Result(200, "查询成功", pageBean);
    }

    @GetMapping("/focus")
    @ApiOperation("获得当前登录用户的关注列表")
    public Result focus() {
        // 1.用户是否登录
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) {
            return new Result(300, "请登录", null);  // 先注释
        }
        // 2.获取最新的用户信息

        loginUser = userService.findOne(loginUser.getId());
        List<User> focusList = loginUser.getFocusList();
        List<User> newestList =  new ArrayList<>();  // 倒序集合
        for (int i = focusList.size(); i > 0; i--){
            newestList.add(focusList.get(i - 1));
        }
        return new Result(200, "查询成功", newestList);
    }

    @GetMapping("/getAddr")
    @ApiOperation("获取收货地址")
    public Result getAddr(HttpSession session){
        User loginUser = (User)session.getAttribute("loginUser");
        if (Objects.isNull(loginUser)){
            return new Result(300, "请登录", null);
        }
        loginUser = userService.findById(loginUser.getId());
        List<Address> addresses = addressService.findCurrenAddr(loginUser.getId());
        return new Result(200, "查询成功", addresses);
    }

    @PostMapping("/addAddress")
    @ApiOperation("添加收货地址")
    public Result addAddress(HttpSession session, @RequestBody Address address){
        User loginUser = (User)session.getAttribute("loginUser");
        if (Objects.isNull(loginUser)){
            return new Result(300, "请登录", null);
        }

        if (address.getIsDefault() == 1){
            addressService.setDefaultAddrZero(loginUser.getId());
        }
        address.setIsDelete(0);
        address.setUserId(loginUser.getId());
        //address.setIsDefault(1);
        addressService.insert(address);
       return new Result(200, "添加成功", null);
    }

    /*@PostMapping("/deleteAddr")
    @ApiOperation("删除收货地址")
    @ApiImplicitParam(name = "id", value = "地址id")
    private Result deleteAddr(HttpSession session, Integer id){
        User loginUser = (User)session.getAttribute("loginUser");
        if (Objects.isNull(loginUser)){
            return new Result(300, "请登录", null);
        }
        addressService.deleteAddr(id);
        return null;
    }*/


    /*@GetMapping
    public Result loginOut(HttpSession session){
        session.removeAttribute("loginUser");
        session.invalidate();
        return new Result(200, "清除成功", null);
    }*/
}
