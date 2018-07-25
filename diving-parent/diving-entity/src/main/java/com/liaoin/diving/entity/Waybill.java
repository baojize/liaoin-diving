package com.liaoin.diving.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "t_waybill", catalog = "diving")
@ApiModel("运单实体类")
@Data
public class Waybill implements Serializable {

    @Id
    @Column(name = "waybill_id")
    @ApiModelProperty("运单编号")
    private String waybillId;

    @Column(name = "express_company")
    @ApiModelProperty("快递公司")
    private String expressCompany;

    /*@ManyToOne(fetch = FetchType.EAGER, targetEntity = Order.class)
    @JoinColumn(name = "order_id")
    @JsonIgnoreProperties({"orderOptionList", "waybillList"})*/
    @Column(name = "order_id")
    @ApiModelProperty("订单")
    private Integer order;

    @Column(name = "is_delete")
    @ApiModelProperty(value = "是否删除：1删除", hidden = true)
    private String isDelete;


}
