package com.liaoin.diving.vo;

import com.liaoin.diving.entity.Content;
import com.liaoin.diving.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * @author : huqingxi
 * @describe :
 * @date 2018/06/07
 */
@Getter
@Setter
public class NoticeVo {

    private User user;

    // 内容正文
    private String conText;

    // 评论内容
    private String disText;

    //创建时间
    private Date createTime;

    // 点赞用户id
    private Integer userId;

    //内容id
    private Integer contentId;

}
