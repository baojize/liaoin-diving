package com.liaoin.diving.controller;

import com.liaoin.diving.common.PageBean;
import com.liaoin.diving.common.Result;
import com.liaoin.diving.dao.ContentGroupRepository;
import com.liaoin.diving.entity.*;
import com.liaoin.diving.entity.relationship.UserLike;
import com.liaoin.diving.service.*;
import com.liaoin.diving.vo.ContentVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.util.ClassUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.jws.WebResult;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
/**
 * 模块
 *
 * @author huqingxi
 * @date 2018/07/09
 */
@RestController
@RequestMapping("/content")
@Api(tags = "内容模块",value = "内容模块")
public class ContentController {

    private static Logger logger = LoggerFactory.getLogger(ContentController.class);

    @Resource
    private ContentService contentService;
    @Resource
    private ImageService imageService;
    @Resource
    private TopicService topicService;
    @Resource
    private ServletContext servletContext;
    @Resource
    private HttpSession session;
    @Resource
    private UserService userService;
    @Resource
    private DiscussionService discussionService;
    @Resource
    private UserLikeService userLikeService;
    @Resource
    private BannerService bannerService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private ContentGroupRepository contentGroupRepository;


    @PostMapping("/insert")
    @ApiOperation("新增")
    public Result save(@RequestBody Content content) {
        contentService.insert(content);
        return new Result(200, "新增成功", null);
    }

    // 发布的内容禁止修改
  /*@PostMapping("/update")
    @ApiOperation("修改")
    public Result update(@RequestBody Content content) {
        contentService.update(content);
        return new Result(200, "修改成功", null);
    }*/

    // 逻辑删除
    @PostMapping("/delete")
    @ApiOperation("删除")
    public Result delete(@RequestBody Integer[] ids) {
        contentService.delete(ids);
        return new Result(200, "删除成功", null);
    }

    @GetMapping("/findOne")
    @ApiImplicitParam(name = "id", value = "主键", dataType = "String", paramType = "query", required = true)
    @ApiOperation("根据主键查询")
    public Result findOne(@RequestParam Integer id) {
        // 1.根据主键查询
        Content content = contentService.findOne(id);
        if (content == null) {
            return new Result(300, "暂无数据", null);
        }
        // 2.更新阅读量
        Integer reading = content.getReading();
        reading++;
        content.setReading(reading);
        contentService.update(content);
        return new Result(200, "查询成功", content);
    }

    @PostMapping("/pageQuery")
    @ApiOperation("条件分页查询，最新数据：sort=createTime,desc，热门数据：")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页码，第一页从0开始", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "size", value = "分页大小", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "sort", value = "排序，如：id,desc，可以添加多个排序条件", dataType = "String", paramType = "query", required = false)
    })
    public Result pageQuery(@PageableDefault Pageable pageable, @RequestBody Content content) {
        PageBean<Content> pageBean = contentService.pageQuery(pageable, content);
        if (pageBean == null) {
            return new Result(300, "暂无数据", null);
        }
        return new Result(200, "查询成功", pageBean);
    }

    /**
     *
     * @param request
     * @param type
     * @return
     * @throws IOException
     */
    @PostMapping(value = "/upload", consumes = "multipart/*", headers = "content-type=multipart/form-data")
    @ApiOperation(value = "批量上传", notes = "上传的文件name属性为file，可以添加多个。单个文件限制100MB，每次请求限制900MB,type: 1图片, 2视频  >")
    public Result upload(HttpServletRequest request, @RequestParam String type) throws IOException {
        // 1.创建文件集合
        List<Image> imageList = new ArrayList<>();
        // 2.上传文件，存入数据库
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        for (MultipartFile multipartFile : files) {
            // 2.1 获取文件名
            String originalFilename = multipartFile.getOriginalFilename();
            String filename = UUID.randomUUID().toString() + originalFilename.substring(originalFilename.lastIndexOf("."));
            // 2.2 创建上传文件夹
            String realPath = servletContext.getRealPath("/upload/");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
            String datePath = simpleDateFormat.format(new Date());
            File dir = new File(realPath + datePath);
            if (!dir.exists()) {
                boolean b = dir.mkdirs();
                logger.info("创建上传文件夹" + dir.getPath() + "：" + b);
            }
            // 2.3 上传文件
            File file = new File(dir, filename);
            FileCopyUtils.copy(multipartFile.getBytes(), file);
            // 2.4 新增文件信息
            Image image = new Image();
            image.setContent(originalFilename);
            image.setType(type);
            image.setUrl("/upload/" + datePath + "/" + filename);
            imageService.insert(image);
            logger.info("新增文件信息：" + image.getUrl());
            // 2.5 添加文件到集合中
            imageList.add(image);
        }
        // 3.返回文件集合
        return new Result(200, "上传成功", imageList);
    }

    /*@PostMapping("/topic/add")
    @ApiOperation("添加话题--这不是内容的话题")
    public Result addTopic(HttpSession session,@RequestBody Topic topic){
        //判断是否登录
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null){
            return  new Result(300,"请登录",null);
        }
        //插入时不能有id
        topic.setId(null);
        //默认不删除
        topic.setIsDelete("0");
        topicService.insert(topic);
        return new Result(200,"添加话题成功",null);
    }*/

    /**
     *   获取content表中 type= 1 的数据
     * @return
     */
    @GetMapping("/topic/discuss")
    @ApiOperation("获取[讨论]话题列表")
    public Result getDiscussTopic() {
        List<Content> contentList = contentService.findByType("1");
        return new Result(200, "查询成功", contentList);
    }

    /**
     * content表中type=2 的数据
     * @return
     */
    @GetMapping("/topic/update")
    @ApiOperation("获取[动态]话题列表")
    public Result getUpdateTopic() {
        List<Content> contentList = contentService.findByType("2");
        return new Result(200, "查询成功", contentList);
    }


    @PostMapping("/publish/discuss")
    @ApiOperation("发布讨论")
    public Result publishDiscuss(HttpSession session, @RequestBody ContentVo contentVo) {
        // 1.判断是否登录
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) {
            return new Result(300, "请登录", null);
        }
        Content content = contentVo.getContent();
        // 2.封装数据
        content.setType("1");
        if (Objects.isNull(contentVo.getGroupId())){
            //groupId为空 保存为用户
            content.setUser(loginUser);
            contentService.insert(content);
        }else {
            //俱乐部发布
            contentService.insert(content);
            //更改俱乐部的发布量
            groupService.updateReleaseNum(contentVo.getGroupId(),1L);
            //维护content group 关系表
            ContentGroup contentGroup = new ContentGroup();
            contentGroup.setContentId(content.getId());
            contentGroup.setGroupId(contentVo.getGroupId());
            contentGroupRepository.save(contentGroup);

        }

        return new Result(200, "发布成功", null);
    }

    @PostMapping("/publish/update")
    @ApiOperation("发布动态")
    public Result publishUpdate(HttpSession session, @RequestBody ContentVo contentVo) {
        // 1.判断是否登录
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) {
            return new Result(300, "请登录", null);
        }
        Content content = contentVo.getContent();
        // 2.封装数据
        content.setType("2");
        if (Objects.isNull(contentVo.getGroupId())){
            //groupId为空 保存为用户
            //绑定用户
            content.setUser(loginUser);
            contentService.insert(content);
        }else {
            //俱乐部发布
            contentService.insert(content);
            //更改俱乐部的发布量
            groupService.updateReleaseNum(contentVo.getGroupId(),1L);
            //维护content group 关系表
            ContentGroup contentGroup = new ContentGroup();
            contentGroup.setContentId(content.getId());
            contentGroup.setGroupId(contentVo.getGroupId());
            contentGroupRepository.save(contentGroup);
        }


        contentService.insert(content);
        return new Result(200, "发布成功", null);
    }
    @PostMapping("/publish/secondHand")
    @ApiOperation("发布二手")
    public Result publishSecondHand(HttpSession session, @RequestBody Content content) {
        // 1.判断是否登录
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) {
            return new Result(300, "请登录", null);
        }
        // 2.封装数据
        content.setType("3");
        content.setIsDelete("0");
        content.setUser(loginUser);
        contentService.insert(content);
        return new Result(200, "发布成功", null);
    }

    // 无解
     /* @PostMapping("/publish/question")
    @ApiOperation("发布问题")
    public Result publishQuestion(HttpSession session, @RequestBody Content content) {
        // 1.判断是否登录
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) {
            return new Result(300, "请登录", null);
        }
        // 2.封装数据
        content.setType("4");
        content.setUser(loginUser);
        contentService.insert(content);
        return new Result(200, "发布成功", null);
    }*/



    @GetMapping("/likeCount")
    @ApiOperation("获指定内容的点赞数量")
    public Result getLikeCount(Integer id){
        try {
            Integer count = contentService.likeCount(id);
            return new Result(200, "查询成功", count);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(300, "查询失败", null);
        }
    }


    /**
     * 批量推荐  , (批量改变所选id对应记录的is_recommend 的状态)
     * @return
     */
    @PostMapping("/bacthReco")
    @ApiOperation("批量推荐")
    public Result bacthReco(@RequestBody Integer[] ids){
        if (ids.length == 0){
            return new Result(300,"请选择要推荐的内容",null);
        }
        contentService.bacthReco(ids);
        return new Result(200,"推荐成功",null);
    }

    /**
     *  查询  is_recommend =1(推荐) ,   type = 2 (动态)  ,  isdelete = 0(未逻辑删除)  的数据
     * @param current
     * @param size
     * @return
     */
    @PostMapping("/recommend")
    @ApiOperation("内容推荐")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current",value = "当前页 0开始" ,dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "size",value = "每页大小" ,dataType = "Integer", paramType = "query")
    })
    public Result recommend(Integer current,Integer size){
        Page<Content> contentPage = contentService.recommend(current,size);
        return new Result(200,"查询成功",contentPage);
    }


    /**
     *  查询  type = 1 (讨论) , isdelete = 0 (未逻辑删除的数据)
     * @param current
     * @param size
     * @return
     */
    @PostMapping("/ring")
    @ApiOperation("混圈子") // 查询所有讨论内容
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current",value = "当前页 0开始" ,dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "size",value = "每页大小" ,dataType = "Integer", paramType = "query")
    })
    public Result ring(Integer current,Integer size){
        Page<Content> contentPage = contentService.ring(current,size);
        return new Result(200,"查询成功",contentPage);
    }

    @GetMapping("/getColumn")
    @ApiOperation("获取某话题下未删除专栏内容")
    @ApiImplicitParam(name = "topicId",value = "话题id")
    public Result getColumnContent(Integer topicId){
        try {
            List<Content> columnConten = contentService.findColumnConten(topicId);
            return new Result(200, "查询成功", columnConten);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(300, "查询失败", null);
        }
    }


    @GetMapping("/collectContent")
    @ApiOperation("添加或取消收藏")
        public Result collectContent(HttpSession session,  Integer contentId){
        // 1.判断是否登录
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) {
            return new Result(300, "请登录", null);
        }
        loginUser = userService.findOne(loginUser.getId());  // 获取最新数据
        // 2.判断内容是否存在
        Content content = contentService.findOne(contentId);
        if ( Objects.isNull(content) ) {
            return new Result(300, "内容不存在", null);
        }
        // 收藏
        contentService.collect(loginUser, content);
        return new Result(200, "操作成功", null);
    }

    /*@GetMapping("/discussionContent")
    @ApiOperation("评论")
    public Result discussionContent(HttpSession session, Discussion discussion){
        User loginUser = (User) session.getAttribute("loginUser");
        if (Objects.isNull(loginUser)){
            return new Result(300, "请登录", null);
        }
        loginUser = userService.findById(loginUser.getId());
        Content content = contentService.findById(discussion.getId());
        if (Objects.isNull(content)){
            return new Result(300, "内容不存在", null);
        }
        discussionService.insert(discussion);
        return new Result(300, "评论成功", null);
    }*/

    @GetMapping("/like")
    @ApiOperation("点赞或取消点赞")
    @ApiImplicitParam(name = "contentId", value = "内容编号", dataType = "String", paramType = "query", required = true)
    public Result like(HttpSession session, @RequestParam Integer contentId) {
        // 1.判断是否登录
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) {
            return new Result(300, "请登录", null);
        }
        loginUser = userService.findOne(loginUser.getId());
        // 2.判断内容是否存在
        Content content = contentService.findOne(contentId);
        if (content == null) {
            return new Result(300, "内容不存在", null);
        }
        // 3.给内容添加或删除喜欢用户列表
        try {
              contentService.like(content, loginUser); // 点赞或取消

            return new Result(200, "添加成功", content);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(300, "操作失败", content);
        }

    }

    @GetMapping("/download")
    @ApiOperation("下载文件")
    public Result download(HttpServletResponse res, HttpSession session, Integer contentId, Integer imageId) {
        // 1.判断是否登录
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) {
            return new Result(300, "请登录", null);
        }

        Content content = contentService.findOne(contentId);
        if (Objects.isNull(content)){
            return new Result(300, "资源不存在", null);
        }


        List<Image> imageList = content.getImageList();
        Image image = new Image();
        image.setId(imageId);

        String fileName = "" ;
        Image dbImg = contentService.findImgById(imageId);

        fileName = dbImg.getUrl();
        res.setHeader("content-type", "application/octet-stream");
        res.setContentType("application/octet-stream");
        res.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = null;
        //String realPath = servletContext.getRealPath(".");
        //File dir = new File("D://");

        /*File path = new File("upload/");
        String rootPath = path.getAbsolutePath();*/
        //String targetPath = ClassUtils.getDefaultClassLoader().getResource("").getPath();

       /* SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        String datePath = simpleDateFormat.format(new Date());
        File dir = new File("D://upload");
        File dateFile = new File(dir.toString() + "//" + datePath);*/
        File dateFile = new File("D://");
        try {
            os = res.getOutputStream();
            bis = new BufferedInputStream(new FileInputStream(new File(dateFile + fileName)));
            int i = bis.read(buff);
            while (i != -1) {
                os.write(buff, 0, buff.length);
                os.flush();
                i = bis.read(buff);
            }
            loginUser = userService.findOne(loginUser.getId());  // 获取最新数据

            List<Image> downloVideoList = loginUser.getDownloVideoList();
            if (!downloVideoList.contains(dbImg)){
                downloVideoList.add(dbImg);
            }
            userService.update(loginUser);


            return new Result(200, "下载成功", null);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(300, "资源错误", null);
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @GetMapping("/banner")
    @ApiOperation("设置轮播图")
    @ApiImplicitParam(name = "contentId", value = "内容id", required = true)
    public Result banner(Integer contentId){
        Content content = contentService.findById(contentId);
        if (Objects.isNull(content)){
            return new Result(300, "内容不存在", null);
        }
        if ("2".equals(content.getType())){
            return new Result(300, "只能设置[动态][活动]内容为轮播图", null);
        }
        Banner banner = new Banner();
        banner.setContent(content);
        banner.setCreateTime(new Date());
        //banner.setIsDelete(0);
        banner.setType("动态");
        bannerService.add(banner);
        return new Result(200, "设置成功", null);
    }


}
