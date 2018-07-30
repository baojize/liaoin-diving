package com.liaoin.diving.service;

import com.liaoin.diving.common.PageBean;
import com.liaoin.diving.entity.Goods;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GoodsService {
    void insert(Goods goods);

    void update(Goods goods);

    void delete(Integer[] ids);

    Goods findOne(Integer id);

    PageBean<Goods> pageQuery(Pageable pageable, Goods goods);

   /* List<Goods> findAll(Goods goods);*/

    /**
     * 根据查询传入的小分类id集合进行查询
     * @param ids
     * @return 返回
     */
//    List<Goods> findByCategory(Pageable pageable,List<Integer> ids);
    PageBean<Goods> findByCategory(Pageable pageable,List<Integer> ids);
}

