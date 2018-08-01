package com.liaoin.diving.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @作者：tt
 * @描述：
 * @日期：2018/7/3 18:02
 */
@Entity
@Table(name = "t_activity_products",catalog = "diving")
@ApiModel("活动产品实体")
public class ActivityProducts implements Serializable {

    @Id
    @GeneratedValue
    @ApiModelProperty(value = "主键", hidden = true)
    private Integer id;

    @Column(name = "name")
    @ApiModelProperty(value = "名称", hidden = true)
    private String name;

    @Column(name = "price")
    @ApiModelProperty("商品原价")
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Activity.class)
    @JoinColumn(name = "activity_id")
    @JsonIgnoreProperties({"broadcastList","labelList", "activityDiscussionArrayList","activityProductsList"})
    @ApiModelProperty("活动")
    private Activity activity;

    @Column(name = "is_delete")
    @ApiModelProperty(value = "是否删除 1删除", hidden = true)
    private String isDelete;

    public ActivityProducts() {
    }

    @Override
    public String toString() {
        return "ActivityProducts{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", activity=" + activity +
                ", isDelete='" + isDelete + '\'' +
                '}';
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }
}
