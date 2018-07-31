package com.liaoin.diving.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @作者：tt
 * @描述：
 * @日期：2018/7/3 11:37
 */
@Entity
@Table(name = "t_activity",catalog = "diving")
@ApiModel("推荐活动实体")
@Getter
@Setter
public class Activity implements Serializable{

    @Id
    @GeneratedValue
    @ApiModelProperty(value = "主键")
    private Integer id;

    @Column(name = "name")
    @ApiModelProperty(value = "名称", hidden = true)
    private String name;

    @Column(name = "address")
    @ApiModelProperty(value = "地点", hidden = true)
    private String address;

    @Column(name = "iphone")
    @ApiModelProperty(value = "提供方电话", hidden = true)
    private String iphone;

    @Column(name = "orders")
    @ApiModelProperty("活动排序")
    private Integer orders;

    @Column(name = "is_home")
    @ApiModelProperty("是否首页：1首页")
    private String isHome;

    /*@Column(name = "is_banner")
    @ApiModelProperty(value = "是否设置轮播图 1.设置")
    private Integer isBanner;*/

    @Column(name = "is_recommend")
    @ApiModelProperty("是否推荐：1推荐")
    private String isRecommend;

    @Column(name = "is_sale")
    @ApiModelProperty("是否上架：1上架")
    private String isSale;

    /*@Column(name = "highlight")
    @ApiModelProperty(value = "亮点", hidden = true) // 1
    private String highlight;

    @Column(name = "trip")
    @ApiModelProperty(value = "行程安排", hidden = true) // 2
    private String trip;

    @Column(name = "cost_explain")  // 3
    @ApiModelProperty("费用说明")
    private String explain;

    @Column(name = "discribe")  // 4
    @ApiModelProperty("活动描述")
    private String discribe;

    @Column(name = "reserve")  // 5
    @ApiModelProperty("预定须知")
    private String reserve;

    @Column(name = "be_discribe")  // 6
    @ApiModelProperty("负责说明")
    private String beDiscribe;

    @Column(name = "other")  // 7
    @ApiModelProperty("其他说明")
    private String other;

    @Column(name = "img_explain")  // 8
    @ApiModelProperty("图文说明")
    private String imgExplain;*/

    @Column(name = "price")
    @ApiModelProperty("价格")
    private Integer price;

    /*@ManyToOne(fetch = FetchType.EAGER, targetEntity = User.class)*/
    /*@JoinColumn(name = "user_id")
    @ApiModelProperty("提供方")
    private Integer user;*/

    /*@JoinColumn(name = "stitle")
    @ApiModelProperty("小标题")
    private String stitle;*/


   /* @ManyToOne(fetch = FetchType.EAGER, targetEntity = User.class)
    @JoinColumn(name = "user_id")
    @ApiModelProperty("提供方")
    private User user;*/

    /*@Column(name = "plan")
    @ApiModelProperty("计划")
    private String plan;*/

    /*@Column(name = "crowd")
    @ApiModelProperty("面向人群")
    private String crowd;*/



    @Column(name = "activity_time")
    @Temporal(TemporalType.TIMESTAMP)
    @ApiModelProperty(value = "活动开始时间", hidden = true)
    private Date activityTime;

    @Column(name = "begin_time")
    @Temporal(TemporalType.TIMESTAMP)
    @ApiModelProperty(value = "开始报名时间", hidden = true)
    private Date beginTime;

    @Column(name = "end_time")
    //@Temporal(TemporalType.TIMESTAMP)——>实体类会封装成完整的时间“yyyy-MM-dd hh:MM:ss”的 Date类型。
    @Temporal(TemporalType.TIMESTAMP)
    @ApiModelProperty(value = "结束报名时间", hidden = true)
    private Date endTime;

    @Column(name = "is_delete")
    @ApiModelProperty(value = "是否删除 1删除", hidden = true)
    private String isDelete;


    @Column(name = "img_url")
    @ApiModelProperty(value = "展示图片", hidden = true)
    private String imgUrl;

    @Transient
    @ApiModelProperty(value = "活动小标签", hidden = true)
    private List<ActivityLabel> activityLabels = new ArrayList<>();

    /*@ManyToOne(fetch = FetchType.EAGER, targetEntity = Image.class)
    @JoinColumn(name = "image_id")
    @ApiModelProperty("活动图片")
    private Image image;*/

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = ActivityCategory.class)
    @JoinColumn(name = "activity_category_id")
    @JsonIgnoreProperties({"activityList"})
    @ApiModelProperty("分类")
    private ActivityCategory activityCategory;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, targetEntity = Label.class)
    @JoinTable(name = "m_activity_label",
            joinColumns = {@JoinColumn(name = "activity_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "label_id", referencedColumnName = "id")})
    @ApiModelProperty("标签列表")
    private List<Label> labelList = new ArrayList<>();

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, targetEntity = ActivityDiscussion.class, mappedBy = "activity")
    @JsonIgnoreProperties({"activity"})
    @ApiModelProperty(value = "评论列表", hidden = true)
    private List<ActivityDiscussion> activityDiscussionArrayList = new ArrayList<>();

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, targetEntity = ActivityProducts.class, mappedBy = "activity")
    @JsonIgnoreProperties({"activity"})
    @ApiModelProperty(value = "活动产品", hidden = true)
    private List<ActivityProducts> activityProductsList = new ArrayList<>();


    @ManyToMany(fetch = FetchType.LAZY, targetEntity = ActivityDetails.class)
    @JsonIgnoreProperties({"activity"})
    @JoinTable(name = "m_activity_details",
            joinColumns = {@JoinColumn(name = "activity_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "details_id", referencedColumnName = "id")})
    @ApiModelProperty(value = "细则列表")
    private List<ActivityDetails> activityDetailsList = new ArrayList<>();




    /*@OneToMany(fetch = FetchType.LAZY, targetEntity = Broadcast.class, mappedBy = "activity")
    @JsonIgnoreProperties(value = {"activity","topic"})
    @ApiModelProperty(value = "轮播图")
    private List<Broadcast> broadcastList = new ArrayList<>();*/
}
