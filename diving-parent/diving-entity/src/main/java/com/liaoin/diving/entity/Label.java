package com.liaoin.diving.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "t_label", catalog = "diving")
@ApiModel("标签实体类")
@Getter
@Setter
public class Label implements Serializable {

    @Id
    @GeneratedValue
    @ApiModelProperty(value = "主键", hidden = true)
    private Integer id;

    @Column(name = "name")
    @ApiModelProperty("标签名称")
    private String name;
    @Column(name = "content")
    @ApiModelProperty("标签内容")
    private String content;

    @Column(name = "type")
    @ApiModelProperty("标签类型")
    private String type;

    @Column(name = "is_delete")
    @ApiModelProperty(value = "是否删除：1删除", hidden = true)
    private String isDelete;

   /*@Column(name = "img")
    @ApiModelProperty(value = "图片")
    private String img;*/

    /*@ManyToOne(fetch = FetchType.EAGER, targetEntity = Feuilleton.class)
    @JoinColumn(name = "feuilleton_id")
    @ApiModelProperty(value = "专栏")
    private Integer feuilletonId;*/




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Label label = (Label) o;
        return Objects.equals(id, label.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
