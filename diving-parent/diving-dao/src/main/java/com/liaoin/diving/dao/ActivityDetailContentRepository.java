package com.liaoin.diving.dao;

import com.liaoin.diving.entity.ActivityDetailContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Author: Allen
 * @Description:
 * @Date: Created in 2018/7/25 11:10
 */
public interface ActivityDetailContentRepository extends JpaRepository<ActivityDetailContent, Integer>, JpaSpecificationExecutor<ActivityDetailContent> {
    //    @Query(value = "",nativeQuery = true)
    List<ActivityDetailContent> findByActivity_Id(Integer activityId);

    ActivityDetailContent findByActivity_IdAndActivityDetails_Id(Integer activity_id,Integer activity_details_id);
}
