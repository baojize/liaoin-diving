package com.liaoin.diving.service;

import com.liaoin.diving.common.PageBean;
import com.liaoin.diving.common.PageHelp;
import com.liaoin.diving.entity.Content;
import com.liaoin.diving.entity.Discussion;
import com.liaoin.diving.entity.User;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface DiscussionService {
    void insert(Discussion discussion);

    void update(Discussion discussion);

    void delete(Integer[] ids);

    Discussion findOne(Integer id);

    PageBean<Discussion> pageQuery(Pageable pageable, Discussion discussion);

    List<Discussion> findNewNotice(Integer userId);

    List<Integer> findCurrentUpContentIds(Integer id);

    List<Discussion> findDisForContentId(PageHelp pageHelp, Integer contentId);


    void like(Map<String, Object> map);
}

