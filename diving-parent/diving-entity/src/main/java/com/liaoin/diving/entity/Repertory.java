package com.liaoin.diving.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_repertory",catalog = "diving")
@ApiModel("仓储实体类")
@Getter
@Setter
public class Repertory {
    @Id
    @GeneratedValue
    @ApiModelProperty(value = "主键")
    private Integer id;

    @ApiModelProperty(value = "商品ID")
    private Integer wareId;

    @ApiModelProperty(value = "类型ID")
    private Integer typeId;

    @ApiModelProperty(value = "尺寸ID")
    private Integer sizeId;

    @ApiModelProperty(value = "库存量")
    private Integer num;

    @ApiModelProperty(value = "展示图地址")
    private String picUrl;
}
