package com.liaoin.diving.dao;

import com.liaoin.diving.entity.CommodityOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CommodityOrderRepository extends JpaRepository<CommodityOrder,Integer>,JpaSpecificationExecutor<CommodityOrder> {
}
