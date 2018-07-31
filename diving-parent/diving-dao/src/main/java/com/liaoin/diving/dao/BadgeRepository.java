package com.liaoin.diving.dao;

import com.liaoin.diving.entity.Badge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BadgeRepository extends JpaRepository<Badge,Integer>,JpaSpecificationExecutor<Integer> {
}
