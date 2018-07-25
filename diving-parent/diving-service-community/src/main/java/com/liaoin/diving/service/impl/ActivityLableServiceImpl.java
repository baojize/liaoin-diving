package com.liaoin.diving.service.impl;

import com.liaoin.diving.dao.ActivityLabelRepository;
import com.liaoin.diving.entity.ActivityLabel;
import com.liaoin.diving.mapper.ActivityLabelMapper;
import com.liaoin.diving.service.ActivityLableService;
import com.liaoin.diving.utils.UpdateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @author : huqingxi
 * @describe :
 * @date 2018/06/07
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ActivityLableServiceImpl implements ActivityLableService {
    @Resource
    private ActivityLabelMapper activityLabelMapper;
    @Resource
    private ActivityLabelRepository activityLabelRepository;

    @Override
    public ActivityLabel findById(Integer id) {
        return activityLabelMapper.findById(id);
    }

    @Override
    public List<ActivityLabel> findAll() {
        return activityLabelMapper.findAll();
    }

    @Override
    public void add(ActivityLabel activityLabel) {
        activityLabelRepository.save(activityLabel);
    }

    @Override
    public void del(Integer id) {
        activityLabelMapper.del(id);
    }

    @Override
    public void update(ActivityLabel activityLabel) {
        ActivityLabel one = activityLabelMapper.findById(activityLabel.getId());
        //ActivityLabel one = activityLabelRepository.findOne(activityLabel.getId());
        if (Objects.isNull(one)){
            return;
        }
        UpdateUtils.copyNonNullProperties(activityLabel, one);
        activityLabelRepository.save(one);
    }

    @Override
    public List<ActivityLabel> findByActivityId(Integer id) {
        return activityLabelMapper.findByActivityId(id);
    }
}
