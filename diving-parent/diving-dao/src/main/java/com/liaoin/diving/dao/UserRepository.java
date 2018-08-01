package com.liaoin.diving.dao;

import com.liaoin.diving.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
    User findByMobile(String mobile);

    User findByNickname(String nickname);

    Page<User> findAllByIsDelete(String isDelete, Pageable pageable);

    @Query(value = "select count(1) from t_content  c join m_content_user m  on c.id = m.content_id  where m.content_id = ?1 and c.is_delete =  0", nativeQuery = true)
    Integer findLikeCount(Integer id);


    @Query(value = "select * from t_user where id = ?1 and is_delete = 0", nativeQuery = true)
    User findById(Integer id);

    @Query(value = "select follow_id from m_user_follow where user_id = ?1", nativeQuery = true)
    List<Integer> findFansIds(Integer userId);


}