package com.liaoin.diving.view;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author : huqingxi
 * @describe : 最新评论view
 * @date 2018/06/07
 */
@Getter
@Setter
public class NoticeView {
    /**
     * 评论创建时间
     */
    private Date createTime;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 用户头像
     */
    private String imgUrl;

    /**
     *内容正文
     */
    private String contentText;

    /**
     *评论正文
     */
    private String disText;

}
