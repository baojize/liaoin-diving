package com.liaoin.diving.dao;

import com.liaoin.diving.entity.ContentGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ContentGroupRepository extends JpaRepository<ContentGroup, Integer>, JpaSpecificationExecutor<ContentGroup> {
}
