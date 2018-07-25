package com.liaoin.diving.service;

import com.liaoin.diving.common.PageBean;
import com.liaoin.diving.entity.Goods;
import org.springframework.data.domain.Pageable;

public interface GoodsService {
    void insert(Goods goods);

    void update(Goods goods);

    void delete(Integer[] ids);

    Goods findOne(Integer id);

    PageBean<Goods> pageQuery(Pageable pageable, Goods goods);
}

