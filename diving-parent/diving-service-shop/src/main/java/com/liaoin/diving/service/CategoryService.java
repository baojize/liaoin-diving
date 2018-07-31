package com.liaoin.diving.service;

import com.liaoin.diving.common.PageBean;
import com.liaoin.diving.entity.Category;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {
    void insert(Category category);

    void update(Category category);

    void delete(Integer[] ids);

    Category findOne(Integer id);

    PageBean<Category> pageQuery(Pageable pageable, Category category);
}

