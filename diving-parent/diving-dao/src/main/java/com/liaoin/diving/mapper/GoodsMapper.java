package com.liaoin.diving.mapper;

import com.liaoin.diving.entity.Goods;
import com.liaoin.diving.view.RecommendGoodsView;

import java.util.List;

/**
 * @Author: Allen
 * @Description:
 * @Date: Created in 2018/7/27 14:35
 */
public interface GoodsMapper {
    List<RecommendGoodsView> findRecommendOrderByCreateTime();

    void setReco(Integer id);

    List<Goods> getReco();

    void cancelReco(Integer id);
}
