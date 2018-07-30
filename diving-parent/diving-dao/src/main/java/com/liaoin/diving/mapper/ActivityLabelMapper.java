package com.liaoin.diving.mapper;

import com.liaoin.diving.entity.Activity;
import com.liaoin.diving.entity.ActivityLabel;
import com.liaoin.diving.view.RecoAcView;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author : huqingxi
 * @describe :
 * @date 2018/06/07
 */
public interface ActivityLabelMapper {
    void add(ActivityLabel activityLabel);
    void update(ActivityLabel activityLabel);

    ActivityLabel findById(Integer id);
    List<ActivityLabel> findAll();
    void del(Integer id);
    List<ActivityLabel> findByActivityId(Integer id);

    List<Activity> findByCategory(@Param("id") Integer id);

    List<RecoAcView> getReco();

    RecoAcView getOneReco(Integer id);
}
