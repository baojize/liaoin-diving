package com.liaoin.diving.dao;

import com.liaoin.diving.entity.Goods;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GoodsRepository extends JpaRepository<Goods, Integer>, JpaSpecificationExecutor<Goods> {


    /**
     * 根据小分类ID进行查询
     * @param ids
     * @return
     */
    Page<Goods> findByCategory_IdIn(List<Integer> ids, Pageable pageable);

}