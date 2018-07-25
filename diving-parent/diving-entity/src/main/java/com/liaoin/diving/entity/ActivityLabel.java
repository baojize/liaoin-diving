package com.liaoin.diving.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.naming.ldap.PagedResultsControl;
import javax.persistence.*;
import java.util.Date;

/**
 * @author : huqingxi
 * @describe :
 * @date 2018/06/07
 */
@Entity
@Table(name = "t_activity_slable",catalog = "diving")
@ApiModel("活动小标签")
@Getter
@Setter
public class ActivityLabel {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "name")
    @ApiModelProperty("名字")
    private String name;

    @Column(name = "create_time")
    @ApiModelProperty("创建时间")
    private Date createTime;

    @Column(name = "activity_id")
    @ApiModelProperty("创建时间")
    private Integer activity;
}
