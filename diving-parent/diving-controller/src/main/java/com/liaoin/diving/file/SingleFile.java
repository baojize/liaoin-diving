package com.liaoin.diving.file;

import com.liaoin.diving.common.Result;
import com.liaoin.diving.entity.Image;
import com.liaoin.diving.service.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author : huqingxi
 * @describe : 上传单个文件
 * @date 2018/06/07
 */
@Slf4j
public class SingleFile {

    private static Logger logger = LoggerFactory.getLogger(SingleFile.class);
    //  private static Logger logger = LoggerFactory.getLogger(ContentController.class);

    public static Image upload(MultipartFile multipartFile, ServletContext servletContext, String type) throws IOException {
        // 1.创建文件集合
        List<Image> imageList = new ArrayList<>();
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
        image.setType(type);
        image.setUrl("/upload/" + datePath + "/" + filename);

        log.info("新增文件信息：" + image.getUrl());

        // 2.5 添加文件到集合中
        imageList.add(image);
        return image;
    }

}
