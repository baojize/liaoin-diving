package com.liaoin.diving.service.impl;

import com.liaoin.diving.common.PageBean;
import com.liaoin.diving.dao.BigCategoryRepository;
import com.liaoin.diving.entity.BigCategory;
import com.liaoin.diving.service.BigCategoryService;
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
public class BigCategoryServiceImpl implements BigCategoryService {

    @Resource
    private BigCategoryRepository bigCategoryRepository;

    @Override
    public void insert(BigCategory bigCategory) {
        bigCategory.setIsDelete("0");
        bigCategoryRepository.save(bigCategory);
    }

    @Override
    public void update(BigCategory bigCategory) {
        bigCategory.setIsDelete(null);
        bigCategoryRepository.save(bigCategory);
    }

    @Override
    public void delete(Integer[] ids) {
        List<BigCategory> bigCategoryList = new ArrayList<>();
        for (Integer id : ids) {
            BigCategory bigCategory = bigCategoryRepository.findOne(id);
            if (bigCategory == null) {
                continue;
            }
            bigCategory.setIsDelete("1");
            bigCategoryList.add(bigCategory);
        }
        bigCategoryRepository.save(bigCategoryList);
    }

    @Override
    public BigCategory findOne(Integer id) {
        return bigCategoryRepository.findOne(id);
    }

    @Override
    public PageBean<BigCategory> pageQuery(Pageable pageable, BigCategory bigCategory) {
        // 1.查询条件
        Specification<BigCategory> specification = new Specification<BigCategory>() {
            @Override
            public Predicate toPredicate(Root<BigCategory> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // 普通条件
                List<Predicate> list = new ArrayList<>();
                if (bigCategory != null) {
                    if (bigCategory.getId() != null) {
                        list.add(cb.equal(root.get("id").as(Integer.class), bigCategory.getId()));
                    }
                    if ((bigCategory.getName() != null) && (!bigCategory.getName().trim().equals(""))) {
                        list.add(cb.like(root.get("name").as(String.class), "%" + bigCategory.getName() + "%"));
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
        Page<BigCategory> page = bigCategoryRepository.findAll(specification, pageable);
        if (page.getTotalElements() == 0) {
            return null;
        }
        // 3.封装数据
        PageBean<BigCategory> pageBean = new PageBean<>();
        pageBean.setTotal(page.getTotalElements());
        pageBean.setRows(page.getContent());
        return pageBean;
    }
}

