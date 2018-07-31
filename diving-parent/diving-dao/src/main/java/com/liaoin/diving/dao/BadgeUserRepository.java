package com.liaoin.diving.dao;

import com.liaoin.diving.entity.BadgeUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BadgeUserRepository extends JpaRepository<BadgeUser,Integer>,JpaSpecificationExecutor<Integer> {
}
