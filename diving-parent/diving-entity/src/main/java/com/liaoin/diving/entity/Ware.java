package com.liaoin.diving.entity;

import com.liaoin.diving.Size;
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
    @ApiModelProperty("现价")
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

    @Transient
    @ApiModelProperty("商品尺寸列表")
    private List<Size> sizeList = new ArrayList<>();

    @Transient
    @ApiModelProperty("商品分类列表")
    private List<Type> typeList = new ArrayList<>();

    @Transient
    @ApiModelProperty("商品轮播图列表")
    private List<Image> bannerList = new ArrayList<>();

    @Transient
    @ApiModelProperty("商品介绍图列表")
    private List<Image> describeList = new ArrayList<>();

   /* @Column(name = "describe_img_id")
    @ApiModelProperty("商品介绍图")
    private Integer describeImg; //商品介绍图  -

    @Column(name = "banner_img_id")
    @ApiModelProperty("商品轮播图")
    private Integer bannerImg; // 商品轮播图  -*/

    /*@Column(name = "type_id")
    @ApiModelProperty("商品分类")
    private Integer type; // 商品分类 -

    @Column(name = "size_id")
    @ApiModelProperty("商品尺寸")
    private Integer size; // 尺寸 -
*/




}
