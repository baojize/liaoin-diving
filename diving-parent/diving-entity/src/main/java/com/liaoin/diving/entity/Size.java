package com.liaoin.diving.entity;

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
@Entity
@Table(name = "t_size")
@ApiModel("商品尺寸实体")
@Data
public class Size implements Serializable {
    @Id
    @GeneratedValue
    @ApiModelProperty("主键")
    private Integer id;

    @Column(name = "name")
    @ApiModelProperty("名称")
    private String name;

    @Column(name = "create_time")
    @ApiModelProperty("创建时间")
    private Date createTime;
}
