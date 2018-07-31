package com.liaoin.diving.dao;

import com.liaoin.diving.entity.Level;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LevelRepository extends JpaRepository<Level, Integer>, JpaSpecificationExecutor<Level> {

}