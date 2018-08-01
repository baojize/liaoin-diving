package com.liaoin.diving.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "t_level", catalog = "diving")
@ApiModel("等级实体类")
@Getter
@Setter
public class Level implements Serializable {

    @Id
    @GeneratedValue
    @ApiModelProperty(value = "主键", hidden = true)
    private Integer id;

    @Column(name = "name")
    @ApiModelProperty("等级名称")
    private String name;
    @Column(name = "min_points")
    @ApiModelProperty("最小积分")
    private Integer minPoints;
    @Column(name = "max_points")
    @ApiModelProperty("最大积分")
    private Integer maxPoints;

    @Column(name = "is_delete")
    @ApiModelProperty(value = "是否删除：1删除", hidden = true)
    private String isDelete;

}
