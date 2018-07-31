package com.liaoin.diving.dao;

import com.liaoin.diving.entity.Content;
import com.liaoin.diving.entity.Discussion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DiscussionRepository extends JpaRepository<Discussion, Integer>, JpaSpecificationExecutor<Discussion> {

    @Query(value = "select * from t_discussion where content_id = ?1 order by create_time desc", nativeQuery = true)
    List<Discussion>  findNewNotice(Integer id);

    // 查询当前用户发布的内容的ids
    @Query(value = "select con.id from t_content con  join t_discussion disc on con.id = disc.content_id  where con.user_id = ?1", nativeQuery = true)
    List<Integer> findCurrentUpContentIds(Integer id);
}