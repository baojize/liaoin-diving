package com.liaoin.diving.service;

import com.liaoin.diving.entity.manager.Admin;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author : huqingxi
 * @describe :
 * @date 2018/06/07
 */
public interface UserManagerService {

    /**
     * @param account
     * @param password
     * @return
     */
    Admin login(@Param("account") String account, @Param("password") String password);

    void add(String account, String password);

    Integer del(Integer id);

    Integer update(Admin admin);

    Admin findById(Integer id);

    List<Admin> findAll();

    /**
     * 检查账号是否注册
     * @param account
     * @return
     */
    Integer checkAccount(String account);
}
