package com.liaoin.diving.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

/**
 * 俱乐部关注列表
 */

@Entity
@Table(name = "t_group_follow")
@ApiModel("俱乐部关注列表")
@Data
public class GroupFollow {

    @Id
    @GeneratedValue
    @ApiModelProperty(value = "主键")
    private Integer id;

    @Column(name = "group_id")
    @ApiModelProperty(value = "俱乐部ID")
    private Integer groupId;

    @Column(name = "user_id")
    @ApiModelProperty(value = "用户ID")
    private Integer userId;

}
