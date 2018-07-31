package com.liaoin.diving.service;

import com.liaoin.diving.common.PageBean;
import com.liaoin.diving.entity.ActivityCategory;
import org.springframework.data.domain.Pageable;

public interface ActivityCategoryService {
    void insert(ActivityCategory activityCategory);

    void update(ActivityCategory activityCategory);

    void delete(Integer[] ids);

    ActivityCategory findOne(Integer id);

    PageBean<ActivityCategory> pageQuery(Pageable pageable, ActivityCategory activityCategory);
}

