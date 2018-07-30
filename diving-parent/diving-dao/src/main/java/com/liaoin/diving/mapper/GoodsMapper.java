package com.liaoin.diving.mapper;

import com.liaoin.diving.view.RecommendGoodsView;

import java.util.List;

/**
 * @Author: Allen
 * @Description:
 * @Date: Created in 2018/7/27 14:35
 */
public interface GoodsMapper {
    List<RecommendGoodsView> findRecommendOrderByCreateTime();
}
