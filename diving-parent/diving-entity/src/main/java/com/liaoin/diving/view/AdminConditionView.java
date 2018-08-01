package com.liaoin.diving.view;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import javax.persistence.Column;

/**
 * @author : huqingxi
 * @describe :
 * @date 2018/06/07
 */
@Data
public class AdminConditionView {
    @ApiModelProperty("账号")
    private String account;
    @ApiModelProperty("昵称")
    private String nickname;
    @ApiModelProperty("电话")
    private String phone;
}
