package com.liaoin.diving.mapper;

import com.liaoin.diving.entity.Activity;
import com.liaoin.diving.view.ActivityConditionView;
import com.liaoin.diving.view.RecoAcView;

import java.util.List;

/**
 * @author : huqingxi
 * @describe :
 * @date 2018/06/07
 */
public interface ActivityMapper {
    List<RecoAcView> findAll();

    List<Activity> condition(ActivityConditionView activity);

    void cancelReco(Integer id);

    Activity findById(Integer id);

    List<Activity> getAcHome();

}
