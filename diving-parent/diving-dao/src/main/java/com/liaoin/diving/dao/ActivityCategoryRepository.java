package com.liaoin.diving.dao;

import com.liaoin.diving.entity.ActivityCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ActivityCategoryRepository extends JpaRepository<ActivityCategory, Integer>, JpaSpecificationExecutor<ActivityCategory> {

}