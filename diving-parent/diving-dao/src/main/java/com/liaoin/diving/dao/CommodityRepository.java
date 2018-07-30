package com.liaoin.diving.dao;

import com.liaoin.diving.entity.Commodity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CommodityRepository extends JpaRepository<Commodity,Integer>,JpaSpecificationExecutor<Commodity> {
}
