package com.liaoin.diving.service.impl;

import com.liaoin.diving.common.PageBean;
import com.liaoin.diving.dao.DivingTicketRepository;
import com.liaoin.diving.entity.DivingTicket;
import com.liaoin.diving.service.DivingTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @作者：tt
 * @描述：
 * @日期：2018/6/29 10:37
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class DivingTicketServiceImpl implements DivingTicketService {
    @Autowired
    public DivingTicketRepository divingTicketRepository;
    @Override
    public void insert(DivingTicket divingTicket) {
        //默认不删除
        divingTicket.setIsDelete("0");
        divingTicket.setCreateTime(new Date());
        divingTicketRepository.save(divingTicket);
    }

    @Override
    public void update(DivingTicket divingTicket) {
        divingTicket.setUpdateTime(new Date());
        divingTicketRepository.save(divingTicket);
    }

    @Override
    public void delete(Integer[] ids) {
        List<DivingTicket> divingTickets = new ArrayList<>();
        for (Integer id : ids){
            DivingTicket divingTicket = divingTicketRepository.findOne(id);
            if (divingTicket != null) {
                divingTicket.setIsDelete("1");
                divingTickets.add(divingTicket);
            }

        }
        divingTicketRepository.save(divingTickets);

    }

    @Override
    public DivingTicket findOne(Integer id) {
        return divingTicketRepository.findOne(id);
    }

    @Override
    public PageBean<DivingTicket> pageQuery(Pageable pageable, DivingTicket divingTicket) {
        // 1.查询条件
        Specification<DivingTicket> specification = new Specification<DivingTicket>() {
            @Override
            public Predicate toPredicate(Root<DivingTicket> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // 普通条件
                List<Predicate> list = new ArrayList<>();
                if (divingTicket != null) {
                    if (divingTicket.getId() != null) {
                        list.add(cb.equal(root.get("id").as(Integer.class), divingTicket.getId()));
                    }
                    if ((divingTicket.getBuyTime() != null) && (!divingTicket.getBuyTime().trim().equals(""))) {
                        list.add(cb.like(root.get("buyTime").as(String.class), "%" + divingTicket.getBuyTime() + "%"));
                    }
                    if ((divingTicket.getGoDiving() != null) && (!divingTicket.getGoDiving().trim().equals(""))) {
                        list.add(cb.like(root.get("goDiving").as(String.class), "%" + divingTicket.getGoDiving() + "%"));
                    }
                    if ((divingTicket.getIncluded() != null) && (!divingTicket.getIncluded().trim().equals(""))) {
                        list.add(cb.like(root.get("included").as(String.class), "%" + divingTicket.getIncluded() + "%"));
                    }
                    if ((divingTicket.getName() != null) && (!divingTicket.getName().trim().equals(""))) {
                        list.add(cb.like(root.get("name").as(String.class), "%" + divingTicket.getName() + "%"));
                    }
                    if ((divingTicket.getNotIncluded() != null) && (!divingTicket.getNotIncluded().trim().equals(""))) {
                        list.add(cb.like(root.get("noIncluded").as(String.class), "%" + divingTicket.getNotIncluded() + "%"));
                    }
                    if ((divingTicket.getNotIncluded() != null) && (!divingTicket.getNotIncluded().trim().equals(""))) {
                        list.add(cb.like(root.get("notIncluded").as(String.class), "%" + divingTicket.getNotIncluded() + "%"));
                    }
                    if ((divingTicket.getTicket() != null) && (!divingTicket.getTicket().trim().equals(""))) {
                        list.add(cb.like(root.get("ticket").as(String.class), "%" + divingTicket.getTicket() + "%"));
                    }
                    if ((divingTicket.getTips() != null) && (!divingTicket.getTips().trim().equals(""))) {
                        list.add(cb.like(root.get("tips").as(String.class), "%" + divingTicket.getTips() + "%"));
                    }
                    if ((divingTicket.getType() != null) && (!divingTicket.getType().trim().equals(""))) {
                        list.add(cb.like(root.get("type").as(String.class), "%" + divingTicket.getType() + "%"));
                    }
                    if ((divingTicket.getUsingRule() != null) && (!divingTicket.getUsingRule().trim().equals(""))) {
                        list.add(cb.like(root.get("usingRule").as(String.class), "%" + divingTicket.getUsingRule() + "%"));
                    }
                    if ((divingTicket.getUsingTime() != null) && (!divingTicket.getUsingTime().trim().equals(""))) {
                        list.add(cb.like(root.get("usingTime").as(String.class), "%" + divingTicket.getUsingTime() + "%"));
                    }
                    if (divingTicket.getDiscount() != null) {
                        list.add(cb.equal(root.get("discount").as(BigDecimal.class), divingTicket.getDiscount()));
                    }
                    if (divingTicket.getPrice() != null) {
                        list.add(cb.equal(root.get("price").as(BigDecimal.class), divingTicket.getPrice()));
                    }
                    if (divingTicket.getCreateTime() != null) {
                        list.add(cb.equal(root.get("createTime").as(java.util.Date.class), divingTicket.getCreateTime()));
                    }
                    if (divingTicket.getUpdateTime() != null) {
                        list.add(cb.equal(root.get("updateTime").as(java.util.Date.class), divingTicket.getUpdateTime()));
                    }
                }
                // 逻辑删除条件
                list.add(cb.notEqual(root.get("isDelete").as(String.class),"0"));
                // 合并查询条件
                Predicate[] predicates = new Predicate[list.size()];
                query.where(cb.and(list.toArray(predicates)));
                return query.getRestriction();
            }
        };
        // 2.获取数据
        Page<DivingTicket> page = divingTicketRepository.findAll(specification, pageable);
        if (page.getTotalElements() == 0) {
            return null;
        }
        // 3.封装数据
        PageBean<DivingTicket> pageBean = new PageBean<>();
        pageBean.setTotal(page.getTotalElements());
        pageBean.setRows(page.getContent());
        return pageBean;
    }
}
