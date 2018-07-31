package com.liaoin.diving.service.impl;

import com.liaoin.diving.common.PageBean;
import com.liaoin.diving.dao.SpecOptionRepository;
import com.liaoin.diving.dao.SpecRepository;
import com.liaoin.diving.entity.Spec;
import com.liaoin.diving.entity.SpecOption;
import com.liaoin.diving.service.SpecService;
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
public class SpecServiceImpl implements SpecService {

    @Resource
    private SpecRepository specRepository;
    @Resource
    private SpecOptionRepository specOptionRepository;

    @Override
    public void insert(Spec spec) {
        for (SpecOption specOption : spec.getSpecOptionList()) {
            specOption.setSpec(spec);
        }
        specRepository.save(spec);
    }

    @Override
    public void update(Spec spec) {
        // 1.删除规格选项列表
        specOptionRepository.deleteBySpec(spec);
        // 2.更新规格
        for (SpecOption specOption : spec.getSpecOptionList()) {
            specOption.setSpec(spec);
        }
        specRepository.save(spec);
    }

    @Override
    public void delete(Integer[] ids) {
        List<Spec> specList = new ArrayList<>();
        for (Integer id : ids) {
            Spec spec = specRepository.findOne(id);
            if (spec == null) {
                continue;
            }
            spec.setIsDelete("1");
            specList.add(spec);
        }
        specRepository.save(specList);
    }

    @Override
    public Spec findOne(Integer id) {
        return specRepository.findOne(id);
    }

    @Override
    public PageBean<Spec> pageQuery(Pageable pageable, Spec spec) {
        // 1.查询条件
        Specification<Spec> specification = new Specification<Spec>() {
            @Override
            public Predicate toPredicate(Root<Spec> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // 普通条件
                List<Predicate> list = new ArrayList<>();
                if (spec != null) {
                    if (spec.getId() != null) {
                        list.add(cb.equal(root.get("id").as(Integer.class), spec.getId()));
                    }
                    if ((spec.getName() != null) && (!spec.getName().trim().equals(""))) {
                        list.add(cb.like(root.get("name").as(String.class), "%" + spec.getName() + "%"));
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
        Page<Spec> page = specRepository.findAll(specification, pageable);
        if (page.getTotalElements() == 0) {
            return null;
        }
        // 3.封装数据
        PageBean<Spec> pageBean = new PageBean<>();
        pageBean.setTotal(page.getTotalElements());
        pageBean.setRows(page.getContent());
        return pageBean;
    }
}

