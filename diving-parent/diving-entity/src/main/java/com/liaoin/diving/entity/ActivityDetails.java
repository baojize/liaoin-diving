package com.liaoin.diving.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Allen
 * @Description: 活动细则
 * @Date: Created in 2018/7/24 15:10
 */
@Entity
@Table(name = "t_activity_details", catalog = "diving")
@ApiModel("活动细则实体")
@Getter
@Setter
public class ActivityDetails implements Serializable {
    @Id
    @GeneratedValue
    @ApiModelProperty(value = "主键", hidden = true)
    private Integer id;

    @Column(name = "name")
    @ApiModelProperty(value = "细则名")
    private String name;

    @Column(name = "is_delete")
    @ApiModelProperty(value = "是否删除 1删除")
    private Integer isDelete;

    @OneToMany(fetch = FetchType.LAZY, targetEntity = ActivityDetailContent.class,mappedBy = "activity")
    @JsonIgnoreProperties({"activity", "activityDetails"})
    @ApiModelProperty(value = "内容列表")
    private List<ActivityDetailContent> activityDetailContentList = new ArrayList<>();
}
