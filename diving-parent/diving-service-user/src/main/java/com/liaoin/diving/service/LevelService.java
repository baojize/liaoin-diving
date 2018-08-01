package com.liaoin.diving.service;

import com.liaoin.diving.common.PageBean;
import com.liaoin.diving.entity.Level;
import org.springframework.data.domain.Pageable;

public interface LevelService {
    void insert(Level level);

    void update(Level level);

    void delete(Integer[] ids);

    Level findOne(Integer id);

    PageBean<Level> pageQuery(Pageable pageable, Level level);
}

