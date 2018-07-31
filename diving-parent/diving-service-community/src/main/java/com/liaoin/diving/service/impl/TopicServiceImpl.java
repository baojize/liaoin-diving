package com.liaoin.diving.service.impl;

import com.liaoin.diving.common.PageBean;
import com.liaoin.diving.dao.TopicRepository;
import com.liaoin.diving.entity.Topic;
import com.liaoin.diving.mapper.TopicMapper;
import com.liaoin.diving.service.TopicService;
import com.liaoin.diving.utils.UpdateUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 *
 *
 * @author huqingxi
 * @date 2018/06/07
 */
@Service
public class TopicServiceImpl implements TopicService{

    @Resource
    private TopicRepository topicRepository;
    @Resource
    private TopicMapper topicMapper;
    @Override
    public void insert(Topic topic) {
        topic.setIsDelete("0");
        topic.setIsColumn("0");
        topic.setCreateTime(new Date());
        topicRepository.save(topic);
    }

    @Override
    public void update(Topic topic) {
        Topic dbTopic = topicMapper.findById(topic.getId());
        if (Objects.isNull(dbTopic)){
            return ;
        }
        UpdateUtils.copyNonNullProperties(topic, dbTopic);
        topicRepository.save(dbTopic);
    }

    @Override
    public void delete(Integer[] ids) {
        List<Topic> topicList = new ArrayList<>();
        for(Integer id : ids){
            Topic topic = topicRepository.findOne(id);
            if (Objects.isNull(topic)){
                continue;
            }
            topic.setIsDelete("1");
            topicList.add(topic);
        }
        topicRepository.save(topicList);
    }

    @Override
    public Topic findOne(Integer id) {
        return topicRepository.findOne(id);
    }

    @Override
    public PageBean<Topic> pageQuery(Pageable pageable, Topic topic) {
        Specification specification = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                // 1. 普通条件
                List<Predicate> predicateList = new ArrayList<>();
                if (!Objects.isNull(topic)){
                    if (!Objects.isNull(topic.getId()) /*&&  !"".equals(topic.getId())*/){
                        predicateList.add(cb.equal(root.get("id").as(Integer.class), topic.getId()));
                    }
                    if (!Objects.isNull(topic.getCreateTime())){
                        predicateList.add(cb.equal(root.get("createTime").as(Date.class), topic.getCreateTime()));
                    }
                    if (!Objects.isNull(topic.getName())){
                        predicateList.add(cb.equal(root.get("name").as(String.class), topic.getName()));
                    }
                }
                // 逻辑删除条件
                predicateList.add(cb.equal(root.get("isDelete").as(String.class), "0"));
                // 合并查询条件
                Predicate[] predicates = new Predicate[predicateList.size()];
                query.where(cb.and(predicateList.toArray(predicates)));
                return query.getRestriction();
            }
        };
        // 2. 获取数据
        Page<Topic> page = topicRepository.findAll(specification, pageable);
        if(page.getTotalElements() == 0 ){
            return null;
        }
        // 3. 封装数据
        PageBean<Topic> pageBean = new PageBean<>();
        pageBean.setTotal(page.getTotalElements());
        pageBean.setRows(page.getContent());
        return pageBean;
    }

    @Override
    public void setColumn(Integer[] ids) {
        List<Topic> topicList = new ArrayList<>();
        for(Integer id: ids){
            Topic topic = topicRepository.findOne(id);
            if(Objects.isNull(topic)){
                continue;
            }
            topic.setIsColumn("1");
            topicList.add(topic);
        }
        topicRepository.save(topicList);
    }

    @Override
    public List<Topic> findByColunm() {
        return topicRepository.findByIsColumnAndIsDelete("1", "0");
    }


    @Override
    public List<Topic> findByType(String type) {
        return null;
    }

    @Override
    public List<Topic> findByDry() {
        return null;
    }

    @Override
    public List<Topic> findByFeuill() {
        return null;
    }

    @Override
    public void updateDry(Integer id) {

    }

    @Override
    public void updateFeuill(Integer id) {

    }

    @Override
    public void setBroadcast(Integer id) {

    }


}
