package com.liaoin.diving.service;

import com.liaoin.diving.common.PageBean;
import com.liaoin.diving.entity.ActivityDiscussion;
import org.springframework.data.domain.Pageable;

public interface ActivityDiscussionService {
    void insert(ActivityDiscussion activityDiscussion);

    void update(ActivityDiscussion activityDiscussion);

    void delete(Integer[] ids);

    ActivityDiscussion findOne(Integer id);

    PageBean<ActivityDiscussion> pageQuery(Pageable pageable, ActivityDiscussion activityDiscussion);
}

