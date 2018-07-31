package com.liaoin.diving.dao;

import com.liaoin.diving.entity.ActivityDiscussion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ActivityDiscussionRepository extends JpaRepository<ActivityDiscussion, Integer>, JpaSpecificationExecutor<ActivityDiscussion> {

}