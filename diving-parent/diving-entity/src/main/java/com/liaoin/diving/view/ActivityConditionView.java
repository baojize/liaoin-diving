package com.liaoin.diving.view;

import lombok.Data;

import java.util.Date;

/**
 * @author : huqingxi
 * @describe :
 * @date 2018/06/07
 */
@Data
public class ActivityConditionView {
    private String name;
    private String address;
    private Integer price;
    private Date beginTime;
    private Date endTime;
}
