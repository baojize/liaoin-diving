package com.liaoin.diving.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @作者：tt
 * @描述：
 * @日期：2018/7/5 14:39
 * @OneToMany(targetEntity=SmallProductClass.class,mappedBy="BigProductClass"),mappedBy指向的是要关联的属性，而不是要关联的类
 */
@Entity
@Table(name = "t_big_category", catalog = "diving")
@ApiModel("大分类实体")
@Setter
@Getter
public class BigCategory implements Serializable{

    @Id
    @GeneratedValue
    @ApiModelProperty(value = "主键", hidden = true)
    private Integer id;

    @Column(name = "name")
    @ApiModelProperty("分类名称")
    private String name;

    @Column(name = "is_delete")
    @ApiModelProperty(value = "是否删除：1删除", hidden = true)
    private String isDelete;

    @OneToMany(fetch = FetchType.LAZY, targetEntity = Category.class, mappedBy = "bigCategory")
    @JsonIgnoreProperties({"bigCategory"})
    @ApiModelProperty(value = "分类列表", hidden = false)
    private List<Category> categoryList = new ArrayList<>();





}
