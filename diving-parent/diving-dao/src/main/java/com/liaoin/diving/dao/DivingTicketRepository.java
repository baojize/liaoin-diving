package com.liaoin.diving.dao;

import com.liaoin.diving.entity.DivingTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @作者：tt
 * @描述：
 * @日期：2018/6/29 9:36
 * 浅水场门票
 */
public interface DivingTicketRepository extends JpaRepository<DivingTicket,Integer>,JpaSpecificationExecutor<DivingTicket> {
}
