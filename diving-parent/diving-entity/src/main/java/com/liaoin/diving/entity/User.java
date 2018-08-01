package com.liaoin.diving.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "t_user", catalog = "diving")
@ApiModel("用户实体类")
@Getter
@Setter
public class User implements Serializable {

    @Id
    @GeneratedValue
    @ApiModelProperty(value = "主键", hidden = true)
    private Integer id;

    @Column(name = "mobile")
    @ApiModelProperty("手机")
    private String mobile;

    @Column(name = "password")
    @ApiModelProperty("密码")
    private String password;

    @Column(name = "nickname")
    @ApiModelProperty("昵称")
    private String nickname;

    @Column(name = "sex")
    @ApiModelProperty("性别：0女，1男")
    private String sex;

    @Column(name = "birthday")
    @Temporal(TemporalType.DATE)
    @ApiModelProperty("生日")
    private Date birthday;

    @Column(name = "signature")
    @ApiModelProperty("签名")
    private String signature;

    @Column(name = "haunt")
    @ApiModelProperty("经常出没")
    private String haunt;

    @Column(name = "points")
    @ApiModelProperty(value = "积分", hidden = true)
    private Integer points;

    @Column(name = "create_time")
    @Temporal(TemporalType.TIMESTAMP)
    @ApiModelProperty(value = "注册时间", hidden = true)
    private Date createTime;

    @Column(name = "update_time")
    @Temporal(TemporalType.TIMESTAMP)
    @ApiModelProperty(value = "修改时间", hidden = true)
    private Date updateTime;

    @Column(name = "is_delete")
    @ApiModelProperty(value = "是否删除：1删除", hidden = true)
    private String isDelete;

    @Column(name = "follow_num")
    @ApiModelProperty(value = "最新关注数量", hidden = true)
    private Integer FollowNum;

    @Column(name = "like_num")
    @ApiModelProperty(name = "最新点赞数量")
    private Integer likeNum;

    @Column(name = "agree_num")
    @ApiModelProperty(name = "赞同数量")
    private Integer agreeNum;

    @Column(name = "notice_num")
    @ApiModelProperty(name = "最新通知")  // 评论数量
    private Integer noticeNum;

    @Column(name = "group_id")
    @ApiModelProperty(name = "俱乐部ID")
    private Integer groupId;

   /* @Column(name = "status")
    @ApiModelProperty(name = "最新数据状态, 0未读, 1 已读")
    private Integer status;*/

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Image.class)   // baseType
    @JoinColumn(name = "image_id")
    @ApiModelProperty("头像")
    private Image image;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Level.class)  //baseType
    @JoinColumn(name = "level_id")
    @ApiModelProperty(value = "等级", hidden = true)
    private Level level;

    @OneToMany(fetch = FetchType.LAZY, targetEntity = PointsHistory.class, mappedBy = "user")
    @JsonIgnoreProperties({"user"})
    @ApiModelProperty("积分历史列表")
    private List<PointsHistory> pointsHistoryList = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, targetEntity = Content.class, mappedBy = "user")
    @JsonIgnoreProperties({"user", "discussionList","imageList","likeList","labelList","bannerList"})
    @ApiModelProperty("发布内容列表")
    private List<Content> contentList = new ArrayList<>();

    /*@OneToMany(fetch = FetchType.LAZY, targetEntity = Equipment.class, mappedBy = "user")
    @JsonIgnoreProperties({"user"})
    @ApiModelProperty("装备列表")
    private List<Equipment> equipmentList = new ArrayList<>();*/


    @OneToMany(fetch = FetchType.LAZY, targetEntity = Order.class, mappedBy = "user")
    @JsonIgnoreProperties({"user","address"})
    @ApiModelProperty("订单列表")
    @JsonIgnore
    private List<Order> orderList = new ArrayList<>();

    /*@Transient
    @OneToMany(fetch = FetchType.LAZY, targetEntity = Address.class, mappedBy = "user")
    @JsonIgnoreProperties({"user"})
    @ApiModelProperty("收货地址列表")
    private List<Address> addressList = new ArrayList<>();*/

    @ManyToMany(fetch = FetchType.LAZY, targetEntity = Equipment.class)
    @JoinTable(name = "m_user_equipment",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "equipment_id", referencedColumnName = "id")})
    @JsonIgnoreProperties({"user"})
    @ApiModelProperty("装备列表")
    private List<Equipment> equipmentList = new ArrayList<>();


    @ManyToMany(fetch = FetchType.LAZY, targetEntity = Label.class)
    @JoinTable(name = "m_user_label",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "label_id", referencedColumnName = "id")})
    @ApiModelProperty("标签列表")
    private List<Label> labelList = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY, targetEntity = User.class)
    @JoinTable(name = "m_user_focus",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "focus_id", referencedColumnName = "id")})
    @JsonIgnoreProperties({"pointsHistoryList", "equipmentList", "orderList", "addressList", "labelList", "focusList", "followList","contentList","downloVideoList"})
    @ApiModelProperty("关注列表")
    private List<User> focusList = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY, targetEntity = User.class)
    @JoinTable(name = "m_user_follow",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "follow_id", referencedColumnName = "id")})
    @JsonIgnoreProperties({"pointsHistoryList", "contentList", "equipmentList", "orderList", "addressList", "labelList", "focusList", "followList","contentList","downloVideoList"})
    @ApiModelProperty("粉丝列表")
    private List<User> followList = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY, targetEntity = Content.class)
    @JoinTable(name = "m_user_collect",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "content_id", referencedColumnName = "id")})
    @JsonIgnoreProperties({"pointsHistoryList", "contentList", "equipmentList", "orderList", "addressList", "labelList", "focusList", "followList","contentList","contentList","downloVideoList"})
    @ApiModelProperty("收藏列表")
    @JsonIgnore
    private List<Content> collectList = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY, targetEntity = Image.class)
    @JoinTable(name = "m_user_cache_video",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "image_id", referencedColumnName = "id")})
    @JsonIgnoreProperties({"pointsHistoryList", "contentList", "equipmentList", "orderList", "addressList", "labelList", "focusList", "followList","contentList","downloVideoList"})
    @ApiModelProperty("下载视频列表")
    private List<Image> downloVideoList = new ArrayList<>();
}
