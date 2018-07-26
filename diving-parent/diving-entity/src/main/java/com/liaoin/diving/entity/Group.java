package com.liaoin.diving.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "t_group", catalog = "diving")
@Document(indexName = "diving", type = "group")
@ApiModel("俱乐部实体类")
@Getter
@Setter
public class Group implements Serializable {

    @Id
    @GeneratedValue
    @ApiModelProperty(value = "主键", hidden = true)
    private Integer id;

    @Column(name = "name")
    @Field(index = FieldIndex.analyzed, store = true, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    @ApiModelProperty("俱乐部名称")
    private String name;

    @Column(name = "image_id")
    @ApiModelProperty("头像")
    private Integer imageId;

    @Column(name = "is_delete")
    @ApiModelProperty(value = "是否删除：1删除")
    private String isDelete;

    @ApiModelProperty(value = "是否认证：0否，1是")
    private Integer status;

    @ApiModelProperty(value = "等级", hidden = true)
    private Integer level;

    @Column(name = "release_num")
    @ApiModelProperty(value = "发布数量")
    private Long releaseNum;

    @ApiModelProperty(value = "地址")
    private String address;

    @Transient
    @ApiModelProperty(value = "成员数量")
    private Long memberNum;

    @Transient
    @ApiModelProperty(value = "粉丝数量")
    private Integer fansNum;

    @Transient
    @ApiModelProperty(value = "排名")
    private Integer order;

    @Transient
    @ApiModelProperty(value = "发布内容列表",hidden = true)
    private List<Content> contentList = new ArrayList<>();

}
