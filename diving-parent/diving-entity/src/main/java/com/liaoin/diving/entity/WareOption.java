package com.liaoin.diving.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "t_ware_option", catalog = "diving")
@ApiModel("商品明细实体类")
@Data
public class WareOption implements Serializable {

    @Id
    @GeneratedValue
    @ApiModelProperty(value = "主键", hidden = true)
    private Integer id;

    @Column(name = "ware_id")
    @ApiModelProperty("商品")
    private Ware ware;

    @Column(name = "num")
    @ApiModelProperty("数量")
    private BigDecimal num;

    @Column(name = "money")
    @ApiModelProperty("小计")
    private BigDecimal money;

    @Column(name = "is_delete")
    @ApiModelProperty(value = "是否删除：1删除", hidden = true)
    private String isDelete;
}
