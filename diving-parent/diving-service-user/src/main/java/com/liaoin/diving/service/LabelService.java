package com.liaoin.diving.service;

import com.liaoin.diving.common.PageBean;
import com.liaoin.diving.entity.Label;
import org.springframework.data.domain.Pageable;

public interface LabelService {
    void insert(Label label);

    void update(Label label);

    void delete(Integer[] ids);

    Label findOne(Integer id);

    PageBean<Label> pageQuery(Pageable pageable, Label label);
}

