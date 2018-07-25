package com.liaoin.diving.service;

import com.liaoin.diving.common.PageBean;
import com.liaoin.diving.entity.Comment;
import org.springframework.data.domain.Pageable;

/**
 * @作者：tt
 * @描述：
 * @日期：2018/6/29 10:34
 */
public interface CommentService {
    void insert(Comment comment);

    void update(Comment comment);

    void delete(Integer[] ids);

    Comment findOne(Integer id);

    PageBean<Comment> pageQuery(Pageable pageable, Comment comment);
}
