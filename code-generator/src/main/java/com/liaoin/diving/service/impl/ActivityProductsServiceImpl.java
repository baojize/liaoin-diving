package com.liaoin.diving.service.impl;

import com.liaoin.diving.common.PageBean;
import com.liaoin.diving.dao.ActivityProductsRepository;
import com.liaoin.diving.entity.ActivityProducts;
import com.liaoin.diving.service.ActivityProductsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class ActivityProductsServiceImpl implements ActivityProductsService {

    @Resource
    private ActivityProductsRepository activityProductsRepository;

    @Override
    public void insert(ActivityProducts activityProducts) {
        activityProducts.setIsDelete("0");
        activityProductsRepository.save(activityProducts);
    }

    @Override
    public void update(ActivityProducts activityProducts) {
        activityProducts.setIsDelete(null);
        activityProductsRepository.save(activityProducts);
    }

    @Override
    public void delete(Integer[] ids) {
        List<ActivityProducts> activityProductsList = new ArrayList<>();
        for (Integer id : ids) {
            ActivityProducts activityProducts = activityProductsRepository.findOne(id);
            if (activityProducts == null) {
                continue;
            }
            activityProducts.setIsDelete("1");
            activityProductsList.add(activityProducts);
        }
        activityProductsRepository.save(activityProductsList);
    }

    @Override
    public ActivityProducts findOne(Integer id) {
        return activityProductsRepository.findOne(id);
    }

    @Override
    public PageBean<ActivityProducts> pageQuery(Pageable pageable, ActivityProducts activityProducts) {
        // 1.查询条件
        Specification<ActivityProducts> specification = new Specification<ActivityProducts>() {
            @Override
            public Predicate toPredicate(Root<ActivityProducts> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // 普通条件
                List<Predicate> list = new ArrayList<>();
                if (activityProducts != null) {
                    if (activityProducts.getId() != null) {
                        list.add(cb.equal(root.get("id").as(Integer.class), activityProducts.getId()));
                    }
                    if ((activityProducts.getName() != null) && (!activityProducts.getName().trim().equals(""))) {
                        list.add(cb.like(root.get("name").as(String.class), "%" + activityProducts.getName() + "%"));
                    }
                    if (activityProducts.getPrice() != null) {
                        list.add(cb.equal(root.get("price").as(BigDecimal.class), activityProducts.getPrice()));
                    }
                }
                // 逻辑删除条件
                list.add(cb.notEqual(root.get("isDelete").as(String.class),"1"));
                // 合并查询条件
                Predicate[] predicates = new Predicate[list.size()];
                query.where(cb.and(list.toArray(predicates)));
                return query.getRestriction();
            }
        };
        // 2.获取数据
        Page<ActivityProducts> page = activityProductsRepository.findAll(specification, pageable);
        if (page.getTotalElements() == 0) {
            return null;
        }
        // 3.封装数据
        PageBean<ActivityProducts> pageBean = new PageBean<>();
        pageBean.setTotal(page.getTotalElements());
        pageBean.setRows(page.getContent());
        return pageBean;
    }
}

