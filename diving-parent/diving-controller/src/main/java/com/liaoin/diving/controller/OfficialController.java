package com.liaoin.diving.controller;

import com.liaoin.diving.common.Result;
import com.liaoin.diving.entity.Image;
import com.liaoin.diving.entity.Label;
import com.liaoin.diving.entity.Official;
import com.liaoin.diving.entity.User;
import com.liaoin.diving.file.FileUpload;
import com.liaoin.diving.file.SingleFile;
import com.liaoin.diving.service.OfficialService;
import com.sun.org.apache.regexp.internal.RE;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.data.elasticsearch.annotations.MultiField;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.beans.Transient;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

/**
 * @author : huqingxi
 * @describe :
 * @date 2018/06/07
 */
@RestController
@RequestMapping("/official")
@Api(tags = "官方消息模块",value = "官方消息模块")
public class OfficialController implements Serializable{
    @Resource
    private OfficialService officialService;
    @Resource
    private ServletContext servletContext;

    @PostMapping("/releaseMsg")
    @ApiOperation("发布官方消息")
    public Result releaseMsg(HttpSession session, @RequestBody Official official){
        User loginUser = (User)session.getAttribute("loginUser");
        if (Objects.isNull(loginUser)){
            return new Result(300,"请登录", null);
        }
        official.setUserId(loginUser.getId()); // 设置作者
        officialService.insert(official); // 保存(保存之后主键自动回填)

        /*List<Label> labels = official.getLabels();
        List<Integer> ids = new ArrayList<>();
        for (Label label : labels){
                ids.add(label.getId());
        }

        Map<String, Object> map = new HashMap<>();
        map.put("id", official.getId());*/
        //officialService.insert();

//        List<Image> images = official.getImages();
//        List<Integer> ids = new ArrayList<>(); // 所有关联图片id集合
//        for (Image image : images){
//            ids.add(image.getId());
//        }            (富文本则用不上)
//        Map<String, Object> map = new HashMap<>();
//        map.put("id", official.getId());
//        map.put("ids", ids);
//        officialService.insertRelation(map);

        return new Result(200, "发布成功", null);

    }

    @PostMapping(value = "/upload", consumes = "multipart/*", headers = "content-type=multipart/form-data")
    @ApiOperation("上传内容图片")
    public Result upload(@ApiParam(value = "上传的文件，此接口只能上传一个文件", required = true) MultipartFile multipartFile) throws IOException {
        Image image = SingleFile.upload(multipartFile, servletContext, "2");
        return new Result(200, "上传成功", image);
    }

    @GetMapping("/get")
    @ApiOperation("获取官方消息")
    public Result get(HttpSession session){
        User loginUser = (User)session.getAttribute("loginUser");
        if (Objects.isNull(loginUser)){
            return new Result(300, "请登录", null);
        }
        List<Official> officials = officialService.findAll();
        return new Result(200, "查询成功", officials);
    }
    @PostMapping("/del")
    @ApiOperation("删除") // 物理删除
    public Result del(HttpSession session, Integer id){
        User loginUser = (User)session.getAttribute("loginUser");
        if (Objects.isNull(loginUser)){
            return new Result(300, "请登录", null);
        }
        Integer del = officialService.del(id);
        if (del != 0){
            return new Result(200, "删除成功", null);
        }
        return new Result(200, "删除失败", null);
    }
}
