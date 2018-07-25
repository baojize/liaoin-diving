package com.liaoin.diving.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @作者：tt
 * @描述：
 * @日期：2018/7/3 11:46
 */
@Entity
@Table(name = "t_activity_category",catalog = "diving")
@ApiModel("活动分类实体")
public class ActivityCategory implements Serializable {

    @Id
    @GeneratedValue
    @ApiModelProperty(value = "主键", hidden = true)
    private Integer id;

    @Column(name = "name")
    @ApiModelProperty(value = "名称", hidden = true)
    private String name;
    @Column(name = "is_delete")
    @ApiModelProperty(value = "是否删除 1删除", hidden = true)
    private String isDelete;

    @Column(name = "create_time")
    @Temporal(TemporalType.TIMESTAMP)
    @ApiModelProperty(value = "创建时间", hidden = true)
    private Date createTime;
    @Column(name = "update_time")
    //@Temporal(TemporalType.TIMESTAMP)——>实体类会封装成完整的时间“yyyy-MM-dd hh:MM:ss”的 Date类型。
    @Temporal(TemporalType.TIMESTAMP)
    @ApiModelProperty(value = "修改时间", hidden = true)
    private Date updateTime;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, targetEntity = Activity.class, mappedBy = "activityCategory")
    @ApiModelProperty(value = "活动列表", hidden = true)
    //@JsonIgnoreProperties({"user","activityCategory","ActivityDiscussion","ActivityProducts"}) activity 的这些属性不会持久化
    @JsonIgnoreProperties({"user","activityCategory","activityDiscussionArrayList","activityProductsList"})
    private List<Activity> activityList = new ArrayList<>();

    public ActivityCategory() {
    }

    public List<Activity> getActivityList() {
        return activityList;
    }

    public void setActivityList(List<Activity> activityList) {
        this.activityList = activityList;
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

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
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
}
