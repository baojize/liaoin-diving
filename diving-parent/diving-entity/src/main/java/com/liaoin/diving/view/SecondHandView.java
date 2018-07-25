package com.liaoin.diving.view;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.liaoin.diving.entity.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

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
public class SecondHandView {

    @ApiModelProperty("主键")
    private Integer id;

    @ApiModelProperty(value = "创建时间", hidden = true)
    private Date createTime;

    @ApiModelProperty(value = "阅读量")
    private Integer reading = 0;

    @ApiModelProperty("正文")
    private String text;

    @ApiModelProperty("主题")
    private String theme;


    @ApiModelProperty("品牌")
    private String brand;

    @ApiModelProperty("成色")
    private String quality;

    @ApiModelProperty("价格")
    private String price;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("分类")
    private Integer categoryId;

    @ApiModelProperty(value = "是否删除：1删除", hidden = true)
    private String isDelete;

    @ApiModelProperty("类型：1讨论，2动态，3二手，4问题")
    private String type;









    @ApiModelProperty("是否推荐：1推荐")
    private String isRecommend = "0";

    @ApiModelProperty(value = "是否显示首页")
    private Integer isHome = 0;




}
