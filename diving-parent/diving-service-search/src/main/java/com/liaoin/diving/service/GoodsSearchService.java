package com.liaoin.diving.service;

import com.liaoin.diving.common.PageBean;
import com.liaoin.diving.entity.Goods;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GoodsSearchService {
    void save(List<Goods> contentList);

    void delete(List<Goods> contentList);

    PageBean<Goods> search(Pageable pageable, String key);
}
