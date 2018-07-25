package com.liaoin.diving.service.impl;

import com.liaoin.diving.dao.UserRepository;
import com.liaoin.diving.entity.User;
import com.liaoin.diving.service.FocusService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@Service
@Transactional(rollbackFor = Exception.class)
public class FocusServiceImpl implements FocusService {

    @Resource
    private UserRepository userRepository;

    @Override
    public void focus(User loginUser, User focusUser) {
        // 1.对于当前登录用户，添加关注到关注列表
        List<User> focusList = loginUser.getFocusList();
        if (focusList.contains(focusUser)) {  //  关注列表中是否包含 关注用户 ,  包含则移除, 不包含则添加
            focusList.remove(focusUser);
        } else {
            focusList.add(focusUser);
        }
        userRepository.save(loginUser);
        // 2.对于被关注的用户，添加粉丝到粉丝列表
        List<User> followList = focusUser.getFollowList();
        if (followList.contains(loginUser)) {
            followList.remove(loginUser);
        } else {
            Integer followNum = focusUser.getFollowNum();
            if (Objects.isNull(followNum)){
                followNum = 0;
            }
            focusUser.setFollowNum(followNum + 1);  // 通知
            followList.add(loginUser);

        }
        userRepository.save(focusUser);
    }

    @Override
    public List<Integer> findFansIds(Integer userId) {
        return userRepository.findFansIds(userId);
    }
}
