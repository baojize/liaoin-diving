package com.liaoin.diving.mapper;

import com.liaoin.diving.view.RecommendGoodsView;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: Allen
 * @Description:
 * @Date: Created in 2018/7/27 14:35
 */
public interface GoodsMapper {
    /**
     * 查询推荐商品
     *
     * @return
     */
    List<RecommendGoodsView> findRecommendOrderByCreateTime();

    /**
     * 设置商品归属
     * @param ids
     * @param mode
     * @param symbol
     * @return
     */
    Integer setRecommendGoodsByIds(@Param("ids") Integer[] ids,@Param("mode") Integer mode, @Param("symbol") Integer symbol);

}
