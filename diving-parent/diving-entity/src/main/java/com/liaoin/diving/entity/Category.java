package com.liaoin.diving.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "t_category", catalog = "diving")
@ApiModel("分类实体类")
public class Category implements Serializable {

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

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = BigCategory.class)
    @JoinColumn(name = "big_category_id")
    @JsonIgnoreProperties("categoryList")
    @ApiModelProperty("内容")
    private BigCategory bigCategory;

    @OneToMany(fetch = FetchType.EAGER, targetEntity = Template.class, mappedBy = "category")
    @JsonIgnoreProperties({"category"})
    @ApiModelProperty("模板列表")
    private Set<Template> templateList = new HashSet<>();

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

    public Set<Template> getTemplateList() {
        return templateList;
    }

    public void setTemplateList(Set<Template> templateList) {
        this.templateList = templateList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Category category = (Category) o;
        return Objects.equals(id, category.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }

    public BigCategory getBigCategory() {
        return bigCategory;
    }

    public void setBigCategory(BigCategory bigCategory) {
        this.bigCategory = bigCategory;
    }
}
