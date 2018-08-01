package com.liaoin.diving.view;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author : huqingxi
 * @describe :
 * @date 2018/06/07
 */
@Data
public class RecoAcView {

    private Integer id;
    @ApiModelProperty("图片")
    private String img;
    @ApiModelProperty("名称")
    private String name;
    @ApiModelProperty("价格")
    private Integer price;
    @ApiModelProperty("地址")
    private String address;
    @ApiModelProperty("是否推荐 1推荐  ")
    private Integer isRecommend;
    @ApiModelProperty("是否显示首页 1显示  ")
    private Integer isHome;
}
