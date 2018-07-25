package com.liaoin.diving.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "t_second_hand", catalog = "diving")
@ApiModel("二手实体类")
public class SecondHand implements Serializable {

    @Id
    @GeneratedValue
    @ApiModelProperty(value = "主键", hidden = true)
    private Integer id;

    @Column(name = "brand")
    @ApiModelProperty("品牌")
    private String brand;
    @Column(name = "quality")
    @ApiModelProperty("成色")
    private String quality;
    @Column(name = "price")
    @ApiModelProperty("价格")
    private String price;
    @Column(name = "description")
    @ApiModelProperty("描述")
    private String description;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Category.class)
    @JoinColumn(name = "category_id")
    @ApiModelProperty("分类")
    private Category category;

    @Column(name = "is_delete")
    @ApiModelProperty(value = "是否删除：1删除", hidden = true)
    private String isDelete;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }
}
