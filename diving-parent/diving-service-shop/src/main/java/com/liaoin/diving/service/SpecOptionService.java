package com.liaoin.diving.service;

import com.liaoin.diving.common.PageBean;
import com.liaoin.diving.entity.SpecOption;
import org.springframework.data.domain.Pageable;

public interface SpecOptionService {
    void insert(SpecOption specOption);

    void update(SpecOption specOption);

    void delete(Integer[] ids);

    SpecOption findOne(Integer id);

    PageBean<SpecOption> pageQuery(Pageable pageable, SpecOption specOption);
}

