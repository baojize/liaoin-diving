package com.liaoin.diving.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author : huqingxi
 * @describe :
 * @date 2018/06/07
 */
@Entity
@Table(name = "t_nav")
@ApiModel("首页")
@Data
public class Nav implements Serializable {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "img_url")
    @ApiModelProperty("图片url")
    private String Imgurl;

    @Column(name = "uri")
    @ApiModelProperty("链接uri")
    private String uri;

    @Column(name = "name")
    @ApiModelProperty("名称")
    private String name;

    @Column(name = "`order`")
    @ApiModelProperty("排序")
    private Integer order;
}
