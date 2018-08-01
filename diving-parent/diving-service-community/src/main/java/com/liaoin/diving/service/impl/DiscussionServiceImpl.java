package com.liaoin.diving.service.impl;

import com.github.pagehelper.PageHelper;
import com.liaoin.diving.common.PageBean;
import com.liaoin.diving.common.PageHelp;
import com.liaoin.diving.dao.DiscussionRepository;
import com.liaoin.diving.entity.Content;
import com.liaoin.diving.entity.Discussion;
import com.liaoin.diving.entity.User;
import com.liaoin.diving.mapper.DiscussionMapper;
import com.liaoin.diving.service.DiscussionService;
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
import java.util.*;

@Service
@Transactional(rollbackFor = Exception.class)
public class DiscussionServiceImpl implements DiscussionService {

    @Resource
    private DiscussionRepository discussionRepository;
    @Resource
    private DiscussionMapper discussionMapper;

    @Override
    public void insert(Discussion discussion) {
        discussion.setLiking(0);
        discussion.setCreateTime(new Date());
        discussionRepository.save(discussion);
    }

    @Override
    public void update(Discussion discussion) {
        discussionRepository.save(discussion);
    }

    @Override
    public void delete(Integer[] ids) {
        List<Discussion> discussionList = new ArrayList<>();
        for (Integer id : ids) {
            Discussion discussion = discussionRepository.findOne(id);
            if (discussion == null) {
                continue;
            }
            discussion.setIsDelete("1");
            discussionList.add(discussion);
        }
        discussionRepository.save(discussionList);
    }

    @Override
    public Discussion findOne(Integer id) {
        return discussionRepository.findOne(id);
    }

    @Override
    public PageBean<Discussion> pageQuery(Pageable pageable, Discussion discussion) {
        // 1.查询条件
        Specification<Discussion> specification = new Specification<Discussion>() {
            @Override
            public Predicate toPredicate(Root<Discussion> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // 普通条件
                List<Predicate> list = new ArrayList<>();
                if (discussion != null) {
                    if (discussion.getId() != null) {
                        list.add(cb.equal(root.get("id").as(Integer.class), discussion.getId()));
                    }
                    if (discussion.getCreateTime() != null) {
                        list.add(cb.equal(root.get("createTime").as(Date.class), discussion.getCreateTime()));
                    }
                    if (discussion.getLiking() != null) {
                        list.add(cb.equal(root.get("liking").as(Integer.class), discussion.getLiking()));
                    }
                    if ((discussion.getText() != null) && (!discussion.getText().trim().equals(""))) {
                        list.add(cb.like(root.get("text").as(String.class), "%" + discussion.getText() + "%"));
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
        Page<Discussion> page = discussionRepository.findAll(specification, pageable);
        if (page.getTotalElements() == 0) {
            return null;
        }
        // 3.封装数据
        PageBean<Discussion> pageBean = new PageBean<>();
        pageBean.setTotal(page.getTotalElements());
        pageBean.setRows(page.getContent());
        return pageBean;
    }

    @Override
    public List<Discussion> findNewNotice(Integer id) {
        return discussionRepository.findNewNotice(id);
    }

    @Override
    public List<Integer> findCurrentUpContentIds(Integer id) {
        return discussionRepository.findCurrentUpContentIds(id);
    }

    @Override
    public List<Discussion> findDisForContentId(PageHelp pageHelp, Integer contentId) {
        PageHelper.startPage(pageHelp.getStart(), pageHelp.getPageSize());
        List<Discussion>  discussions = discussionMapper.findDisForContentId(contentId);
        return discussions;
    }

    @Override
    public void like(Map<String, Object> map) {
        Discussion discussion = discussionMapper.findDisForDisIdAndUserId(map);
        if (Objects.isNull(discussion)){
            discussionMapper.like(map); // 点赞

            User loginUser = (User) map.get("loginUser");
            Integer agreeNum = loginUser.getAgreeNum(); //通知
            if (Objects.isNull(agreeNum)){
                   agreeNum = 0;
            }
            //loginUser.setAgreeNum(agreeNum + 1);
            discussionMapper.updateAgreeNum(agreeNum + 1 ,loginUser.getId());

        }else {
            discussionMapper.notLike(discussion.getId()); // 取消
        }

    }

}

