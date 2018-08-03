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
import java.math.BigDecimal;
import java.util.*;

@Entity
@Table(name = "t_goods", catalog = "diving")
@Document(indexName = "diving", type = "goods")
@ApiModel("商品SPU实体类")
@Getter
@Setter
public class Goods implements Serializable {

    @Id
    @GeneratedValue
    @ApiModelProperty(value = "主键", hidden = true)
    private Integer id;

    @Column(name = "name")
    @Field(index = FieldIndex.analyzed, store = true, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    @ApiModelProperty("商品名称")
    private String name;

    @Column(name = "price")
    @Field(index = FieldIndex.not_analyzed, store = true)
    @ApiModelProperty("商品原价")
    private BigDecimal price;

    @Column(name = "money")
    @Field(index = FieldIndex.not_analyzed, store = true)
    @ApiModelProperty("商品现价 (原价 * 折扣)")
    private BigDecimal money;

    @Column(name = "discount")
    @Field(index = FieldIndex.not_analyzed, store = true)
    @ApiModelProperty("商品折扣")
    private BigDecimal discount;

    @Column(name = "introduction")
    @Field(index = FieldIndex.analyzed, store = true, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    @ApiModelProperty("商品介绍")
    private String introduction;

    @Column(name = "orders")
    @Field(index = FieldIndex.not_analyzed, store = true)
    @ApiModelProperty("商品排序")
    private Integer orders;

    @Column(name = "is_home")
    @Field(index = FieldIndex.not_analyzed, store = true)
    @ApiModelProperty("是否首页：1首页")
    private String isHome;

    @Column(name = "is_recommend")
    @Field(index = FieldIndex.not_analyzed, store = true)
    @ApiModelProperty("是否推荐：1推荐")
    private String isRecommend;

    @Column(name = "is_sale")
    @Field(index = FieldIndex.not_analyzed, store = true)
    @ApiModelProperty("是否上架：1上架")
    private String isSale;

    @Column(name = "is_delete")
    @Field(index = FieldIndex.not_analyzed, store = true)
    @ApiModelProperty(value = "是否删除：1删除", hidden = true)
    private String isDelete;

    @Column(name = "is_related")
    @Field(index = FieldIndex.not_analyzed, store = true)
    @ApiModelProperty("是否为周边物品：1周边")
    private String isRelated;

    @Column(name = "create_time")
    @Temporal(TemporalType.TIMESTAMP)
    @Field(index = FieldIndex.not_analyzed, store = true)
    @ApiModelProperty("创建时间")
    private Date createTime;

    @Column(name = "update_time")
    @Temporal(TemporalType.TIMESTAMP)
    @Field(index = FieldIndex.not_analyzed, store = true)
    @ApiModelProperty("更新时间")
    private Date updateTime;

    @Column(name = "delete_time")
    @Temporal(TemporalType.TIMESTAMP)
    @Field(index = FieldIndex.not_analyzed, store = true)
    @ApiModelProperty("删除时间")
    private Date deleteTime;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Category.class)
    @JoinColumn(name = "category_id")
    @ApiModelProperty("分类")
    private Category category;

    /************************************************/
    @Column(name = "amount")
    @Field(index = FieldIndex.not_analyzed, store = true)
    @ApiModelProperty("库存数量")
    private Integer amount;

    /************************************************/


    /*@ManyToOne(fetch = FetchType.EAGER, targetEntity = Template.class)
    @JoinColumn(name = "template_id")
    @ApiModelProperty("模板")
    private Template template;*/


    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Brand.class)
    @JoinColumn(name = "brand_id")
    @ApiModelProperty("品牌")
    private Brand brand;

    /*@ManyToMany(fetch = FetchType.LAZY, targetEntity = Spec.class)
    @JoinTable(name = "m_goods_spec",
            joinColumns = {@JoinColumn(name = "goods_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "spec_id", referencedColumnName = "id")})
    @ApiModelProperty("规格列表")
    private List<Spec> specList = new ArrayList<>();*/
    @ManyToMany(fetch = FetchType.EAGER, targetEntity = Image.class)
    @JoinTable(name = "m_goods_image",
            joinColumns = {@JoinColumn(name = "goods_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "image_id", referencedColumnName = "id")})
    @ApiModelProperty("图片列表")
    private Set<Image> imageList = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER, targetEntity = Label.class)
    @JoinTable(name = "m_goods_label",
            joinColumns = {@JoinColumn(name = "goods_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "label_id", referencedColumnName = "id")})
    @ApiModelProperty("标签列表")
    private Set<Label> labelList = new HashSet<>();






    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Goods goods = (Goods) o;
        return Objects.equals(id, goods.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
