package com.liaoin.diving.service.impl;

import com.liaoin.diving.common.PageBean;
import com.liaoin.diving.dao.BrandRepository;
import com.liaoin.diving.entity.Brand;
import com.liaoin.diving.service.BrandService;
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
public class BrandServiceImpl implements BrandService {

    @Resource
    private BrandRepository brandRepository;

    @Override
    public void insert(Brand brand) {
        brand.setIsDelete("0");
        brandRepository.save(brand);
    }

    @Override
    public void update(Brand brand) {
        brandRepository.save(brand);
    }

    @Override
    public void delete(Integer[] ids) {
        List<Brand> brandList = new ArrayList<>();
        for (Integer id : ids) {
            Brand brand = brandRepository.findOne(id);
            if (brand == null) {
                continue;
            }
            brand.setIsDelete("1");
            brandList.add(brand);
        }
        brandRepository.save(brandList);
    }

    @Override
    public Brand findOne(Integer id) {
        return brandRepository.findOne(id);
    }

    @Override
    public PageBean<Brand> pageQuery(Pageable pageable, Brand brand) {
        // 1.查询条件
        Specification<Brand> specification = new Specification<Brand>() {
            @Override
            public Predicate toPredicate(Root<Brand> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // 普通条件
                List<Predicate> list = new ArrayList<>();
                if (brand != null) {
                    if (brand.getId() != null) {
                        list.add(cb.equal(root.get("id").as(Integer.class), brand.getId()));
                    }
                    if ((brand.getFirstChar() != null) && (!brand.getFirstChar().trim().equals(""))) {
                        list.add(cb.like(root.get("firstChar").as(String.class), "%" + brand.getFirstChar() + "%"));
                    }
                    if ((brand.getName() != null) && (!brand.getName().trim().equals(""))) {
                        list.add(cb.like(root.get("name").as(String.class), "%" + brand.getName() + "%"));
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
        Page<Brand> page = brandRepository.findAll(specification, pageable);
        if (page.getTotalElements() == 0) {
            return null;
        }
        // 3.封装数据
        PageBean<Brand> pageBean = new PageBean<>();
        pageBean.setTotal(page.getTotalElements());
        pageBean.setRows(page.getContent());
        return pageBean;
    }
}

