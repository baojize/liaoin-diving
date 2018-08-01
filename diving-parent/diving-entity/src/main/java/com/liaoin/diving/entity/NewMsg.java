package com.liaoin.diving.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author : huqingxi
 * @describe :  我的消息(最新点赞,评论)
 * @date 2018/06/07
 */
/*@Entity
@Table(name = "t_newmsg")
@ApiModel("我的消息")
@Getter
@Setter*/
public class NewMsg implements Serializable{

    @Id
    @GeneratedValue
    @ApiModelProperty(value = "主键", hidden = true)
    private Integer id;

    @Column(name = "foucs")
    @ApiModelProperty(value = "新的朋友  (谁关注了我)")
    private User focus;

    @Column(name = "like_user")
    @ApiModelProperty(value = "点赞 (除讨论以外的内容点赞)")
    private User likeUser;

    @Column(name = "agree")
    @ApiModelProperty(value = "被赞同 (讨论点赞)")
    private User agree;


    @Column(name = "agree")
    @ApiModelProperty(value = "最新通知(评论)")
    private Discussion discussion;

    @Column(name = "status")
    @ApiModelProperty(value = "状态, 0未读, 1已读")
    private Integer status;

    @Column(name = "create_time")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
}
