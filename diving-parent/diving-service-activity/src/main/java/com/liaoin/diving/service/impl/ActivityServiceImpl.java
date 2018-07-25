package com.liaoin.diving.service.impl;

import com.github.pagehelper.PageHelper;
import com.liaoin.diving.common.PageBean;
import com.liaoin.diving.common.PageHelp;
import com.liaoin.diving.dao.ActivityProductsRepository;
import com.liaoin.diving.dao.ActivityRepository;
import com.liaoin.diving.dao.BroadcastRepository;
import com.liaoin.diving.entity.Activity;
import com.liaoin.diving.entity.ActivityLabel;
import com.liaoin.diving.entity.ActivityProducts;
import com.liaoin.diving.entity.Broadcast;
import com.liaoin.diving.mapper.ActivityLabelMapper;
import com.liaoin.diving.service.ActivityService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class ActivityServiceImpl implements ActivityService {

    @Resource
    private ActivityRepository activityRepository;

    @Resource
    private ActivityProductsRepository activityProductsRepository;
    @Resource
    private BroadcastRepository broadcastRepository;
    @Resource
    private ActivityLabelMapper activityLabelMapper;

    @Override
    public void insert(Activity activity) {
        activityRepository.save(activity);
    }

    @Override
    public void update(Activity activity) {
        activityRepository.save(activity);
    }

    @Override
    public void delete(Integer[] ids) {
        List<Activity> activityList = new ArrayList<>();
        for (Integer id : ids) {
            Activity activity = activityRepository.findOne(id);
            if (activity == null) {
                continue;
            }
            activity.setIsDelete("1");
            activityList.add(activity);
        }
        activityRepository.save(activityList);
    }

    @Override
    public Activity findOne(Integer id) {
        Activity activity = activityRepository.findOne(id);
        if (Objects.isNull(activity)){
            return null;
        }
        if (!"0".equals(activity.getIsDelete())){
            return null;
        }
        List<ActivityLabel> labels = activityLabelMapper.findByActivityId(id);
        activity.setActivityLabels(labels);
        return  activity;
    }

    @Override
    public List<Activity> findByCategory(PageHelp pageHelp, Integer id) {
        PageHelper.startPage(pageHelp.getStart(), pageHelp.getPageSize());
        List<Activity> activities = activityLabelMapper.findByCategory(id);
        for (Activity activity : activities){
            List<ActivityLabel> activityLabels = activityLabelMapper.findByActivityId(activity.getId());
            activity.setActivityLabels(activityLabels);
        }
        return activities;
    }

    @Override
    public PageBean<Activity> pageQuery(Pageable pageable, Activity activity) {
        // 1.查询条件
        Specification<Activity> specification = new Specification<Activity>() {
            @Override
            public Predicate toPredicate(Root<Activity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // 普通条件
                List<Predicate> list = new ArrayList<>();
                if (activity != null) {
                    if (activity.getId() != null) {
                        list.add(cb.equal(root.get("id").as(Integer.class), activity.getId()));
                    }
                    if ((activity.getAddress() != null) && (!activity.getAddress().trim().equals(""))) {
                        list.add(cb.like(root.get("address").as(String.class), "%" + activity.getAddress() + "%"));
                    }
                    if (activity.getBeginTime() != null) {
                        list.add(cb.equal(root.get("beginTime").as(java.util.Date.class), activity.getBeginTime()));
                    }
                    /*if ((activity.getCrowd() != null) && (!activity.getCrowd().trim().equals(""))) {
                        list.add(cb.like(root.get("crowd").as(String.class), "%" + activity.getCrowd() + "%"));
                    }*/
                    if (activity.getEndTime() != null) {
                        list.add(cb.equal(root.get("endTime").as(java.util.Date.class), activity.getEndTime()));
                    }
                    if (((activity.getExplain()) != null) && (!activity.getExplain().trim().equals(""))) {
                        list.add(cb.like(root.get("costExplain").as(String.class), "%" + activity.getExplain() + "%"));
                    }
                    if ((activity.getHighlight() != null) && (!activity.getHighlight().trim().equals(""))) {
                        list.add(cb.like(root.get("highlight").as(String.class), "%" + activity.getHighlight() + "%"));
                    }
                    if ((activity.getImgExplain() != null) && (!activity.getImgExplain().trim().equals(""))) {
                        list.add(cb.like(root.get("imgExplain").as(String.class), "%" + activity.getImgExplain() + "%"));
                    }
                    if ((activity.getIphone() != null) && (!activity.getIphone().trim().equals(""))) {
                        list.add(cb.like(root.get("iphone").as(String.class), "%" + activity.getIphone() + "%"));
                    }
                    if ((activity.getName() != null) && (!activity.getName().trim().equals(""))) {
                        list.add(cb.like(root.get("name").as(String.class), "%" + activity.getName() + "%"));
                    }
                   /* if ((activity.getPlan() != null) && (!activity.getPlan().trim().equals(""))) {
                        list.add(cb.like(root.get("plan").as(String.class), "%" + activity.getPlan() + "%"));
                    }*/
                   if (activity.getTrip() != null && "".equals(activity.getTrip()) ){
                       list.add(cb.like(root.get("trip").as(String.class), "%" + activity.getTrip() + "%"));
                   }
                    if (activity.getDiscribe() != null && "".equals(activity.getDiscribe()) ){
                        list.add(cb.like(root.get("discribe").as(String.class), "%" + activity.getDiscribe() + "%"));
                    }
                    if (activity.getBeDiscribe() != null && "".equals(activity.getBeDiscribe()) ){
                        list.add(cb.like(root.get("beDiscribe").as(String.class), "%" + activity.getBeDiscribe() + "%"));
                    }
                    if (activity.getOther() != null && "".equals(activity.getOther()) ){
                        list.add(cb.like(root.get("other").as(String.class), "%" + activity.getOther() + "%"));
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
        Page<Activity> page = activityRepository.findAll(specification, pageable);
        if (page.getTotalElements() == 0) {
            return null;
        }
        // 3.封装数据
        PageBean<Activity> pageBean = new PageBean<>();
        pageBean.setTotal(page.getTotalElements());
        pageBean.setRows(page.getContent());
        return pageBean;
    }
    @Override
    public boolean delProducts(Integer[] ids){
      List<ActivityProducts> list =  activityProductsRepository.findAllByIdIsIn(ids);
      return false;
    }

    /**
     * 活动推荐首页
     * @param type(1首页 其他非首页)
     * @return
     */
    @Override
    public List<Activity> recommend(Integer type) {
        // 1.查询条件
        Specification<Activity> specification = new Specification<Activity>() {
            @Override
            public Predicate toPredicate(Root<Activity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // 普通条件
                List<Predicate> list = new ArrayList<>();
                list.add(cb.equal(root.get("isRecommend").as(String.class), "1"));
                list.add(cb.equal(root.get("isSale").as(String.class), "1"));
                if (type == 1){
                    list.add(cb.equal(root.get("isHome").as(String.class), "1"));
                }
                // 逻辑删除条件
                list.add(cb.notEqual(root.get("isDelete").as(String.class),"1"));
                // 合并查询条件
                Predicate[] predicates = new Predicate[list.size()];
                query.where(cb.and(list.toArray(predicates)));
                return query.getRestriction();
            }
        };
        Page<Activity> reco = activityRepository.findAll(specification,new PageRequest(0,10,new Sort(Sort.Direction.DESC,"orders")));
        return reco.getContent();
    }

    @Override
    public List<Activity> findByName(String name) {
        activityRepository.findByNameLike("%" + name + "%");
        return null;
    }

    // 设置轮播图()
    @Override
    public void setBroadcast(Integer activitiId) {
        Broadcast broadcast = new Broadcast();
        //broadcast.setUpdateTime(new Date());
        broadcast.setCreateTime(new Date());
        //broadcast.setActivityId(activitiId);
        //broadcastRepository.insertActivity(broadcast.getCreateTime(), broadcast.getUpdateTime(), activitiId);
        activityRepository.setBroadcast(activitiId);
    }

    @Override
    public List<Activity> findRecommend() {
        List<Activity> activities = activityRepository.findRecommend();
        return activities;
    }


}

