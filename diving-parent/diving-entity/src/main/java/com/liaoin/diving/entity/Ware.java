package com.liaoin.diving.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author : huqingxi
 * @describe :
 * @date 2018/06/07
 */
@Data
@Entity
@ApiModel("商品类")
@Table(name = "t_ware")
public class Ware implements Serializable {

    @Id
    @GeneratedValue
    @ApiModelProperty("主键")
    private Integer id; // id

    @Column(name = "name")
    @ApiModelProperty("商品名")
    private String name; // 商品名

    @Column(name = "stock")
    @ApiModelProperty("库存")
    private Integer stock; // 库存

    @Column(name = "oriprice")
    @ApiModelProperty("原价")
    private BigDecimal oriprice; // 原价

    @Column(name = "price")
    @ApiModelProperty("现价")
    private BigDecimal price; //现价

    @Column(name = "discount")
    @ApiModelProperty("折扣价")
    private Integer discount; //折扣

    @Column(name = "year")
    @ApiModelProperty("产品年份")
    private Date  year; // 产品年份

    @Column(name = "use_object")
    @ApiModelProperty("使用对象")
    private Integer useObject; // 使用对象 , 1,男 , 2女   3.儿童  4.其他

    @Column(name = "using_id")
    @ApiModelProperty("适用于")
    private Integer using; // 适用于 -

    @Column(name = "dis_begin")
    @ApiModelProperty("折扣开始时间")
    private Date disBegin; // 折扣开始时间

    @Column(name = "dis_end")
    @ApiModelProperty("折扣结束时间")
    private Date disEnd; // 折扣结束时间

    @Column(name = "`describe`")
    @ApiModelProperty("商品介绍")
    private String describe;

    @Transient
    @ApiModelProperty("商品类型")
    private Integer typeId;

    @Transient
    @ApiModelProperty("商品尺寸")
    private Integer sizeId;

    @ManyToMany
    @ApiModelProperty(value = "商品分类列表",hidden = true)
    private List<Type> typeList = new ArrayList<>();


    @ManyToMany
    @ApiModelProperty(value = "商品轮播图列表",hidden = true)
    private List<Image> bannerList = new ArrayList<>();

    @Transient
    @ApiModelProperty(value = "商品尺寸列表",hidden = true)
    private List<Size> sizeList = new ArrayList<>();

/*    @Transient
    @ApiModelProperty(value = "商品介绍图列表",hidden = true)
    private List<Image> describeList = new ArrayList<>();*/

}
