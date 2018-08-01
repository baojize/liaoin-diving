package com.liaoin.diving.service.impl;

import com.github.pagehelper.PageHelper;
import com.liaoin.diving.common.PageHelp;
import com.liaoin.diving.dao.BadgeRepository;
import com.liaoin.diving.dao.BadgeUserRepository;
import com.liaoin.diving.entity.BadgeUser;
import com.liaoin.diving.mapper.BadgeMapper;
import com.liaoin.diving.mapper.BadgeUserMapper;
import com.liaoin.diving.service.BadgeUserService;
import com.liaoin.diving.utils.UpdateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BadgeUserServiceImpl implements BadgeUserService {
    @Autowired
    private BadgeUserRepository badgeUserRepository;
    @Autowired
    private BadgeUserMapper badgeUserMapper;

    @Override
    public void insert(BadgeUser badgeUser) {
        badgeUser.setIsDelete(0);
        badgeUserRepository.save(badgeUser);
    }

    @Override
    public void update(BadgeUser badgeUser) {
        BadgeUser one = badgeUserRepository.findOne(badgeUser.getId());
        UpdateUtils.copyNonNullProperties(badgeUser, one);
        badgeUserRepository.save(one);
    }

    @Override
    public void delete(Integer[] ids) {
        List<BadgeUser> list = new ArrayList<>();
        for (Integer id : ids) {
            BadgeUser badgeUser = badgeUserRepository.findOne(id);
            if (badgeUser == null) {
                continue;
            }
            badgeUser.setIsDelete(1);
            list.add(badgeUser);
        }
        badgeUserRepository.save(list);
    }

    @Override
    public BadgeUser findOne(Integer id) {
        return badgeUserRepository.findOne(id);
    }

    /**
     * 分页查询
     * @param pageHelp
     * @return
     */
    @Override
    public List<BadgeUser> findList(PageHelp pageHelp) {
        PageHelper.startPage(pageHelp.getStart(), pageHelp.getPageSize());
        List<BadgeUser> badgeUserList = badgeUserMapper.findAll();
        return badgeUserList;
    }
}
