package com.liaoin.diving.service;

import com.liaoin.diving.common.PageBean;
import com.liaoin.diving.common.PageHelp;
import com.liaoin.diving.entity.Activity;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ActivityService {
    void insert(Activity activity);

    void update(Activity activity);

    void delete(Integer[] ids);

    Activity findOne(Integer id);

    PageBean<Activity> pageQuery(Pageable pageable, Activity activity);

    boolean delProducts(Integer[] ids);

    List<Activity> recommend(Integer type);

    List<Activity> findByName(String name);


    void setBroadcast(Integer id);

    List<Activity> findRecommend();


    List<Activity> findByCategory(PageHelp pageHelp, Integer id);

}

