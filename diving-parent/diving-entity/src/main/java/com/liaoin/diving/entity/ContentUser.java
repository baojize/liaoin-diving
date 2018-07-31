package com.liaoin.diving.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * 点赞列表
 */
@Entity
@Table(name = "m_content_user")
@ApiModel("内容 用户关系表")
@Data
public class ContentUser implements Serializable {

    @Id
    @GeneratedValue
    @ApiModelProperty(value = "主键")
    private Integer id;

    @Column(name = "content_id")
    @ApiModelProperty(value = "内容ID")
    private Integer contentId;

    @Column(name = "user_id")
    @ApiModelProperty(value = "用户ID")
    private Integer userId;

    @Column(name = "create_time")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
}
