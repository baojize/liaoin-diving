package com.liaoin.diving.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "t_template", catalog = "diving")
@ApiModel("模板实体类")
public class Template implements Serializable {

    @Id
    @GeneratedValue
    @ApiModelProperty(value = "主键", hidden = true)
    private Integer id;

    @Column(name = "name")
    @ApiModelProperty("模板名称")
    private String name;

    @Column(name = "is_delete")
    @ApiModelProperty(value = "是否删除：1删除", hidden = true)
    private String isDelete;

    @ManyToMany(fetch = FetchType.LAZY, targetEntity = Brand.class)
    @JoinTable(name = "m_template_brand",
            joinColumns = {@JoinColumn(name = "template_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "brand_id", referencedColumnName = "id")})
    @ApiModelProperty("品牌列表")
    private List<Brand> brandList = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY, targetEntity = Spec.class)
    @JoinTable(name = "m_template_spec",
            joinColumns = {@JoinColumn(name = "template_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "spec_id", referencedColumnName = "id")})
    @ApiModelProperty("规格列表")
    private List<Spec> specList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Category.class)
    @JoinColumn(name = "category_id")
    @JsonIgnoreProperties({"templateList"})
    @ApiModelProperty("分类")
    private Category category;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public List<Brand> getBrandList() {
        return brandList;
    }

    public void setBrandList(List<Brand> brandList) {
        this.brandList = brandList;
    }

    public List<Spec> getSpecList() {
        return specList;
    }

    public void setSpecList(List<Spec> specList) {
        this.specList = specList;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
