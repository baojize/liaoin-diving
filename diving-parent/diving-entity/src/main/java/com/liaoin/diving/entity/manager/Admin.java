package com.liaoin.diving.entity.manager;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author : huqingxi
 * @describe : 后台管理
 * @date 2018/06/07
 */
@Entity
@Table(name = "t_admin")
@ApiModel("管理员实体")
@Data
public class Admin implements Serializable{
    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "account")
    @ApiModelProperty("账号")
    private String account;

    @Column(name = "password")
    @ApiModelProperty("密码")
    private String password;

    @Column(name = "nickname")
    @ApiModelProperty("昵称")
    private String nickname;

    @Column(name = "head")
    @ApiModelProperty("头像")
    private String head;

    @Column(name = "phone")
    @ApiModelProperty("电话")
    private String phone;

    @Column(name = "create_time")
    @ApiModelProperty("创建时间")
    private Date create_time;
}
