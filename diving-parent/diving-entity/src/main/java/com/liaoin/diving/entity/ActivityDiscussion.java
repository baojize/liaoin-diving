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
 * @日期：2018/7/3 14:23
 */
@Entity
@Table(name = "t_activity_discussion",catalog = "diving")
@ApiModel("活动评论实体")
public class ActivityDiscussion implements Serializable {

    @Id
    @GeneratedValue
    @ApiModelProperty(value = "主键", hidden = true)
    private Integer id;

    @Column(name = "text")
    @ApiModelProperty("正文")
    private String text;

    @Column(name = "create_time")
    @Temporal(TemporalType.TIMESTAMP)
    @ApiModelProperty("创建时间")
    private Date createTime;

    @Column(name = "is_delete")
    @ApiModelProperty(value = "是否删除：1删除", hidden = true)
    private String isDelete;


    @ManyToOne(fetch = FetchType.EAGER, targetEntity = User.class)
    @JoinColumn(name = "create_user_id")
    @JsonIgnoreProperties({"pointsHistoryList", "contentList", "equipmentList", "labelList", "focusList", "followList"})
    @ApiModelProperty("创建用户")
    private User createUser;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Activity.class)
    @JoinColumn(name = "activity_id")
    @JsonIgnoreProperties({"user","activityCategory","activityDiscussionArrayList","ActivityProducts"})
    @ApiModelProperty("活动")
    private Activity activity;

    public ActivityDiscussion() {
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public User getCreateUser() {
        return createUser;
    }

    public void setCreateUser(User createUser) {
        this.createUser = createUser;
    }
}
