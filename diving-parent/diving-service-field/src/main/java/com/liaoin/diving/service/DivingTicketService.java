package com.liaoin.diving.service;

import com.liaoin.diving.common.PageBean;
import com.liaoin.diving.entity.DivingField;
import com.liaoin.diving.entity.DivingTicket;
import org.springframework.data.domain.Pageable;

/**
 * @作者：tt
 * @描述：
 * @日期：2018/6/29 10:34
 */
public interface DivingTicketService {
    void insert(DivingTicket divingTicket);

    void update(DivingTicket divingTicket);

    void delete(Integer[] ids);

    DivingTicket findOne(Integer id);

    PageBean<DivingTicket> pageQuery(Pageable pageable, DivingTicket divingTicket);
}
