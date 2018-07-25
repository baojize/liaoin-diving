package com.liaoin.diving.service;

import com.liaoin.diving.common.PageBean;
import com.liaoin.diving.entity.ActivityProducts;
import org.springframework.data.domain.Pageable;

public interface ActivityProductsService {
    void insert(ActivityProducts activityProducts);

    void update(ActivityProducts activityProducts);

    void delete(Integer[] ids);

    ActivityProducts findOne(Integer id);

    PageBean<ActivityProducts> pageQuery(Pageable pageable, ActivityProducts activityProducts);
}

