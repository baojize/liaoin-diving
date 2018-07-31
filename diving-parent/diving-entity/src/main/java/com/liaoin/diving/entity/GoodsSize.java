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
 * @Date: Created in 2018/7/30 17:50
 */
@Entity
@Table(name = "t_goods_classify",catalog = "diving")
@Document(indexName = "diving", type = "goodsclassify")
@ApiModel("商品分类实体类")
@Getter
@Setter
@ToString
public class GoodsSize {
    @Id
    @GeneratedValue
    @ApiModelProperty(value = "商品分类ID 主键")
    private Integer id;

    @Column(name = "name")
    @ApiModelProperty(value = "尺寸名")
    private String name;

    @Column(name = "is_delete")
    @ApiModelProperty(value = "是否删除：1删除", hidden = true)
    private Integer isDelete;

    @Column(name = "amount")
    @ApiModelProperty(value = "数量")
    private Integer amount;

    @Column(name = "goods_id")
    @ApiModelProperty(value = "商品id")
    private Integer goodsId;
}
