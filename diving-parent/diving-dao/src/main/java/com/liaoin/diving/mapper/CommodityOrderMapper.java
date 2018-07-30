package com.liaoin.diving.mapper;

import com.liaoin.diving.entity.CommodityOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommodityOrderMapper {
    /**
     * 查询集合 并查询出地址
     * @return
     */
    List<CommodityOrder> findAll(@Param("userId") Integer userId);
}
