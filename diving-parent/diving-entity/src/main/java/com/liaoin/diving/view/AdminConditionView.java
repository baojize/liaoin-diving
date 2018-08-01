package com.liaoin.diving.view;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author : huqingxi
 * @describe :
 * @date 2018/06/07
 */
@Data
public class AdminConditionView {
    @ApiModelProperty(value = "账号")
    private String account;
    @ApiModelProperty(value = "昵称")
    private String nickname;
    @ApiModelProperty(value = "电话")
    private String phone;
}
