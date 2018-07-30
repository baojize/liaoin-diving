package com.liaoin.diving.dao;

import com.liaoin.diving.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Integer>, JpaSpecificationExecutor<Activity> {


    List<Activity> findByNameLike(String name);

    @Query(value = "update t_activity set is_broadcase = 1 where id = ?1", nativeQuery = true)
    @Modifying
    void setBroadcast(Integer id);

    @Query(value = "select * from t_activity where is_recommend = 1 and is_delete = 0 order by  activity_time", nativeQuery = true)
    List<Activity> findRecommend();

    List<Activity> findByActivityCategory(Integer id);

    /*List<Activity> findByNameLike(String name);*/

   /* @Query(value = "update t_activity set is_broadcase = 1 where id = ?1", nativeQuery = true)
    @Modifying
    void setBroadcast(Integer id);*/
}