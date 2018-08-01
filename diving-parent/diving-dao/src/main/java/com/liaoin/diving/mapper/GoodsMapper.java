package com.liaoin.diving.mapper;

import com.liaoin.diving.entity.Goods;
import com.liaoin.diving.view.EqConditionView;
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

    void setReco(Integer id);

    List<Goods> getReco();

    void cancelReco(Integer id);
    /**
     * 设置商品归属
     * @param ids
     * @param mode
     * @param symbol
     * @return
     */
    Integer setRecommendGoodsByIds(@Param("ids") Integer[] ids, @Param("mode") Integer mode, @Param("symbol") Integer symbol);

    /**
     * 条件查询
     * @return
     */
    List<Goods> condition(EqConditionView conditon);

    /**
     * 设置首页
     * @param id
     */
    void setHome(@Param("id") Integer id);

    /**
     * 获取首页数据
     * @return
     */
    List<Goods> getEqHome();


    /**
     * 查询所有装备
     * @return
     */
    List<Goods> findAll();

}
