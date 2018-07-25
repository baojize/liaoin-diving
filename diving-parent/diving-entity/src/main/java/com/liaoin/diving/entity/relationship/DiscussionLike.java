package com.liaoin.diving.entity.relationship;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author : huqingxi
 * @describe :
 * @date 2018/06/07
 */
@Entity
@Table(name = "t_discussion_like", catalog = "diving")
@ApiModel("评论点赞表")
@Data
public class DiscussionLike implements Serializable{
    @Id
    @GeneratedValue
    @ApiModelProperty(value = "主键", hidden = true)
    private Integer id;

    @Column(name = "user_id")
    @ApiModelProperty("用户")
    private Integer user;

    @Column(name = "discussion_id")
    @ApiModelProperty("评论")
    private Integer discussion;

    @Column(name = "create_time")
    @ApiModelProperty("创建时间")
    private Date createTime;
}
