package com.liaoin.diving.service.impl;

import com.github.pagehelper.PageHelper;
import com.liaoin.diving.common.PageBean;
import com.liaoin.diving.common.PageHelp;
import com.liaoin.diving.dao.CategoryRepository;
import com.liaoin.diving.dao.GoodsRepository;
import com.liaoin.diving.entity.Goods;
import com.liaoin.diving.mapper.GoodsMapper;
import com.liaoin.diving.service.GoodsService;
import com.liaoin.diving.utils.UpdateUtils;
import com.liaoin.diving.view.RecommendGoodsView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class GoodsServiceImpl implements GoodsService {

    @Resource
    private GoodsRepository goodsRepository;

    @Resource
    private CategoryRepository categoryRepository;

    @Resource
    private GoodsMapper goodsMapper;

    @Override
    public void insert(Goods goods) {
        goods.setCreateTime(new Date());
        goods.setUpdateTime(new Date());
        goods.setIsDelete("0");
        goodsRepository.save(goods);
    }

    @Override
    public void update(Goods newgoods) {
        newgoods.setUpdateTime(new Date());
        Goods oldGoods = goodsRepository.findOne(newgoods.getId());
        UpdateUtils.copyNonNullProperties(newgoods, oldGoods);
        goodsRepository.save(oldGoods);
    }

    @Override
    public void delete(Integer[] ids) {
        List<Goods> goodsList = new ArrayList<>();
        for (Integer id : ids) {
            Goods goods = goodsRepository.findOne(id);
            if (goods == null) {
                continue;
            }
            goods.setIsDelete("1");
            goods.setIsSale("0");
            goods.setDeleteTime(new Date());
            goodsList.add(goods);
        }
        goodsRepository.save(goodsList);
    }

    @Override
    public Goods findOne(Integer id) {
        return goodsRepository.findOne(id);
    }

    @Override
    public PageBean<Goods> pageQuery(Pageable pageable, Goods goods) {
        // 1.查询条件
        Specification<Goods> specification = new Specification<Goods>() {
            @Override
            public Predicate toPredicate(Root<Goods> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // 普通条件
                List<Predicate> list = new ArrayList<>();
                if (goods != null) {
                    if (goods.getId() != null) {
                        list.add(cb.equal(root.get("id").as(Integer.class), goods.getId()));
                    }
                    if (goods.getCreateTime() != null) {
                        list.add(cb.equal(root.get("createTime").as(java.util.Date.class), goods.getCreateTime()));
                    }
                    if (goods.getDeleteTime() != null) {
                        list.add(cb.equal(root.get("deleteTime").as(java.util.Date.class), goods.getDeleteTime()));
                    }
                    if (goods.getDiscount() != null) {
                        list.add(cb.lessThanOrEqualTo(root.get("discount").as(BigDecimal.class), goods.getDiscount()));
                    }
                    if ((goods.getIntroduction() != null) && (!goods.getIntroduction().trim().equals(""))) {
                        list.add(cb.like(root.get("introduction").as(String.class), "%" + goods.getIntroduction() + "%"));
                    }
                    if ((goods.getIsHome() != null) && (!goods.getIsHome().trim().equals(""))) {
                        list.add(cb.like(root.get("isHome").as(String.class), "%" + goods.getIsHome() + "%"));
                    }
                    if ((goods.getIsRecommend() != null) && (!goods.getIsRecommend().trim().equals(""))) {
                        list.add(cb.like(root.get("isRecommend").as(String.class), "%" + goods.getIsRecommend() + "%"));
                    }
                    if ((goods.getName() != null) && (!goods.getName().trim().equals(""))) {
                        list.add(cb.like(root.get("name").as(String.class), "%" + goods.getName() + "%"));
                    }
                    if (goods.getPrice() != null) {
                        list.add(cb.equal(root.get("price").as(BigDecimal.class), goods.getPrice()));
                    }
                    if (goods.getUpdateTime() != null) {
                        list.add(cb.equal(root.get("updateTime").as(java.util.Date.class), goods.getUpdateTime()));
                    }
                    if (goods.getOrders() != null) {
                        list.add(cb.equal(root.get("orders").as(Integer.class), goods.getOrders()));
                    }
                    //默认不删除
                    if (goods.getIsDelete() != null) {
                        list.add(cb.like(root.get("isDelete").as(String.class), "%" + goods.getIsDelete() + "%"));
                    }
                    //默认上架
                    if (goods.getIsSale() != null) {
                        list.add(cb.like(root.get("isSale").as(String.class), "%" + goods.getIsSale() + "%"));
                    }
                }
                // 合并查询条件
                Predicate[] predicates = new Predicate[list.size()];
                query.where(cb.and(list.toArray(predicates)));
                return query.getRestriction();
            }
        };
        // 2.获取数据
        Page<Goods> page = goodsRepository.findAll(specification, pageable);
        if (page.getTotalElements() == 0) {
            return null;
        }
        // 3.封装数据
        PageBean<Goods> pageBean = new PageBean<>();
        pageBean.setTotal(page.getTotalElements());
        pageBean.setRows(page.getContent());
        return pageBean;
    }

    @Override
    public PageBean<Goods> findByCategory(Pageable pageable, List<Integer> ids) {
        List<Goods> goodsList = new ArrayList<>();
        Page<Goods> page = goodsRepository.findByCategory_IdIn(ids, pageable);
//        PageBean
        if (page.getTotalElements() == 0) {
            return null;
        }
        PageBean<Goods> pageBean = new PageBean<>();
        pageBean.setTotal(page.getTotalElements());
        pageBean.setRows(page.getContent());
        return pageBean;
    }

    //获取推荐商品
    @Override
    public List<RecommendGoodsView> findRecommendOrderByCreateTime(PageHelp pageHelp) {
        PageHelper.startPage(pageHelp.getStart(), pageHelp.getPageSize());
        List<RecommendGoodsView> goodsList = goodsMapper.findRecommendOrderByCreateTime();
        for (RecommendGoodsView recommendGoodsView : goodsList) {
            BigDecimal newMoney = recommendGoodsView.getPrice().multiply(recommendGoodsView.getDiscount());
            recommendGoodsView.setMoney(newMoney);
        }
        return goodsList;
    }

    //设置商品归属
    @Override
    public Integer setRecommendGoods(Integer[] ids, Integer mode, Integer symbol) {
        Integer sign = goodsMapper.setRecommendGoodsByIds(ids, mode, symbol);
        return sign;
    }
}

