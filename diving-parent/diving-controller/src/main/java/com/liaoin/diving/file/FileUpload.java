package com.liaoin.diving.file;

import com.liaoin.diving.common.Result;
import com.liaoin.diving.entity.Image;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @作者：hqx
 * @描述：
 * @日期：2018/7/3 10:12
 */
@Slf4j
public class FileUpload {

    public static List<Image> upload(List<MultipartFile> files,String realPath,String type) throws IOException {

        // 1.创建文件集合
        List<Image> imageList = new ArrayList<>();
        // 2.上传文件，存入数据库
//        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        for (MultipartFile multipartFile : files) {
            // 2.1 获取文件名
            String originalFilename = multipartFile.getOriginalFilename();
            String filename = UUID.randomUUID().toString() + originalFilename.substring(originalFilename.lastIndexOf("."));
            // 2.2 创建上传文件夹
//            String realPath = servletContext.getRealPath("/upload/");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
            String datePath = simpleDateFormat.format(new Date());
            File dir = new File(realPath + datePath);
            if (!dir.exists()) {
                boolean b = dir.mkdirs();
                log.info("创建上传文件夹" + dir.getPath() + "：" + b);
            }
            // 2.3 上传文件
            File file = new File(dir, filename);

            FileCopyUtils.copy(multipartFile.getBytes(), file);
            // 2.4 新增文件信息
            Image image = new Image();
            image.setContent(originalFilename);
            image.setType(type);
            image.setUrl("/upload/" + datePath + "/" + filename);

            log.info("新增文件信息：" + image.getUrl());

            // 2.5 添加文件到集合中
            imageList.add(image);
        }
        // 3.返回文件集合
       return imageList;
    }
}
