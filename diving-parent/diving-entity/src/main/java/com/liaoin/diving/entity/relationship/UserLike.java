package com.liaoin.diving.entity.relationship;

import com.liaoin.diving.entity.Content;
import com.liaoin.diving.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author : huqingxi
 * @describe : 用户点赞表    ()
 * @date 2018/06/07
 */
@Entity
@Table(name = "t_user_like", catalog = "diving")
@ApiModel("点赞表")
@Getter
@Setter
public class UserLike implements Serializable{
    @Id
    @GeneratedValue
    @ApiModelProperty(value = "主键", hidden = true)
    private Integer id;

    @Column(name = "user_id")
    @ApiModelProperty("用户")
    private Integer user;

    @Column(name = "content_id")
    @ApiModelProperty("内容")
    private Integer content;

    @Column(name = "create_time")
    @ApiModelProperty("创建时间")
    private Date createTime;



}
