package com.liaoin.diving.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "t_badge")
@ApiModel("徽章")
@Getter
@Setter
public class Badge {
    @Id
    @GeneratedValue
    @ApiModelProperty(value = "主键")
    private Integer id;

    @Column(name = "title")
    @ApiModelProperty(value = "标题")
    private String title;

    @Column(name = "`describe`")
    @ApiModelProperty(value = "描述")
    private String describe;

    @Column(name = "type")
    @ApiModelProperty(value = "类型")
    private String type;

    @ApiModelProperty(value = "图片地址")
    private String priUrl;

    @ApiModelProperty(value = "达标线")
    private Integer line;

    @Column(name = "is_delete")
    @ApiModelProperty(value = "是否删除")
    private Integer isDelete;
}
