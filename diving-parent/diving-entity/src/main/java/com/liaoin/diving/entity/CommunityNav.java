package com.liaoin.diving.entity;

import com.liaoin.diving.entity.Order;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author : huqingxi
 * @describe :
 * @date 2018/06/07
 */
@Data
@Entity
@Table(name = "t_community_nav", catalog = "diving")
@ApiModel(value = "社区导航栏实体")
public class CommunityNav implements Serializable{
    @Id
    @GeneratedValue
    @ApiModelProperty("主键")
    private Integer id;

    @Column(name = "`name`")
    @ApiModelProperty("名称")
    private String name;

    @Column(name = "create_time")
    @ApiModelProperty("创建时间")
    private Date createTime;

    @Column(name = "`order`")
    @ApiModelProperty("排序")
    private Integer order;

    @Column(name = "is_hidden")
    @ApiModelProperty("是否显示 0:不显示(默认)  1:显示")
    private Integer isHidden;
}
