package com.liaoin.diving.dao;

import com.liaoin.diving.entity.BigCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BigCategoryRepository extends JpaRepository<BigCategory, Integer>, JpaSpecificationExecutor<BigCategory> {

}