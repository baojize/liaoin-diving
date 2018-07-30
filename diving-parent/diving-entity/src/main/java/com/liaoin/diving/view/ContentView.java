package com.liaoin.diving.view;

import com.liaoin.diving.entity.Content;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ContentView extends Content {
    /**
     * 点赞数
     */
    @ApiModelProperty(value = "点赞数")
    private Integer likeNum;
    /**
     * 评论数
     */
    @ApiModelProperty(value = "评论数")
    private Integer commentNum;
    /**
     * 图片或者视频的URL
     */
    @ApiModelProperty(value = "图片地址")
    private String imgUrl;
}
