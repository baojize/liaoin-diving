package com.liaoin.diving.service;

import com.liaoin.diving.common.PageBean;
import com.liaoin.diving.common.PageHelp;
import com.liaoin.diving.entity.Activity;
import com.liaoin.diving.view.ActivityConditionView;
import com.liaoin.diving.view.RecoAcView;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ActivityService {
    void insert(Activity activity);

    void update(Activity activity);

    void delete(Integer[] ids);

    Activity findOne(Integer id);

    PageBean<Activity> pageQuery(Pageable pageable, Activity activity);

    boolean delProducts(Integer[] ids);

    List<Activity> recommend(Integer type);

    List<Activity> findByName(String name);


    void setBroadcast(Integer id);

    List<Activity> findRecommend();


    /**
     * 通过分类查询
     * @param pageHelp
     * @param id
     * @return
     */
    List<Activity> findByCategory(PageHelp pageHelp, Integer id);

    /**
     * 查询所有推荐
     * @param pageHelp
     * @return
     */
    List<RecoAcView> getReco(PageHelp pageHelp);

    /**
     * 查询一条推荐活动
     * @param id
     * @return
     */
    RecoAcView getOneReco(Integer id);

    /**
     * 设置指定id活动为推荐活动
     * @param id
     */
    Integer setAc(Integer id);

    /**
     * 查询所有记录
     * @param pageHelp
     * @return
     */
    List<RecoAcView> findAll(PageHelp pageHelp);

    /**
     * 条件查询
     * @param pageHelp
     * @param activity
     * @return
     */
    List<Activity> condition(PageHelp pageHelp, ActivityConditionView activity);

    /**
     * 取消活动推荐
     * @param id
     */
    void cancelReco(Integer id);

    /**
     * 查询一条记录
     * @param id
     * @return
     */
    Activity findById(Integer id);

    /**
     * 查询首页活动
     * @param pageHelp
     * @return
     */
    List<Activity> getAcHome(PageHelp pageHelp);

    void deleteById(Integer id);
}

