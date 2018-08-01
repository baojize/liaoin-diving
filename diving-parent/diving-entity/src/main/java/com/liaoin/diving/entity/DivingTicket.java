package com.liaoin.diving.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @作者：
 * @描述：
 * @日期：2018/6/28 16:41
 */
@Entity
@Table(name = "t_diving_ticket", catalog = "diving")
@ApiModel("潜水场门票实体类")
public class DivingTicket implements Serializable{

    @Id
    @GeneratedValue
    @ApiModelProperty(value = "主键", hidden = true)
    private Integer id;

    @Column(name = "name")
    @ApiModelProperty("门票名称")
    private String name;
    @Column(name = "type")
    @ApiModelProperty("门票类型")
    private String type;
    @Column(name = "tips")
    @ApiModelProperty("门票提示")
    private String tips;

    @Column(name = "price")
    @ApiModelProperty("商品原价")
    private BigDecimal price;

    @Column(name = "discount")
    @ApiModelProperty("商品折扣")
    private BigDecimal discount;

    @Column(name = "using_time")
    @ApiModelProperty("使用时间")
    private String usingTime;

    @Column(name = "buy_time")
    @ApiModelProperty("购买时间")
    private String buyTime;

    @Column(name = "ticket")
    @ApiModelProperty("取票方式")
    private String ticket;

    @Column(name = "go_diving")
    @ApiModelProperty("入场方式")
    private String goDiving;

    @Column(name = "using_rule")
    @ApiModelProperty("使用规则")
    private String usingRule;

    @Column(name = "included")
    @ApiModelProperty("费用包含")
    private String included;

    @Column(name = "no_included")
    @ApiModelProperty("费用不包含")
    private String notIncluded;

    @Column(name = "not_included")
    @ApiModelProperty("退票规则")
    private String  refundable;

    @Column(name = "create_time")
    @ApiModelProperty(value = "创造时间", hidden = true)
    private Date createTime;

    @Column(name = "update_time")
    @ApiModelProperty(value = "修改时间", hidden = true)
    private Date updateTime;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = DivingField.class)
    @JoinColumn(name = "diving_field_id")
    @JsonIgnoreProperties({"divingFieldList", "commentList"})
    @ApiModelProperty("潜水场")
    private DivingField divingField;

    @Column(name = "is_delete")
    @ApiModelProperty(value = "是否删除：1删除", hidden = true)
    private String isDelete;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public DivingTicket() {
    }

    public Integer getId() {

        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public String getUsingTime() {
        return usingTime;
    }

    public void setUsingTime(String usingTime) {
        this.usingTime = usingTime;
    }

    public String getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(String buyTime) {
        this.buyTime = buyTime;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getGoDiving() {
        return goDiving;
    }

    public void setGoDiving(String goDiving) {
        this.goDiving = goDiving;
    }

    public String getUsingRule() {
        return usingRule;
    }

    public void setUsingRule(String usingRule) {
        this.usingRule = usingRule;
    }

    public String getIncluded() {
        return included;
    }

    public void setIncluded(String included) {
        this.included = included;
    }

    public String getNotIncluded() {
        return notIncluded;
    }

    public void setNotIncluded(String notIncluded) {
        this.notIncluded = notIncluded;
    }

    public String getRefundable() {
        return refundable;
    }

    public void setRefundable(String refundable) {
        this.refundable = refundable;
    }

    public DivingField getDivingField() {
        return divingField;
    }

    public void setDivingField(DivingField divingField) {
        this.divingField = divingField;
    }
}
