package com.liaoin.diving.service.impl;

import com.github.pagehelper.PageHelper;
import com.liaoin.diving.common.PageHelp;
import com.liaoin.diving.dao.BadgeRepository;
import com.liaoin.diving.dao.BadgeUserRepository;
import com.liaoin.diving.entity.Badge;
import com.liaoin.diving.entity.BadgeUser;
import com.liaoin.diving.mapper.BadgeMapper;
import com.liaoin.diving.service.BadgeService;
import com.liaoin.diving.utils.UpdateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class BadgeServiceImpl implements BadgeService {

    @Autowired
    private BadgeRepository badgeRepository;
    @Autowired
    private BadgeMapper badgeMapper;
    @Autowired
    private BadgeUserRepository badgeUserRepository;

    @Override
    public void insert(Badge badge) {
        badge.setIsDelete(0);
        badgeRepository.save(badge);
    }

    @Override
    public void update(Badge badge) {
        Badge one = badgeRepository.findOne(badge.getId());
        UpdateUtils.copyNonNullProperties(badge, one);
        badgeRepository.save(one);
    }

    @Override
    public void delete(Integer[] ids) {
        List<Badge> list = new ArrayList<>();
        for (Integer id : ids) {
            Badge badge = badgeRepository.findOne(id);
            if (badge == null) {
                continue;
            }
            badge.setIsDelete(1);
            list.add(badge);
        }
        badgeRepository.save(list);
    }

    @Override
    public Badge findOne(Integer id) {
        return badgeRepository.findOne(id);
    }

    /**
     * 分页查询
     * @param pageHelp
     * @return
     */
    @Override
    public List<Badge> findList(PageHelp pageHelp) {
        PageHelper.startPage(pageHelp.getStart(), pageHelp.getPageSize());
        List<Badge> badgeList = badgeMapper.findAll();
        return badgeList;
    }

    /**
     * 为用户颁发徽章
     * @param id
     * @param badgeId
     * @param num
     */
    @Override
    public void releaseBadge(Integer id, Integer badgeId, Integer num) {
        if (Objects.isNull(badgeId)||Objects.isNull(num)){
            throw new RuntimeException("错误的参数");
        }
        //查询出徽章
        Badge badge = badgeRepository.findOne(badgeId);
        if (Objects.isNull(badge)){
            throw new RuntimeException("错误的参数");
        }
        int line = badge.getLine();
        //判断是否满足条件
        if (num < line){
            throw new RuntimeException("没有达到条件");
        }
        //查询是否已经添加
        List<Badge> list = badgeMapper.findListWithUser(id);
        for (Badge b:list
             ) {
            if (b.getId() == badgeId){
                throw new RuntimeException("请勿重复添加");
            }
        }
        //满足条件添加数据
        BadgeUser badgeUser = new BadgeUser();
        badgeUser.setBadgeId(badgeId);
        badgeUser.setCreateTime(new Date());
        badgeUser.setIsDelete(0);
        badgeUser.setUserId(id);
        badgeUserRepository.save(badgeUser);

    }

    /**
     * 查询当前用户拥有的徽章
     * @param id 当前用户ID
     * @return
     */
    @Override
    public List<Badge> findListWithUser(Integer id) {
        List<Badge> list = badgeMapper.findListWithUser(id);
        return list;
    }
}
