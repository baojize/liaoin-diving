package com.liaoin.diving.entity;

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
@Table(name = "t_type")
@ApiModel("商品分类实体")
@Data
public class Type implements Serializable{
    @Id
    @GeneratedValue
    @ApiModelProperty("主键")
    private Integer id;

    @Column(name = "name")
    @ApiModelProperty("名称")
    private String name;

    @Column(name = "create_time")
    @ApiModelProperty("创建时间")
    private Date createTime;

    @Column(name = "show_img_id")
    @ApiModelProperty("展示图片")
    private Integer showImg;

    @Column(name = "url")
    @ApiModelProperty("展示图片url")
    private String url;

    @Column(name = "ware_id")
    private Integer ware;

    /*@Column(name = "stock")
    @ApiModelProperty("库存")
    private Integer stock; // 库存*/
}
