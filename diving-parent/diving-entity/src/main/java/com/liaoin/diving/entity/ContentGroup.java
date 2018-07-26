package com.liaoin.diving.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_content_group")
@ApiModel("内容 俱乐部 关系表")
@Data
public class ContentGroup {

    @Id
    @GeneratedValue
    @ApiModelProperty(value = "主键")
    private Integer id;

    @Column(name = "content_id")
    @ApiModelProperty(value = "内容ID")
    private Integer contentId;

    @Column(name = "group_id")
    @ApiModelProperty(value = "俱乐部ID")
    private Integer groupId;

}
