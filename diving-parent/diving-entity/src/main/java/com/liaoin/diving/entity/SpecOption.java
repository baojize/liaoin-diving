package com.liaoin.diving.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "t_spec_option", catalog = "diving")
@ApiModel("规格选项实体类")
public class SpecOption implements Serializable {

    @Id
    @GeneratedValue
    @ApiModelProperty(value = "主键", hidden = true)
    private Integer id;

    @Column(name = "name")
    @ApiModelProperty("规格选项名称")
    private String name;

    @Column(name = "is_delete")
    @ApiModelProperty(value = "是否删除：1删除", hidden = true)
    private String isDelete;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Spec.class)
    @JoinColumn(name = "spec_id")
    @JsonIgnoreProperties({"specOptionList"})
    @ApiModelProperty("规格")
    private Spec spec;

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

    public Spec getSpec() {
        return spec;
    }

    public void setSpec(Spec spec) {
        this.spec = spec;
    }
}
