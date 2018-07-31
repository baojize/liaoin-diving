package com.liaoin.diving.vo;

import com.liaoin.diving.entity.Content;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;

@Data
public class ContentVo{
    @ApiModelProperty(value = "俱乐部ID")
    private Integer groupId;

    @ApiModelProperty(value = "内容")
    private Content content;
}
