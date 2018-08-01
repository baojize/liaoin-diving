package com.liaoin.diving.dao;

import com.liaoin.diving.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @作者：tt
 * @描述：
 * @日期：2018/6/29 9:38
 * 潜水场点评
 */
public interface CommentRepository extends JpaRepository<Comment,Integer>,JpaSpecificationExecutor<Comment> {
}
