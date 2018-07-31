package com.liaoin.diving.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.liaoin.diving.entity.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author : huqingxi
 * @describe :
 * @date 2018/06/07
 */
@Getter
@Setter
public class UserVo  {


    @ApiModelProperty(value = "主键", hidden = true)
    private Integer id;

    @ApiModelProperty("手机")
    private String mobile;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("昵称")
    private String nickname;

    @ApiModelProperty("性别：0女，1男")
    private String sex;

    @Temporal(TemporalType.DATE)
    @ApiModelProperty("生日")
    private Date birthday;

    @ApiModelProperty("签名")
    private String signature;

    @ApiModelProperty("经常出没")
    private String haunt;

    @ApiModelProperty(value = "积分", hidden = true)
    private Integer points;

    @Temporal(TemporalType.TIMESTAMP)
    @ApiModelProperty(value = "注册时间", hidden = true)
    private Date createTime;

    @Temporal(TemporalType.TIMESTAMP)
    @ApiModelProperty(value = "修改时间", hidden = true)
    private Date updateTime;

    @ApiModelProperty(value = "是否删除：1删除", hidden = true)
    private String isDelete;

    @ApiModelProperty(value = "最新关注数量", hidden = true)
    private Integer FollowNum;

    @ApiModelProperty(name = "最新点赞数量")
    private Integer likeNum;

    @ApiModelProperty(name = "赞同数量")
    private Integer agreeNum;

    @ApiModelProperty(name = "最新通知")  // 评论数量
    private Integer noticeNum;

   /* @Column(name = "status")
    @ApiModelProperty(name = "最新数据状态, 0未读, 1 已读")
    private Integer status;*/


    @ApiModelProperty("头像")
    private Integer imageId;


    @ApiModelProperty(value = "等级", hidden = true)
    private Integer levelId;



    /*@OneToMany(fetch = FetchType.LAZY, targetEntity = Equipment.class, mappedBy = "user")
    @JsonIgnoreProperties({"user"})
    @ApiModelProperty("装备列表")
    private List<Equipment> equipmentList = new ArrayList<>();*/



    @ApiModelProperty("装备列表")
    private List<Equipment> equipmentList = new ArrayList<>();


    @ApiModelProperty("标签列表")
    private List<Label> labelList = new ArrayList<>();

    @ApiModelProperty("关注列表")
    private List<User> focusList = new ArrayList<>();

    @ApiModelProperty("粉丝列表")
    private List<User> followList = new ArrayList<>();

    @ApiModelProperty("收藏列表")
    private List<Content> collectList = new ArrayList<>();

    @ApiModelProperty("下载视频列表")
    private List<Image> downloVideoList = new ArrayList<>();
}
