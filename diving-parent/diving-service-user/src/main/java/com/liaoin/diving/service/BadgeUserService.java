package com.liaoin.diving.service;

import com.liaoin.diving.common.PageHelp;
import com.liaoin.diving.entity.BadgeUser;

import java.util.List;

public interface BadgeUserService {

    void insert(BadgeUser badgeUser);

    void update(BadgeUser badgeUser);

    void delete(Integer[] ids);

    BadgeUser findOne(Integer id);

    List<BadgeUser> findList(PageHelp pageHelp);
}
