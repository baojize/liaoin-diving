package com.liaoin.diving.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "t_order", catalog = "diving")
@ApiModel("订单实体类")
@Getter
@Setter
public class Order implements Serializable {

    @Id
    @Column(name = "order_id")
    @ApiModelProperty("订单编号")
    private String orderId;

    @Column(name = "num")  // *
    @ApiModelProperty("商品数量")
    private BigDecimal num;

    @Column(name = "money") // *
    @ApiModelProperty("订单金额")
    private BigDecimal money;

    @Column(name = "postage")
    @ApiModelProperty("订单邮费")
    private BigDecimal postage;

    @Column(name = "amount")
    @ApiModelProperty("订单总金额=订单金额+订单邮费")
    private BigDecimal amount;

    @Column(name = "pay_way")
    @ApiModelProperty("支付方式：1支付宝支付，2微信支付")
    private String payWay;

    @Column(name = "third_pay_no")
    @ApiModelProperty("三方支付编号")
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

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Address.class)
    @JoinColumn(name = "address_id")
    @ApiModelProperty("收货地址")
    private Address address;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER, targetEntity = User.class)
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"pointsHistoryList", "contentList", "equipmentList", "orderList", "addressList", "labelList", "focusList", "followList"})
    @ApiModelProperty("用户")
    private User user;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, targetEntity = OrderOption.class, mappedBy = "order")
    @JsonIgnoreProperties({"order"})
    @ApiModelProperty("订单明细集合")
    private List<OrderOption> orderOptionList = new ArrayList<>();

    /*@OneToMany(fetch = FetchType.LAZY, targetEntity = Waybill.class, mappedBy = "order")
    @JsonIgnoreProperties({"order"})
    @ApiModelProperty("运单集合")
    private List<Waybill> waybillList = new ArrayList<>();*/







    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(orderId, order.orderId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(orderId);
    }
}
