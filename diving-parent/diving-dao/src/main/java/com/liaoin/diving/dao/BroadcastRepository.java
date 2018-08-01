package com.liaoin.diving.dao;

import com.liaoin.diving.entity.Broadcast;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface BroadcastRepository extends JpaRepository<Broadcast, Integer> , JpaSpecificationExecutor<Broadcast> {
    @Query(value = "select * from t_broadcast order by create_time desc limit 1", nativeQuery = true)
    Broadcast findNewDate();

    // 话题设置轮播图
    @Query(value = "insert into t_broadcast(create_time,update_time,topic_id) values(?1,?2,?3)", nativeQuery = true)
    @Modifying
    void insertTopic(Date createTime, Date updateTime, Integer topic_id);

    // 活动设置轮播图
    @Query(value = "insert into t_broadcast(create_time,update_time,activity_id) values(?1,?2,?3)", nativeQuery = true)
    @Modifying
    void insertActivity(Date createTime, Date updateTime, Integer activity_id);

    // 默认获取更新时间
    @Query(value = "select * from t_broadcast order by create_time desc limit ?1", nativeQuery = true)
    List<Broadcast> getBroadcast(Integer size);

}
