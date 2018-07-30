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

/**
 * @author : huqingxi
 * @describe : 轮播图
 * @date 2018/06/07
 */
@Entity
@Table(name = "t_banner")
@ApiModel("轮播图")
@Getter
@Setter
public class Banner implements Serializable {

    @Id
    @GeneratedValue
    @ApiModelProperty(value = "主键", hidden = true)
    private Integer id;

    @Column(name = "create_time")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @Column(name = "title")
    @ApiModelProperty(value = "标题")
    private String title;

    /*@Column(name = "is_delete")
    @ApiModelProperty(value = "是否删除")
    private Integer isDelete;*/

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Content.class)
    @JoinColumn(name = "content_id")
    @JsonIgnoreProperties({"bannerList"})
    @ApiModelProperty("内容")
    private Content content;

    @Column(name = "activityId")
    @ApiModelProperty("活动id")
    private Integer activityId;

    @Column(name = "type")
    @ApiModelProperty("类型")
    private String type;

    @Column(name = "is_hidden")
    @ApiModelProperty("是否显示 0:不显示, 1 :显示")
    private Integer isHidden;
}
