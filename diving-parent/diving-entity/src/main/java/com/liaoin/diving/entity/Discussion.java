package com.liaoin.diving.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "t_discussion", catalog = "diving")
@ApiModel("评论实体类")
@Getter
@Setter
public class Discussion implements Serializable {

    @Id
    @GeneratedValue
    @ApiModelProperty(value = "主键", hidden = true)
    private Integer id;

    @Column(name = "text")
    @ApiModelProperty("正文")
    private String text;

    @Column(name = "liking")
    @ApiModelProperty("点赞数 ")  // 评论点赞只记录数量
    private Integer liking;

    @Column(name = "create_time")
    @Temporal(TemporalType.TIMESTAMP)
    @ApiModelProperty("创建时间")
    private Date createTime;

    @Column(name = "is_delete")
    @ApiModelProperty(value = "是否删除：1删除", hidden = true)
    private String isDelete;

    @Column(name = "answer_user")
    @ApiModelProperty("回复用户")
    private Integer answerUser;

    @Column(name = "create_user")
    @ApiModelProperty("创建用户")
    private Integer createUser;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Content.class)
    @JoinColumn(name = "content_id")
    @JsonIgnoreProperties({"discussionList","imageList","likeList","labelList","user"})
    @ApiModelProperty("内容")
    private Content content;

    /*@ManyToOne(fetch = FetchType.EAGER, targetEntity = Content.class)
    @JoinColumn(name = "content_id")
    @JsonIgnoreProperties({"discussionList","imageList","likeList","labelList","user"})
    @ApiModelProperty("内容")
    private Content content;*/



    /*@ManyToOne(fetch = FetchType.EAGER, targetEntity = User.class)
    @JoinColumn(name = "answer_user_id")
    @JsonIgnoreProperties({"pointsHistoryList", "contentList", "equipmentList", "labelList", "focusList", "followList"})
    @ApiModelProperty("回复用户")
    private User answerUser;*/

    /*@ManyToOne(fetch = FetchType.EAGER, targetEntity = User.class)
    @JoinColumn(name = "answer_user_id")
    @JsonIgnoreProperties({"pointsHistoryList", "contentList", "equipmentList", "labelList", "focusList", "followList"})*/


    /*@ManyToOne(fetch = FetchType.EAGER, targetEntity = User.class)
    @JoinColumn(name = "create_user_id")
    @JsonIgnoreProperties({"pointsHistoryList", "contentList", "equipmentList", "labelList", "focusList", "followList"})
    @ApiModelProperty("创建用户")
    private User createUser;*/




    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Discussion that = (Discussion) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
