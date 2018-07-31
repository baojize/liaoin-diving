package com.liaoin.diving.entity.relationship;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author : huqingxi
 * @describe :
 * @date 2018/06/07
 */
@Entity
@Table(name = "t_type_size")
@ApiModel("商品尺寸关系 实体")
@Data
public class TypeSize implements Serializable {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "type_id")
    @ApiModelProperty("商品类型")
    private Integer type;

    @Column(name = "size_id")
    @ApiModelProperty("商品尺寸")
    private Integer size;

    @Column(name = "stock")
    @ApiModelProperty("商品库存")
    private Integer stock;

    @Column(name = "ware_id")
    @ApiModelProperty("商品id")
    private Integer ware;
}
