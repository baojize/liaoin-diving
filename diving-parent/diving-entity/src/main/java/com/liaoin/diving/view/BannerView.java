package com.liaoin.diving.view;

import com.liaoin.diving.entity.Activity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author : huqingxi
 * @describe :
 * @date 2018/06/07
 */
@Data
public class BannerView implements Serializable{
    private Integer id;
    private Integer contentId;
    private String url;
    private String title;
    private Date createTime;
    private String type;
    private Integer activityId;
}
