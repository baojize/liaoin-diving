package com.liaoin.diving.dao;

import com.liaoin.diving.entity.relationship.UserLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author : huqingxi
 * @describe :
 * @date 2018/06/07
 */
public interface UserLikeRepository extends JpaRepository<UserLike, Integer>, JpaSpecificationExecutor<UserLike> {

    UserLike findByContentAndUser(Integer content, Integer user);
}
