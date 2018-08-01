package com.liaoin.diving.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_badge_user")
@ApiModel("徽章用户")
@Getter
@Setter
public class BadgeUser {

    @Id
    @GeneratedValue
    @ApiModelProperty(value = "主键")
    private Integer id;

    @Column(name = "badge_id")
    @ApiModelProperty(value = "徽章ID")
    private Integer badgeId;

    @Column(name = "user_id")
    @ApiModelProperty(value = "用户ID")
    private Integer userId;

    @Column(name = "create_time")
    @ApiModelProperty(value = "完成时间")
    private Date createTime;

    @Column(name = "is_delete")
    @ApiModelProperty(value = "是否删除")
    private Integer isDelete;
}
