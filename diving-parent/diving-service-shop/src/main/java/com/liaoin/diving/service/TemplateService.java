package com.liaoin.diving.service;

import com.liaoin.diving.common.PageBean;
import com.liaoin.diving.entity.Template;
import org.springframework.data.domain.Pageable;

public interface TemplateService {
    void insert(Template template);

    void update(Template template);

    void delete(Integer[] ids);

    Template findOne(Integer id);

    PageBean<Template> pageQuery(Pageable pageable, Template template);
}

