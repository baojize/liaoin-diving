package com.liaoin.diving.service;

import com.liaoin.diving.common.PageBean;
import com.liaoin.diving.entity.Brand;
import org.springframework.data.domain.Pageable;

public interface BrandService {
    void insert(Brand brand);

    void update(Brand brand);

    void delete(Integer[] ids);

    Brand findOne(Integer id);

    PageBean<Brand> pageQuery(Pageable pageable, Brand brand);
}

