package com.liaoin.diving.service;

import com.liaoin.diving.common.PageBean;

import com.liaoin.diving.entity.BigCategory;
import org.springframework.data.domain.Pageable;

public interface BigCategoryService {
    void insert(BigCategory bigCategory);

    void update(BigCategory bigCategory);

    void delete(Integer[] ids);

    BigCategory findOne(Integer id);

    PageBean<BigCategory> pageQuery(Pageable pageable, BigCategory bigCategory);
}

