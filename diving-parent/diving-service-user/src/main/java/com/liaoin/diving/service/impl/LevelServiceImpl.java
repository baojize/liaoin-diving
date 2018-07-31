package com.liaoin.diving.service.impl;

import com.liaoin.diving.common.PageBean;
import com.liaoin.diving.dao.LevelRepository;
import com.liaoin.diving.entity.Level;
import com.liaoin.diving.service.LevelService;
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
public class LevelServiceImpl implements LevelService {

    @Resource
    private LevelRepository levelRepository;

    @Override
    public void insert(Level level) {
        level.setIsDelete("0");
        levelRepository.save(level);
    }

    @Override
    public void update(Level level) {
        level.setIsDelete(null);
        levelRepository.save(level);
    }

    @Override
    public void delete(Integer[] ids) {
        List<Level> levelList = new ArrayList<>();
        for (Integer id : ids) {
            Level level = levelRepository.findOne(id);
            if (level == null) {
                continue;
            }
            level.setIsDelete("1");
            levelList.add(level);
        }
        levelRepository.save(levelList);
    }

    @Override
    public Level findOne(Integer id) {
        return levelRepository.findOne(id);
    }

    @Override
    public PageBean<Level> pageQuery(Pageable pageable, Level level) {
        // 1.查询条件
        Specification<Level> specification = new Specification<Level>() {
            @Override
            public Predicate toPredicate(Root<Level> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // 普通条件
                List<Predicate> list = new ArrayList<>();
                if (level != null) {
                    if (level.getId() != null) {
                        list.add(cb.equal(root.get("id").as(Integer.class), level.getId()));
                    }
                    if (level.getMaxPoints() != null) {
                        list.add(cb.equal(root.get("maxPoints").as(Integer.class), level.getMaxPoints()));
                    }
                    if (level.getMinPoints() != null) {
                        list.add(cb.equal(root.get("minPoints").as(Integer.class), level.getMinPoints()));
                    }
                    if ((level.getName() != null) && (!level.getName().trim().equals(""))) {
                        list.add(cb.like(root.get("name").as(String.class), "%" + level.getName() + "%"));
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
        Page<Level> page = levelRepository.findAll(specification, pageable);
        if (page.getTotalElements() == 0) {
            return null;
        }
        // 3.封装数据
        PageBean<Level> pageBean = new PageBean<>();
        pageBean.setTotal(page.getTotalElements());
        pageBean.setRows(page.getContent());
        return pageBean;
    }
}

