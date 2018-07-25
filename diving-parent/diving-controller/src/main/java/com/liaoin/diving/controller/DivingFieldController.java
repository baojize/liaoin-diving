package com.liaoin.diving.controller;

import com.liaoin.diving.common.Result;
import com.liaoin.diving.entity.DivingField;
import com.liaoin.diving.entity.Image;
import com.liaoin.diving.file.FileUpload;
import com.liaoin.diving.service.ImageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;
import com.liaoin.diving.service.DivingFieldService;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import java.io.IOException;
import java.util.List;

/**
 * @作者：tt
 * @描述：
 * @日期：2018/6/29 11:38
 */

/**
 * 潜水场地 控制类
 */
@RestController
@RequestMapping("/divingField")
@Api(tags = "潜水场模块",value = "潜水场模块")
public class DivingFieldController {

    @Resource
    private DivingFieldService divingFieldService;

    @Resource
    private HttpServletRequest request;

    @Resource
    private ServletContext servletContext;

    @Resource
    private ImageService imageService;
    @PostMapping("/insert")
    @ApiOperation("新增")
   public Result save(@RequestBody DivingField divingField){
       divingFieldService.insert(divingField);
       return new Result(200,"新增成功",null);
   }

   @PutMapping("/update")
   @ApiOperation("修改")
    public Result update (@RequestBody DivingField divingField){
       divingFieldService.update(divingField);
       return new Result(200,"修改成功",null);
    }
    @DeleteMapping("/delete")
    @ApiOperation("删除")
    public Result delete(@RequestBody Integer[] ids){

        divingFieldService.delete(ids);
        return new Result(200,"删除成功",null);
    }

    @GetMapping("/find")
    @ApiOperation("查询单个")
    public Result findOne(@RequestParam("id") Integer id){
        DivingField divingField = divingFieldService.findOne(id);
        return new Result(200,"查询成功",divingField);
    }
    @PostMapping("/uploadFile")
    @ApiOperation("单文件上传--浅水场找照片")
    public Result uploadFile(@RequestParam String type) throws IOException {
        //文件对象
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        //路劲
        String realPath = servletContext.getRealPath("/upload/");
        //保存文件
        List<Image> images = FileUpload.upload(files,realPath,type);
        //插入数据库
        imageService.insert(images.get(0));
        return new Result(200,"上传成功",images);
    }


}
