package com.liaoin.diving.service;

import com.liaoin.diving.common.PageHelp;
import com.liaoin.diving.entity.Badge;

import java.util.List;

public interface BadgeService {

    void insert(Badge badge);

    void update(Badge badge);

    void delete(Integer[] ids);

    Badge findOne(Integer id);

    List<Badge> findList(PageHelp pageHelp);

    void releaseBadge(Integer id, Integer badgeId, Integer num);

    List<Badge> findListWithUser(Integer id);
}
