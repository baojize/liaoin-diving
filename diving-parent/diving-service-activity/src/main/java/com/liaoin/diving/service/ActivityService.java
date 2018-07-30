package com.liaoin.diving.service;

import com.liaoin.diving.common.PageBean;
import com.liaoin.diving.common.PageHelp;
import com.liaoin.diving.entity.Activity;
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
}

