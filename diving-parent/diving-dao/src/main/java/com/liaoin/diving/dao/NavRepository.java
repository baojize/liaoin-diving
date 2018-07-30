package com.liaoin.diving.dao;

import com.liaoin.diving.entity.Nav;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author : huqingxi
 * @describe :
 * @date 2018/06/07
 */
public interface NavRepository extends JpaRepository<Nav, Integer>, JpaSpecificationExecutor<Nav> {
    @Query(value = "select * from t_nav order by `order` ", nativeQuery = true)
    List<Nav> findNav();
}
