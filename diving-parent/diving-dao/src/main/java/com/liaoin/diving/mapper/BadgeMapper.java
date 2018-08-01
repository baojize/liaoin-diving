package com.liaoin.diving.mapper;

import com.liaoin.diving.entity.Badge;

import java.util.List;

public interface BadgeMapper {
    List<Badge> findAll();

    /**
     * 查询当前用户拥有的徽章
     * @param id 当前用户ID
     * @return
     */
    List<Badge> findListWithUser(Integer id);
}
