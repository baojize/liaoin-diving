package com.liaoin.diving.controller;

import com.liaoin.diving.common.Result;
import com.liaoin.diving.entity.Image;
import com.liaoin.diving.file.SingleFile;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import java.io.IOException;

/**
 * @author : huqingxi
 * @describe :
 * @date 2018/06/07
 */
@RestController
@RequestMapping("/upload")
@Api(tags = "图片上传",value = "图片上传")
public class UploadController {
    @Resource
    private ServletContext servletContext;

    @PostMapping("/upload")
    @ApiOperation("单个图片上传")
    public Result upload(MultipartFile multipartFile, String type)  {
        try {
            Image image = SingleFile.upload(multipartFile, servletContext, type);
            return new Result(200, "上传成功", image);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(200, "系统错误,上传失败", null);
        }

    }
}
