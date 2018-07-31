package com.liaoin.diving.service.impl;

import com.liaoin.diving.common.PageBean;
import com.liaoin.diving.dao.ActivityCategoryRepository;
import com.liaoin.diving.entity.ActivityCategory;
import com.liaoin.diving.service.ActivityCategoryService;
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
public class ActivityCategoryServiceImpl implements ActivityCategoryService {

    @Resource
    private ActivityCategoryRepository activityCategoryRepository;

    @Override
    public void insert(ActivityCategory activityCategory) {
        activityCategoryRepository.save(activityCategory);
    }

    @Override
    public void update(ActivityCategory activityCategory) {
        activityCategoryRepository.save(activityCategory);
    }

    @Override
    public void delete(Integer[] ids) {
        List<ActivityCategory> activityCategoryList = new ArrayList<>();
        for (Integer id : ids) {
            ActivityCategory activityCategory = activityCategoryRepository.findOne(id);
            if (activityCategory == null) {
                continue;
            }
            activityCategory.setIsDelete("1");
            activityCategoryList.add(activityCategory);
        }
        activityCategoryRepository.save(activityCategoryList);
    }

    @Override
    public ActivityCategory findOne(Integer id) {
        return activityCategoryRepository.findOne(id);
    }

    @Override
    public PageBean<ActivityCategory> pageQuery(Pageable pageable, ActivityCategory activityCategory) {
        // 1.查询条件
        Specification<ActivityCategory> specification = new Specification<ActivityCategory>() {
            @Override
            public Predicate toPredicate(Root<ActivityCategory> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // 普通条件
                List<Predicate> list = new ArrayList<>();
                if (activityCategory != null) {
                    if (activityCategory.getId() != null) {
                        list.add(cb.equal(root.get("id").as(Integer.class), activityCategory.getId()));
                    }
                    if (activityCategory.getCreateTime() != null) {
                        list.add(cb.equal(root.get("createTime").as(java.util.Date.class), activityCategory.getCreateTime()));
                    }
                    if ((activityCategory.getName() != null) && (!activityCategory.getName().trim().equals(""))) {
                        list.add(cb.like(root.get("name").as(String.class), "%" + activityCategory.getName() + "%"));
                    }
                    if (activityCategory.getUpdateTime() != null) {
                        list.add(cb.equal(root.get("updateTime").as(java.util.Date.class), activityCategory.getUpdateTime()));
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
        Page<ActivityCategory> page = activityCategoryRepository.findAll(specification, pageable);
        if (page.getTotalElements() == 0) {
            return null;
        }
        // 3.封装数据
        PageBean<ActivityCategory> pageBean = new PageBean<>();
        pageBean.setTotal(page.getTotalElements());
        pageBean.setRows(page.getContent());
        return pageBean;
    }
}

