package com.liaoin.diving.service.impl;

import com.liaoin.diving.common.PageBean;
import com.liaoin.diving.dao.SpecOptionRepository;
import com.liaoin.diving.entity.SpecOption;
import com.liaoin.diving.service.SpecOptionService;
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
public class SpecOptionServiceImpl implements SpecOptionService {

    @Resource
    private SpecOptionRepository specOptionRepository;

    @Override
    public void insert(SpecOption specOption) {
        specOptionRepository.save(specOption);
    }

    @Override
    public void update(SpecOption specOption) {
        specOptionRepository.save(specOption);
    }

    @Override
    public void delete(Integer[] ids) {
        List<SpecOption> specOptionList = new ArrayList<>();
        for (Integer id : ids) {
            SpecOption specOption = specOptionRepository.findOne(id);
            if (specOption == null) {
                continue;
            }
            specOption.setIsDelete("1");
            specOptionList.add(specOption);
        }
        specOptionRepository.save(specOptionList);
    }

    @Override
    public SpecOption findOne(Integer id) {
        return specOptionRepository.findOne(id);
    }

    @Override
    public PageBean<SpecOption> pageQuery(Pageable pageable, SpecOption specOption) {
        // 1.查询条件
        Specification<SpecOption> specification = new Specification<SpecOption>() {
            @Override
            public Predicate toPredicate(Root<SpecOption> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // 普通条件
                List<Predicate> list = new ArrayList<>();
                if (specOption != null) {
                    if (specOption.getId() != null) {
                        list.add(cb.equal(root.get("id").as(Integer.class), specOption.getId()));
                    }
                    if ((specOption.getName() != null) && (!specOption.getName().trim().equals(""))) {
                        list.add(cb.like(root.get("name").as(String.class), "%" + specOption.getName() + "%"));
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
        Page<SpecOption> page = specOptionRepository.findAll(specification, pageable);
        if (page.getTotalElements() == 0) {
            return null;
        }
        // 3.封装数据
        PageBean<SpecOption> pageBean = new PageBean<>();
        pageBean.setTotal(page.getTotalElements());
        pageBean.setRows(page.getContent());
        return pageBean;
    }
}

