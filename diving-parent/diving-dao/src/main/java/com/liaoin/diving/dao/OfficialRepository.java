package com.liaoin.diving.dao;

import com.liaoin.diving.entity.Official;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author : huqingxi
 * @describe :
 * @date 2018/06/07
 */
public interface OfficialRepository extends JpaRepository<Official, Integer>, JpaSpecificationExecutor<Official> {

    @Query(value = "select * from t_official where is_delete = 0 order by create_time desc ", nativeQuery = true)
    List<Official> findAll();
}
