package com.liaoin.diving.service.impl;

import com.liaoin.diving.common.PageBean;
import com.liaoin.diving.dao.CommentRepository;
import com.liaoin.diving.entity.Comment;
import com.liaoin.diving.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @作者：tt
 * @描述：
 * @日期：2018/6/29 10:36
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CommentServiceImpl implements CommentService {
    @Autowired
    public CommentRepository commentRepository;


    @Override
    public void insert(Comment comment) {
        comment.setCreateTime(new Date());
        //默认不删除
        comment.setIsDelete("0");
        commentRepository.save(comment);
    }

    @Override
    public void update(Comment comment) {

        commentRepository.save(comment);
    }

    //批量删除
    @Override
    public void delete(Integer[] ids) {
        List<Comment> comments = new ArrayList<>();
        for (Integer id : ids){
            Comment comment = commentRepository.findOne(id);
            if (comment != null){
                comment.setIsDelete("1");
                comments.add(comment);
            }
        }
        commentRepository.save(comments);
    }

    @Override
    public Comment findOne(Integer id) {
        return commentRepository.findOne(id);
    }

    @Override
    public PageBean<Comment> pageQuery(Pageable pageable, Comment comment) {

        return null;
    }
}
