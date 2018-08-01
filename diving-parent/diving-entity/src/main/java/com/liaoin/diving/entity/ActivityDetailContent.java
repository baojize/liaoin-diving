package com.liaoin.diving.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Author: Allen
 * @Description: 活动细则内容
 * @Date: Created in 2018/7/24 15:11
 */
@Entity
@Table(name = "t_activity_detail_content", catalog = "diving")
@ApiModel("活动细则内容实体")
@Getter
@Setter
@ToString
public class ActivityDetailContent implements Serializable {
    @Id
    @GeneratedValue
    @ApiModelProperty(value = "主键", hidden = true)
    private Integer id;

    @Column(name = "content",columnDefinition = "TEXT")
    @ApiModelProperty(value = "细则内容")
    private String content;

    @ManyToOne(targetEntity = ActivityDetails.class)
    @JoinColumn(name = "activity_details_id")
    @JsonIgnoreProperties({"activityDetailContentList"})
    @ApiModelProperty(value = "细则")
    private ActivityDetails activityDetails;

//    @OneToOne(targetEntity = ActivityDetails.class)
//    @JoinColumn(name = "activity_details_id")
//    @JsonIgnoreProperties({"activity","activityDetails"})
//    @ApiModelProperty(value = "细则")
//    private ActivityDetails activityDetails;

    @ManyToOne(targetEntity = Activity.class)
    @JoinColumn(name = "activity_id")
    @ApiModelProperty("活动")
    private Activity activity;
}
