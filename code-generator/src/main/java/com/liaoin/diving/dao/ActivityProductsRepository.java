package com.liaoin.diving.dao;

import com.liaoin.diving.entity.ActivityProducts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ActivityProductsRepository extends JpaRepository<ActivityProducts, Integer>, JpaSpecificationExecutor<ActivityProducts> {

}