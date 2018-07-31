package com.liaoin.diving.service;

import com.liaoin.diving.entity.relationship.UserLike;
import com.liaoin.diving.view.LikeView;

import java.util.List;

/**
 * @author : huqingxi
 * @describe :
 * @date 2018/06/07
 */
public interface UserLikeService {
    void insert(UserLike userLike);

    UserLike findByContentAndUser(Integer content, Integer user);

    void cancelLike(Integer id);


    /**
     * app端获取点赞列表
     * @param id
     * @param start
     * @param pageSize
     * @return
     */
    List<LikeView> findLikeViewList(Integer id, Integer start, Integer pageSize);
}
