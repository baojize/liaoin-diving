package com.liaoin.diving.mapper;

import com.liaoin.diving.entity.User;

import java.util.List;

/**
 * @author : huqingxi
 * @describe :
 * @date 2018/06/07
 */
public interface UserMapper {
    List<User> findAllUser();

    List<User> findAgree(Integer id);

    List<User> findFans(Integer id);

    Integer findFansNum(Integer id);
}
