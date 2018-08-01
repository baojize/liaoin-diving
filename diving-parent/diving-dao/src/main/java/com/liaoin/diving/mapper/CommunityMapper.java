package com.liaoin.diving.mapper;

import com.liaoin.diving.entity.CommunityNav;

import java.util.List;

/**
 * @author : huqingxi
 * @describe :
 * @date 2018/06/07
 */
public interface CommunityMapper {

    Integer addCommunityNav(CommunityNav nav);

    Integer delCommunityNav(Integer id);

    CommunityNav findById(Integer id);

    Integer updateCommunityNav(CommunityNav nav);

    List<CommunityNav> codition(CommunityNav nav);

    List<CommunityNav> findAll();

}
