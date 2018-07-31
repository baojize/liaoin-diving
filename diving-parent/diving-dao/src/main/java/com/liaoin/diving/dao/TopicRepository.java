package com.liaoin.diving.dao;

import com.liaoin.diving.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @作者：huqingxi
 * @描述：
 * @日期：2018/7/10 17:23
 */
public interface TopicRepository extends JpaRepository<Topic, Integer>, JpaSpecificationExecutor<Topic> {

    List<Topic> findByIsColumnAndIsDelete(String isColumn, String isDelete);
}
