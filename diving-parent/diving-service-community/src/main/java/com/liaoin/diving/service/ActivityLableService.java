package com.liaoin.diving.service;

import com.liaoin.diving.entity.Activity;
import com.liaoin.diving.entity.ActivityLabel;

import java.util.List;

/**
 * @author : huqingxi
 * @describe :
 * @date 2018/06/07
 */
public interface ActivityLableService {
    ActivityLabel findById(Integer id);
    List<ActivityLabel> findAll();
    void add(ActivityLabel activityLabel);
    void del(Integer id);
    void update(ActivityLabel activityLabel);
    List<ActivityLabel> findByActivityId(Integer id);
}
