package com.liaoin.diving.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 积分商品
 */
@Entity
@Table(name = "t_commodity", catalog = "diving")
@Document(indexName = "diving", type = "commodity")
@ApiModel("积分商品")
@Getter
@Setter
public class Commodity implements Serializable {

    @Id
    @GeneratedValue
    @ApiModelProperty(value = "主键")
    private Integer id;

    @Column(name = "`name`")
    @ApiModelProperty(value = "商品名称")
    private String name;

    @Column(name = "money")
    @ApiModelProperty("订单金额")
    private BigDecimal money;

    @ApiModelProperty(value = "积分")
    private Integer points;

    @Column(name = "`explain`")
    @ApiModelProperty(value = "使用说明")
    private String explain;

    @ApiModelProperty(value = "图片地址")
    private String priUrl;

    @Column(name = "is_delete")
    @ApiModelProperty(value = "是否删除")
    private Integer isDelete;
}
