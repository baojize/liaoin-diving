package com.liaoin.diving.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 * @作者：
 * @描述：
 * @日期：2018/6/28 16:37
 */
@Entity
@Table(name = "t_diving_field", catalog = "diving")
@ApiModel("潜水场实体类")
public class DivingField implements Serializable {
    @Id
    @GeneratedValue
    @ApiModelProperty(value = "主键", hidden = true)
    private Integer id;

    @Column(name = "name")
    @ApiModelProperty("潜水场名称")
    private String name;

    @Column(name = "suggest")
    @ApiModelProperty("潜水场介绍")
    private String suggest;

    @Column(name = "open_time")
    @ApiModelProperty("开放时间")
    private String OpenTime;

    @Column(name = "address")
    @ApiModelProperty("潜水场地址")
    private String address;

    @Column(name = "phone")
    @ApiModelProperty("潜水场电话")
    private String phone;
    @Column(name = "create_time")
    @ApiModelProperty(value = "创造时间", hidden = true)
    private Date createTime;

    @Column(name = "update_time")
    @ApiModelProperty(value = "修改时间", hidden = true)
    private Date updateTime;

    @Column(name = "is_delete")
    @ApiModelProperty(value = "是否删除：1删除", hidden = true)
    private String isDelete;


//    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Image.class)
//    @JoinColumn(name = "image_id")
//    @ApiModelProperty("潜水馆图片图片")
    private Image image;

    @OneToMany(fetch = FetchType.LAZY,cascade={CascadeType.PERSIST} , targetEntity = DivingTicket.class, mappedBy = "divingField")
    @JsonIgnoreProperties({"divingField"})
    @ApiModelProperty("门票列表")
    private List<DivingTicket> divingTicketList= new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, targetEntity = DivingTicket.class, mappedBy = "divingField")
    @JsonIgnoreProperties({"divingField"})
    @ApiModelProperty("点评列表")
    private List<Comment> commentList = new ArrayList<>();

    public DivingField() {
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void addDivingTicket(DivingTicket divingTicket){
        divingTicketList.add(divingTicket);
    }
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

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
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

    public String getSuggest() {
        return suggest;
    }

    public void setSuggest(String suggest) {
        this.suggest = suggest;
    }

    public String getOpenTime() {
        return OpenTime;
    }

    public void setOpenTime(String openTime) {
        OpenTime = openTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<DivingTicket> getDivingTicketList() {
        return divingTicketList;
    }

    public void setDivingTicketList(List<DivingTicket> divingTicketList) {
        this.divingTicketList = divingTicketList;
    }


    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }
}
