package com.liaoin.diving.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;

/**
 * @Author: Allen
 * @Description:
 * @Date: Created in 2018/7/30 17:23
 */
@Entity
@Table(name = "t_goods_classify",catalog = "diving")
@Document(indexName = "diving", type = "goodsclassify")
@ApiModel("商品分类实体类")
@Getter
@Setter
@ToString
public class GoodsClassify {
    @Id
    @GeneratedValue
    @ApiModelProperty(value = "商品分类ID 主键")
    private Integer id;

    @Column(name = "name")
    @ApiModelProperty(value = "分类名")
    private String name;

    @Column(name = "is_delete")
    @ApiModelProperty(value = "是否删除：1删除", hidden = true)
    private Integer isDelete;

    @Column(name = "goods_id")
    @ApiModelProperty(value = "商品id")
    private Integer goodsId;
}
