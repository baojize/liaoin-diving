package com.liaoin.diving.mapper;

import com.liaoin.diving.entity.manager.Admin;
import com.liaoin.diving.view.AdminConditionView;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author : huqingxi
 * @describe :
 * @date 2018/06/07
 */
public interface UserManagerMapper {
    /**
     * @param account
     * @param password
     * @return
     */
    Admin login(@Param("account") String account, @Param("password") String password);


    Admin findByAccount(String account);

    List<Admin> findAll();

    List<Admin> condition(AdminConditionView admin);
}
