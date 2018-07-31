package com.liaoin.diving.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.liaoin.diving.vo.DiscussionVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author : huqingxi
 * @describe :
 * @date 2018/06/07
 */
@Entity
@Table(name = "t_official")
@ApiModel("官方消息")
@Getter
@Setter
public class Official implements Serializable{
    @Id
    @GeneratedValue
    @ApiModelProperty("主键")
    private Integer id;

    @Column(name = "create_time")
    @ApiModelProperty("创建时间")
    private Date createTime;

    @Column(name = "pager")
    @ApiModelProperty("页面标题")
    private String pager;

    @Column(name = "discribe")
    @ApiModelProperty("页面描述")
    private String discribe;

    @Column(name = "pager_img")
    @ApiModelProperty("页面展示图片")
    private String pagerImg;

    /*@Column(name = "official_img")
    @ApiModelProperty("页面展示图片")
    private String officialImg;*/

    @Column(name = "title")
    @ApiModelProperty("内容标题")
    private String title;

    @Column(name = "text")
    @ApiModelProperty("内容正文")
    private String text;

    @Column(name = "like_count")
    @ApiModelProperty("点赞数")
    private Integer likeCount;

    @Column(name = "dis_count")
    @ApiModelProperty("评论数")
    private Integer disCount;

    @Column(name = "read_count")
    @ApiModelProperty("阅读量")
    private Integer readCount;

    @Column(name = "user_id")
    @ApiModelProperty("发布用户id")
    private Integer userId;

    @Transient
    @ApiModelProperty("评论列表")
    private List<DiscussionVo> discussionsVo;

    @Transient
    @ApiModelProperty("标签列表")
    private List<Label> labels;

    // 内容如果是富文本则此属性无意义
    @Transient
    @ApiModelProperty("文件列表")
    private List<Image> images;

}
