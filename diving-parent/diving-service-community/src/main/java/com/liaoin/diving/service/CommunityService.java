package com.liaoin.diving.service;

import com.liaoin.diving.common.PageHelp;
import com.liaoin.diving.entity.CommunityNav;

import java.util.List;

/**
 * @author : huqingxi
 * @describe :
 * @date 2018/06/07
 */
public interface CommunityService {
    /**
     * 添加社区导航栏
     * @param nav
     * @return
     */
    Integer addCommunityNav(CommunityNav nav);

    /**
     * 删除社区导航栏  物理删
     * @param id
     * @return
     */
    Integer delCommunityNav(Integer id);

    /**
     * 更新社区导航栏
     * @param nav
     * @return
     */
    Integer updateCommunityNav(CommunityNav nav);

    /**
     * 社区导航栏条件查询
     * @param pageHelp
     * @param nav
     * @return
     */
    List<CommunityNav> conditionNav(PageHelp pageHelp, CommunityNav nav);

    /**
     * 前台接口 ,  查询所有, 排除条件: is_hidden = 1
     * @return
     */
    List<CommunityNav> findAll();
}
