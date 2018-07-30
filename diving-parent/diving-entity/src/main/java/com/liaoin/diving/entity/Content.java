package com.liaoin.diving.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "t_content", catalog = "diving")
@Document(indexName = "diving", type = "content")
@ApiModel("内容实体类")
@Getter
@Setter
public class Content implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id")
    @Field(index = FieldIndex.not_analyzed, store = true, type = FieldType.Integer)
    @ApiModelProperty(value = "主键", hidden = true)
    private Integer id;

    @Column(name = "type")
    @Field(index = FieldIndex.not_analyzed, store = true)
    @ApiModelProperty("类型：1讨论，2动态，3二手，4问题")
    private String type;

    @Column(name = "theme")
    @Field(index = FieldIndex.analyzed, store = true, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    @ApiModelProperty("主题")
    private String theme;

    @Column(name = "text")
    @Field(index = FieldIndex.analyzed, store = true, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    @ApiModelProperty("正文")
    private String text;

    @Column(name = "reading")
    @Field(index = FieldIndex.no, store = true)
    @ApiModelProperty(value = "阅读量", hidden = true)
    private Integer reading = 0;

    @Column(name = "titile")
    @ApiModelProperty(value = "标题")
    private String title;

    @Column(name = "create_time")
    @Temporal(TemporalType.TIMESTAMP)
    @Field(index = FieldIndex.no, store = true)
    @ApiModelProperty(value = "创建时间", hidden = true)
    private Date createTime;

    @Column(name = "is_recommend")
    @ApiModelProperty("是否推荐：1推荐")
    private String isRecommend = "0";

    @Column(name = "is_home")
    @ApiModelProperty(value = "是否显示首页")
    private Integer isHome = 0;

    @Column(name = "is_delete")
    @ApiModelProperty(value = "是否删除：1删除", hidden = true)
    @Field(index = FieldIndex.no, store = true)
    private String isDelete = "0";

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, targetEntity = Banner.class)
    @ApiModelProperty(value = "内容")
    @JsonIgnoreProperties({"content"})
    private List<Banner> bannerList =  new ArrayList<>();

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST, targetEntity = SecondHand.class)
    @JoinColumn(name = "second_hand_id")
    @ApiModelProperty("二手")
    private SecondHand secondHand;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Topic.class)
    @JoinColumn(name = "topic_id")
    @ApiModelProperty("话题")
    private Topic topic;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = User.class)
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"pointsHistoryList", "contentList", "equipmentList", "orderList", "addressList", "labelList", "focusList", "followList","downloVideoList"})
    @ApiModelProperty("用户")
    private User user;

    @ManyToMany(fetch = FetchType.LAZY, targetEntity = Label.class)
    @JoinTable(name = "m_content_label",
            joinColumns = {@JoinColumn(name = "content_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "label_id", referencedColumnName = "id")})
    @ApiModelProperty("标签列表")
    private List<Label> labelList = new ArrayList<>();

    //@ManyToMany(fetch = FetchType.LAZY, targetEntity = User.class)
    @Transient
    @JoinTable(name = "m_content_user",
            joinColumns = {@JoinColumn(name = "content_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")})
    @JsonIgnoreProperties({"pointsHistoryList", "contentList", "equipmentList", "orderList", "addressList", "labelList", "focusList", "followList"})
    @ApiModelProperty(value = "点赞列表", hidden = true)
    private List<User> likeList = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY, targetEntity = Image.class)
    @JoinTable(name = "m_content_image",
            joinColumns = {@JoinColumn(name = "content_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "image_id", referencedColumnName = "id")})
    @ApiModelProperty("图片或视频列表")
    private List<Image> imageList = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, targetEntity = Discussion.class, mappedBy = "content")
    @JsonIgnoreProperties({"content"})
    @ApiModelProperty(value = "评论列表", hidden = true)
    private List<Discussion> discussionList = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Content content = (Content) o;
        return Objects.equals(id, content.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
