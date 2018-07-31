package com.liaoin.diving.service.impl;

import com.liaoin.diving.common.PageBean;
import com.liaoin.diving.dao.CategoryRepository;
import com.liaoin.diving.entity.Category;
import com.liaoin.diving.service.CategoryService;
import com.liaoin.diving.utils.UpdateUtils;
import org.hibernate.sql.Update;
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
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class CategoryServiceImpl implements CategoryService {

    @Resource
    private CategoryRepository categoryRepository;

    @Override
    public void insert(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public void update(Category category) {
        Category one = categoryRepository.findById(category.getId());
        UpdateUtils.copyNonNullProperties(category, one);
        categoryRepository.save(one);
    }

    @Override
    public void delete(Integer[] ids) {
        List<Category> categoryList = new ArrayList<>();
        for (Integer id : ids) {
            Category category = categoryRepository.findOne(id);
            if (category == null) {
                continue;
            }
            category.setIsDelete("1");
            categoryList.add(category);
        }
        categoryRepository.save(categoryList);
    }

    @Override
    public Category findOne(Integer id) {
        return categoryRepository.findById(id);
    }

    @Override
    public PageBean<Category> pageQuery(Pageable pageable, Category category) {
        // 1.查询条件
        Specification<Category> specification = new Specification<Category>() {
            @Override
            public Predicate toPredicate(Root<Category> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // 普通条件
                List<Predicate> list = new ArrayList<>();
                if (category != null) {
                    if (category.getId() != null) {
                        list.add(cb.equal(root.get("id").as(Integer.class), category.getId()));
                    }
                    if ((category.getName() != null) && (!category.getName().trim().equals(""))) {
                        list.add(cb.like(root.get("name").as(String.class), "%" + category.getName() + "%"));
                    }
                }
                // 逻辑删除条件
                list.add(cb.notEqual(root.get("isDelete").as(String.class), "1"));
                // 合并查询条件
                Predicate[] predicates = new Predicate[list.size()];
                query.where(cb.and(list.toArray(predicates)));
                return query.getRestriction();
            }
        };
        // 2.获取数据
        Page<Category> page = categoryRepository.findAll(specification, pageable);
        if (page.getTotalElements() == 0) {
            return null;
        }
        // 3.封装数据
        PageBean<Category> pageBean = new PageBean<>();
        pageBean.setTotal(page.getTotalElements());
        pageBean.setRows(page.getContent());
        return pageBean;
    }
}

