package com.liaoin.diving.service;

import com.liaoin.diving.entity.Group;
import com.liaoin.diving.entity.User;

import java.util.List;

public interface FocusService {
    void focus(User loginUser, User focusUser);

    List<Integer> findFansIds(Integer userId);

    void groupFocus(User loginUser, Integer groupId);
}
