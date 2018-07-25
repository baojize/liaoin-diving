package com.liaoin.diving.view;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
public class LikeView {
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 用户头像地址
     */
    private String headUrl;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 文章ID
     */
    private Integer contentId;
    /**
     * 文章图片
     */
    private String contentUrl;
    /**
     * 创建时间
     */
    private Date createTime;

}
