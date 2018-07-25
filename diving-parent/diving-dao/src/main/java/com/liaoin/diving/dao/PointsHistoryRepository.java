package com.liaoin.diving.dao;

import com.liaoin.diving.entity.PointsHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PointsHistoryRepository extends JpaRepository<PointsHistory, Integer>, JpaSpecificationExecutor<PointsHistory> {

}