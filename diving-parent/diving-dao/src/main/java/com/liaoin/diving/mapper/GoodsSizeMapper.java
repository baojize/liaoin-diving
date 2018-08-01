package com.liaoin.diving.mapper;

import com.liaoin.diving.entity.GoodsSize;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: Allen
 * @Description:
 * @Date: Created in 2018/7/31 15:19
 */
public interface GoodsSizeMapper {
    List<GoodsSize> findGoodsSizeByGoodsId(@Param("id") Integer id);
}
