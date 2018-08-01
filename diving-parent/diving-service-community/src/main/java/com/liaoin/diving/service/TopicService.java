package com.liaoin.diving.service;

import com.liaoin.diving.common.PageBean;
import com.liaoin.diving.entity.Topic;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TopicService {
    void insert(Topic topic);

    void update(Topic topic);

    void delete(Integer[] ids);

    Topic findOne(Integer id);

    PageBean<Topic> pageQuery(Pageable pageable, Topic topic);

    void setColumn(Integer[] ids);

    List<Topic> findByColunm();

    // ----------------------- pass


    List<Topic> findByType(String type);

    List<Topic> findByDry();

    List<Topic> findByFeuill();

    void updateDry(Integer id);

    void updateFeuill(Integer id);

    void setBroadcast(Integer id);
}

