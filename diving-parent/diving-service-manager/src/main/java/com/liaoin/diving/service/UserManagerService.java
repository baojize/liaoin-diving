package com.liaoin.diving.service;

import com.liaoin.diving.common.PageHelp;
import com.liaoin.diving.entity.manager.Admin;
import com.liaoin.diving.view.AdminConditionView;
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

    List<Admin> findAll(PageHelp pageHelp);

    /**
     * 检查账号是否注册
     * @param account
     * @return
     */
    Integer checkAccount(String account);

    /**
     * 条件查询
     * @param pageHelp
     * @param admin
     * @return
     */
    List<Admin> condition(PageHelp pageHelp, AdminConditionView admin);
}
