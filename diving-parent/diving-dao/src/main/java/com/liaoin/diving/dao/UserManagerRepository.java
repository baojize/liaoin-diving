package com.liaoin.diving.dao;

import com.liaoin.diving.entity.manager.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * @author : huqingxi
 * @describe :
 * @date 2018/06/07
 */
public interface UserManagerRepository extends JpaRepository<Admin, Integer>, JpaSpecificationExecutor<Admin> {
    @Query(value = "select * from t_admin where account = ?1}", nativeQuery = true)
    Admin findByAccount(String account);
}
