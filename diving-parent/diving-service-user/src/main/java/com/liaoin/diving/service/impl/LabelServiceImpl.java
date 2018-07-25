package com.liaoin.diving.service.impl;

import com.liaoin.diving.common.PageBean;
import com.liaoin.diving.dao.LabelRepository;
import com.liaoin.diving.entity.Label;
import com.liaoin.diving.service.LabelService;
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
public class LabelServiceImpl implements LabelService {

    @Resource
    private LabelRepository labelRepository;

    @Override
    public void insert(Label label) {
        label.setIsDelete("0");
        labelRepository.save(label);
    }

    @Override
    public void update(Label label) {
        labelRepository.save(label);
    }

    @Override
    public void delete(Integer[] ids) {
        List<Label> labelList = new ArrayList<>();
        for (Integer id : ids) {
            Label label = labelRepository.findOne(id);
            if (label == null) {
                continue;
            }
            label.setIsDelete("1");
            labelList.add(label);
        }
        labelRepository.save(labelList);
    }

    @Override
    public Label findOne(Integer id) {
        return labelRepository.findOne(id);
    }

    @Override
    public PageBean<Label> pageQuery(Pageable pageable, Label label) {
        // 1.查询条件
        Specification<Label> specification = new Specification<Label>() {
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // 普通条件
                List<Predicate> list = new ArrayList<>();
                if (label != null) {
                    if (label.getId() != null) {
                        list.add(cb.equal(root.get("id").as(Integer.class), label.getId()));
                    }
                    if ((label.getContent() != null) && (!label.getContent().trim().equals(""))) {
                        list.add(cb.like(root.get("content").as(String.class), "%" + label.getContent() + "%"));
                    }
                    if ((label.getName() != null) && (!label.getName().trim().equals(""))) {
                        list.add(cb.like(root.get("name").as(String.class), "%" + label.getName() + "%"));
                    }
                    if ((label.getType() != null) && (!label.getType().trim().equals(""))) {
                        list.add(cb.like(root.get("type").as(String.class), "%" + label.getType() + "%"));
                    }
                }
                // 逻辑删除条件
                list.add(cb.equal(root.get("isDelete").as(String.class),"0"));
                // 合并查询条件
                Predicate[] predicates = new Predicate[list.size()];
                query.where(cb.and(list.toArray(predicates)));
                return query.getRestriction();
            }
        };
        // 2.获取数据
        Page<Label> page = labelRepository.findAll(specification, pageable);
        if (page.getTotalElements() == 0) {
            return null;
        }
        // 3.封装数据
        PageBean<Label> pageBean = new PageBean<>();
        pageBean.setTotal(page.getTotalElements());
        pageBean.setRows(page.getContent());
        return pageBean;
    }
}

