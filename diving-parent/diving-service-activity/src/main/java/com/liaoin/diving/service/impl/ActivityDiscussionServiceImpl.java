package com.liaoin.diving.service.impl;

import com.liaoin.diving.common.PageBean;
import com.liaoin.diving.dao.ActivityDiscussionRepository;
import com.liaoin.diving.entity.ActivityDiscussion;
import com.liaoin.diving.service.ActivityDiscussionService;
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
public class ActivityDiscussionServiceImpl implements ActivityDiscussionService {

    @Resource
    private ActivityDiscussionRepository activityDiscussionRepository;

    @Override
    public void insert(ActivityDiscussion activityDiscussion) {
        activityDiscussionRepository.save(activityDiscussion);
    }

    @Override
    public void update(ActivityDiscussion activityDiscussion) {
        activityDiscussionRepository.save(activityDiscussion);
    }

    @Override
    public void delete(Integer[] ids) {
        List<ActivityDiscussion> activityDiscussionList = new ArrayList<>();
        for (Integer id : ids) {
            ActivityDiscussion activityDiscussion = activityDiscussionRepository.findOne(id);
            if (activityDiscussion == null) {
                continue;
            }
            activityDiscussion.setIsDelete("1");
            activityDiscussionList.add(activityDiscussion);
        }
        activityDiscussionRepository.save(activityDiscussionList);
    }

    @Override
    public ActivityDiscussion findOne(Integer id) {
        return activityDiscussionRepository.findOne(id);
    }

    @Override
    public PageBean<ActivityDiscussion> pageQuery(Pageable pageable, ActivityDiscussion activityDiscussion) {
        // 1.查询条件
        Specification<ActivityDiscussion> specification = new Specification<ActivityDiscussion>() {
            @Override
            public Predicate toPredicate(Root<ActivityDiscussion> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // 普通条件
                List<Predicate> list = new ArrayList<>();
                if (activityDiscussion != null) {
                    if (activityDiscussion.getId() != null) {
                        list.add(cb.equal(root.get("id").as(Integer.class), activityDiscussion.getId()));
                    }
                    if (activityDiscussion.getCreateTime() != null) {
                        list.add(cb.equal(root.get("createTime").as(java.util.Date.class), activityDiscussion.getCreateTime()));
                    }
                    if ((activityDiscussion.getText() != null) && (!activityDiscussion.getText().trim().equals(""))) {
                        list.add(cb.like(root.get("text").as(String.class), "%" + activityDiscussion.getText() + "%"));
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
        Page<ActivityDiscussion> page = activityDiscussionRepository.findAll(specification, pageable);
        if (page.getTotalElements() == 0) {
            return null;
        }
        // 3.封装数据
        PageBean<ActivityDiscussion> pageBean = new PageBean<>();
        pageBean.setTotal(page.getTotalElements());
        pageBean.setRows(page.getContent());
        return pageBean;
    }
}

