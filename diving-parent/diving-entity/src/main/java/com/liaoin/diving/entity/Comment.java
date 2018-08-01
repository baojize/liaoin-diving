package com.liaoin.diving.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @作者：tt
 * @描述：
 * @日期：2018/6/28 16:51
 */
@Entity
@Table(name = "t_comment", catalog = "diving")
@ApiModel("潜水场点评实体类")
public class Comment implements Serializable{

    @Id
    @GeneratedValue
    @ApiModelProperty(value = "主键", hidden = true)
    private Integer id;

    @Column(name = "infrastructure")
    @ApiModelProperty("基础设施")
    private String infrastructure;

    @Column(name = "water")
    @ApiModelProperty("水质")
    private String water;

    @Column(name = "service")
    @ApiModelProperty("服务")
    private String service;

    @Column(name = "suit")
    @ApiModelProperty("适合人群")
    private String suit;

    @Column(name = "message")
    @ApiModelProperty("评论内容")
    private String message;

    @Column(name = "play_time")
    @ApiModelProperty("评论内容")
    private Date playTime;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = DivingField.class)
    @JoinColumn(name = "diving_field_id")
    @JsonIgnoreProperties({"divingFieldList", "commentList"})
    @ApiModelProperty("潜水场")
    private DivingField divingField;

    @Column(name = "is_delete")
    @ApiModelProperty(value = "是否删除：1删除", hidden = true)
    private String isDelete;

    @Column(name = "create_time")
    @ApiModelProperty(value = "创造时间", hidden = true)
    private Date createTime;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Comment() {
    }

    public DivingField getDivingField() {
        return divingField;
    }

    public void setDivingField(DivingField divingField) {
        this.divingField = divingField;
    }

    public Integer getId() {

        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInfrastructure() {
        return infrastructure;
    }

    public void setInfrastructure(String infrastructure) {
        this.infrastructure = infrastructure;
    }

    public String getWater() {
        return water;
    }

    public void setWater(String water) {
        this.water = water;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getSuit() {
        return suit;
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getPlayTime() {
        return playTime;
    }

    public void setPlayTime(Date playTime) {
        this.playTime = playTime;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }
}
