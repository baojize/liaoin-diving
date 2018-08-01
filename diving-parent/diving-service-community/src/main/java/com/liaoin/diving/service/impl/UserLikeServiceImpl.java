package com.liaoin.diving.service.impl;

import com.github.pagehelper.PageHelper;
import com.liaoin.diving.dao.UserLikeRepository;
import com.liaoin.diving.entity.User;
import com.liaoin.diving.entity.relationship.UserLike;
import com.liaoin.diving.mapper.UserLikeMapper;
import com.liaoin.diving.service.UserLikeService;
import com.liaoin.diving.view.LikeView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author : huqingxi
 * @describe :
 * @date 2018/06/07
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserLikeServiceImpl implements UserLikeService {

    @Resource
    private UserLikeRepository userLikeRepository;
    @Resource
    private UserLikeMapper userLikeMapper;

    @Override
    public void insert(UserLike userLike) {
        userLike.setCreateTime(new Date());
        userLikeRepository.save(userLike);
    }

    @Override
    public UserLike findByContentAndUser(Integer content, Integer user) {
        return userLikeRepository.findByContentAndUser(content, user);
    }

    @Override
    public void cancelLike(Integer id) {
        userLikeRepository.delete(id);
    }

    @Override
    public List<LikeView> findLikeViewList(Integer id,Integer start,Integer pageSize) {
        PageHelper.startPage(start,pageSize);
        List<LikeView> likeViewList = userLikeMapper.findLikeViewList(id);

        return likeViewList;
    }

}
