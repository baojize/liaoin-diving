package com.liaoin.diving.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "t_address", catalog = "diving")
@ApiModel("收货地址实体类")
@Getter
@Setter
public class Address implements Serializable{

    @Id
    @GeneratedValue
    @ApiModelProperty(value = "主键", hidden = true)
    private Integer id;

    @Column(name = "receiver_name")
    @ApiModelProperty("收货人姓名")
    private String receiverName;

    @Column(name = "receiver_mobile")
    @ApiModelProperty("收货人手机")
    private String receiverMobile;

    @Column(name = "receiver_address")
    @ApiModelProperty("收货人详细地址")
    private String receiverAddress;

    @Column(name = "receiver_area")
    @ApiModelProperty("所在地区")
    private String area;

    @Column(name = "is_default")
    @ApiModelProperty("是否为默认地址 0:否 , 1是")
    private Integer isDefault;

    @Column(name = "user_id")
    @ApiModelProperty("用户")
    private Integer userId;


    /*@ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"pointsHistoryList", "contentList", "equipmentList", "orderList", "addressList", "labelList", "focusList", "followList","collectList","downloVideoList"})
    @ApiModelProperty("用户")*/


    @Column(name = "is_delete")
    @ApiModelProperty(value = "是否删除：1删除", hidden = true)
    private Integer isDelete;

}
