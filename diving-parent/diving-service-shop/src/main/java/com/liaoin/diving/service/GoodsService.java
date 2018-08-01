package com.liaoin.diving.service;

import com.liaoin.diving.common.PageBean;
import com.liaoin.diving.common.PageHelp;
import com.liaoin.diving.entity.Goods;
import com.liaoin.diving.view.EqConditionView;
import com.liaoin.diving.view.RecommendGoodsView;
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
     *
     * @param ids
     * @return 返回
     */
    PageBean<Goods> findByCategory(Pageable pageable, List<Integer> ids);

    /**
     * 查询推荐商品 分页
     *
     * @param pageHelp
     * @return
     */
    List<RecommendGoodsView> findRecommendOrderByCreateTime(PageHelp pageHelp);

    /**
<<<<<<< HEAD
     * 创建推荐装备
     * @param id
     */
    void setReco(Integer id);

    /**
     * 取消推荐装备
     * @param id
     */
    void cancelReco(Integer id);

    /**
     * 查询所有推荐装备
     * @param pageHelp
     * @return
     */
    List<Goods> getReco(PageHelp pageHelp);



     /* 设置推荐商品
     *
     * @param ids
     * @param symbol
     * @return
     */
    Integer setRecommendGoods(Integer[] ids, Integer mode, Integer symbol);

    /**
     * 条件查询
     * @return
     */
    List<Goods> condition(PageHelp pageHelp, EqConditionView condition);

    /**
     * 设置首页
     * @param id
     */
    void setHome(Integer id);

    /**
     * 获取首页数据
     * @param pageHelp
     */
    List<Goods> getEqHome(PageHelp pageHelp);

    /**
     * 查询所有装备  is_delete 排除
     * @param pageHelp
     * @return
     */
    List<Goods> findAll(PageHelp pageHelp);
}

