package com.liaoin.diving.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author : huqingxi
 * @describe :
 * @date 2018/06/07
 */
@Entity
@Table(name = "t_theme")
@ApiModel(value = "干货主题类")
@Data
public class Theme {

    @Id
    @ApiModelProperty("主键")
    private Integer id;

    @Column(name = "name")
    @ApiModelProperty("主题名")
    private String name;

}
