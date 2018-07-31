package com.liaoin.diving.dao;

import com.liaoin.diving.entity.ContentGroup;
import com.liaoin.diving.entity.GroupFollow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface GroupFollowRepository extends JpaRepository<GroupFollow, Integer>, JpaSpecificationExecutor<GroupFollow> {

    GroupFollow findByGroupIdAndAndUserId(Integer groupId,Integer userId);
}
