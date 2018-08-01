package com.liaoin.diving.service;

import com.liaoin.diving.common.PageBean;
import com.liaoin.diving.entity.Spec;
import org.springframework.data.domain.Pageable;

public interface SpecService {
    void insert(Spec spec);

    void update(Spec spec);

    void delete(Integer[] ids);

    Spec findOne(Integer id);

    PageBean<Spec> pageQuery(Pageable pageable, Spec spec);
}

