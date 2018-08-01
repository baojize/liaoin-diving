package com.liaoin.diving.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @Column(name = "is_delete")
    @ApiModelProperty(value = "是否删除：1删除")
    private String isDelete;


}
