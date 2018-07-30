package com.liaoin.diving.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;

@Entity
@Table(name = "t_commodity_order", catalog = "diving")
@Document(indexName = "diving", type = "commodityOrder")
@ApiModel("积分商品订单")
@Getter
@Setter
public class CommodityOrder {
    @Id
    @GeneratedValue
    @ApiModelProperty(value = "主键")
    private Integer id;

    @Column(name = "commodity_id")
    @ApiModelProperty(value = "商品ID")
    private Integer commodityId;


    @Column(name = "user_id")
    @ApiModelProperty(value = "用户ID")
    private Integer userId;

    @ApiModelProperty(value = "订单状态")
    private Integer status;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Address.class)
    @ApiModelProperty(value = "收货地址")
    private Address address;

    @Column(name = "is_delete")
    @ApiModelProperty(value = "是否删除")
    private Integer isDelete;
}
