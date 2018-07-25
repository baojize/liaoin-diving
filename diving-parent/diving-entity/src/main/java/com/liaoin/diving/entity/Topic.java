package com.liaoin.diving.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.xml.crypto.Data;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "t_topic", catalog = "diving")
@ApiModel("话题实体类")
@Getter
@Setter
public class Topic implements Serializable {

    @Id
    @GeneratedValue
    @ApiModelProperty(value = "主键", hidden = true)
    private Integer id;

    @Column(name = "text")
    @ApiModelProperty("描述")
    private String text;

    @Column(name = "is_delete")
    @ApiModelProperty(value = "是否删除：1删除", hidden = true)
    private String isDelete;

    @Column(name = "is_column")
    @ApiModelProperty(value = "是否设置专栏, 1 :是 0: 否(default)", hidden = true)
    private String isColumn;

    @Column(name = "img")
    @ApiModelProperty(value = "图片")
    private String img;

    @Column(name = "create_time")
    @ApiModelProperty(value = "创建时间", hidden = true)
    private Date createTime;

    @Column(name = "name")
    @ApiModelProperty(value = "话题名称")
    private String name;


}
