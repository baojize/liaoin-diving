package com.liaoin.diving.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.sql.Select;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName Broadcast
 * @Author huqingxi
 * @Date 2018/7/5 11:17
 **/
@Entity
@Getter
@Setter
@Table(name = "t_broadcast", catalog = "diving")
@ApiModel("轮播图实体类")
public class Broadcast implements Serializable{
    @Id
    @GeneratedValue
    @ApiModelProperty(value = "主键",hidden = true)
    private Integer id;

    @Column(name = "create_time")
    @ApiModelProperty(value = "创建时间",hidden = true)
    private Date createTime;

    /*@ManyToOne(fetch = FetchType.EAGER, targetEntity = Content.class)
    @JoinColumn(name = "content_id")
    @ApiModelProperty(value = "内容")
    private Content content;*/
   /* @Column(name = "update_time")
    @ApiModelProperty(value = "更新时间", hidden = true)
    private Date updateTime;*/

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Topic.class)
    @JsonIgnoreProperties({"broadcastList"})
    @JoinColumn(name = "topic_id")
    @ApiModelProperty(value = "话题")
    private Topic topic;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Activity.class)
    @JoinColumn(name = "activity_id")
    @JsonIgnoreProperties(value = {
            "activityDiscussionArrayList","activityProductsList","broadcastList", "labelList"
    })
    @ApiModelProperty(value = "活动")
    private Activity activity;


}
