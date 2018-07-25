package com.liaoin.diving.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.liaoin.diving.entity.Content;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * @author : huqingxi
 * @describe :
 * @date 2018/06/07
 */
@Getter
@Setter
public class DiscussionVo {

    @ApiModelProperty(value = "主键", hidden = true)
    private Integer id;

    @ApiModelProperty("正文")
    private String text;

    @ApiModelProperty("点赞数 ")  // 评论点赞只记录数量
    private Integer liking;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty(value = "是否删除：1删除", hidden = true)
    private String isDelete;

    @ApiModelProperty("回复用户")
    private Integer answerUser;

    @ApiModelProperty("创建用户")
    private Integer createUser;

    @ApiModelProperty("内容")
    private Integer content;
}
