package com.liaoin.diving.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "t_ware_order", catalog = "diving")
@ApiModel("订单实体类")
@Data
public class WareOrder implements Serializable{

    @Id
    @Column(name = "order_id")
    @ApiModelProperty(value = "订单编号")
    private String orderId;

    @Column(name = "num")  // WareOption 数量
    @ApiModelProperty(value = "商品数量", hidden = true)
    private BigDecimal num;

    @Column(name = "money") // *
    @ApiModelProperty(value = "订单金额", hidden = true)
    private BigDecimal money;

    @Column(name = "postage")
    @ApiModelProperty(value = "订单邮费")
    private BigDecimal postage;

    @Column(name = "amount")
    @ApiModelProperty("订单总金额=订单金额+订单邮费")
    private BigDecimal amount;

    @Column(name = "pay_way")
    @ApiModelProperty("支付方式：1支付宝支付，2微信支付")
    private String payWay;

    @Column(name = "third_pay_no")
    @ApiModelProperty(value = "三方支付编号", hidden = true)
    private String thirdPayNo;

    @Column(name = "message")
    @ApiModelProperty("买家留言")
    private String message;

    @Column(name = "status")
    @ApiModelProperty("状态：1、未付款，2、未发货，3、待收货，4、已完成")
    private String status;

    @Column(name = "create_time")
    @ApiModelProperty("创建时间")
    private Date createTime;

    @Column(name = "update_time")
    @ApiModelProperty("更新时间")
    private Date updateTime;

    @Column(name = "pay_time")
    @ApiModelProperty("付款时间")
    private Date payTime;

    @Column(name = "delivery_time")
    @ApiModelProperty("发货时间")
    private Date deliveryTime;

    @Column(name = "end_time")
    @ApiModelProperty("交易完成时间")
    private Date endTime;

    @Column(name = "close_time")
    @ApiModelProperty("交易关闭时间")
    private Date closeTime;

    @Column(name = "is_delete")
    @ApiModelProperty(value = "是否删除：1删除", hidden = true)
    private String isDelete;

    @Column(name = "user_id")
    @ApiModelProperty("用户")
    private Integer user;

    @JoinColumn(name = "address_id")
    @ApiModelProperty("收货地址")
    private Integer address;

    @Transient
    @ApiModelProperty("订单明细集合")
    private List<WareOption> wareOptionList;

    @Transient
    @ApiModelProperty("运单集合")
    private List<Waybill> waybillList = new ArrayList<>();
}
