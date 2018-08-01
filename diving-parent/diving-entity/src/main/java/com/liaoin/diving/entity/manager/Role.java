package com.liaoin.diving.entity.manager;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author : huqingxi
 * @describe : 后台角色
 * @date 2018/06/07
 */
@Entity
@Table(name = "t_role")
@ApiModel("角色实体")
public class Role implements Serializable{
    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "name")
    @ApiModelProperty("名称")
    private String name;

    @Column(name = "create_time")
    @ApiModelProperty("创建时间")
    private Date createTime;
}
