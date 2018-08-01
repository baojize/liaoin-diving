package com.liaoin.diving.service.impl;

import com.github.pagehelper.PageHelper;
import com.liaoin.diving.common.PageHelp;
import com.liaoin.diving.entity.CommunityNav;
import com.liaoin.diving.mapper.CommunityMapper;
import com.liaoin.diving.service.CommunityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @author : huqingxi
 * @describe :
 * @date 2018/06/07
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CommunityServiceImpl implements CommunityService {
    @Resource
    private CommunityMapper communityMapper;

    @Override
    public Integer addCommunityNav(CommunityNav nav) {
        Integer status = communityMapper.addCommunityNav(nav);
        if (status != 1){
            return -1;
        }
        return 1;
    }

    @Override
    public Integer delCommunityNav(Integer id) {
        Integer status = communityMapper.delCommunityNav(id);
        if (status != 1){
            return -1;
        }
        return 1;
    }

    @Override
    public Integer updateCommunityNav(CommunityNav nav) {
        CommunityNav dbNav = communityMapper.findById(nav.getId());
        if (Objects.isNull(dbNav)){
            return -1;
        }
        nav.setCreateTime(dbNav.getCreateTime());
        Integer status = communityMapper.updateCommunityNav(nav);
        if (status != 1){
            return -1;
        }
        return 1;
    }

    @Override
    public List<CommunityNav> conditionNav(PageHelp pageHelp, CommunityNav nav) {
        PageHelper.startPage(pageHelp.getStart(), pageHelp.getPageSize());
        List<CommunityNav> navList = communityMapper.codition(nav);
        return navList;
    }

    @Override
    public List<CommunityNav> findAll() {
        return communityMapper.findAll();
    }
}
