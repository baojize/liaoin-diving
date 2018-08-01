package com.liaoin.diving.view;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author : huqingxi
 * @describe :
 * @date 2018/06/07
 */
@Data
public class EqConditionView {
    @ApiModelProperty("商品名称")
    private String name;

    @ApiModelProperty("商品折扣")
    private BigDecimal discount;

    @ApiModelProperty("是否上架：1上架")
    private String isSale;

    @ApiModelProperty("库存数量")
    private Integer amount;

    @ApiModelProperty("商品现价 (原价 * 折扣)")
    private BigDecimal money;
}
