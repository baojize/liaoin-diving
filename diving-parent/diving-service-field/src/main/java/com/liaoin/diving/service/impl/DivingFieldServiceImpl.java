package com.liaoin.diving.service.impl;

import com.liaoin.diving.common.PageBean;
import com.liaoin.diving.dao.DivingFieldRepository;
import com.liaoin.diving.entity.DivingField;
import com.liaoin.diving.entity.DivingTicket;
import com.liaoin.diving.service.DivingFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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
public class DivingFieldServiceImpl implements DivingFieldService {

    @Autowired
    public DivingFieldRepository divingFieldRepository;

    @Override
    public void insert(DivingField divingField) {
        divingField.setIsDelete("0");
        divingField.setCreateTime(new Date());

        List<DivingTicket> divingTickets = divingField.getDivingTicketList();
        //级联操作
        for (DivingTicket divingTicket : divingTickets){
            divingTicket.setCreateTime(new Date());
            divingTicket.setIsDelete("0");
            divingTicket.setDivingField(divingField);
        }
        divingFieldRepository.save(divingField);
    }

    @Override
    public void update(DivingField divingField) {
        divingField.setUpdateTime(new Date());
        divingFieldRepository.save(divingField);
    }

    @Override
    public void delete(Integer[] ids) {
        List<DivingField> divingFields = new ArrayList<>();
        for (Integer id : ids){
            DivingField divingField = divingFieldRepository.findOne(id);
            if (divingField != null){
                divingField.setIsDelete("1");
                divingFields.add(divingField);
            }
        }
        divingFieldRepository.save(divingFields);
    }

    @Override
    public DivingField findOne(Integer id) {
        return divingFieldRepository.findOne(id);
    }

    /**
     * 分页查询
     * @param pageable
     * @param divingField
     * @return
     */
    @Override
    public PageBean<DivingField> pageQuery(Pageable pageable, DivingField divingField) {

        //封装查询条件
        Specification<DivingField> specification = new Specification<DivingField>() {
            /**
             *
             * @param root 返回查询的结果
             * @param query 查询
             * @param cb 条件
             * @return
             */
            @Override
            public Predicate toPredicate(Root<DivingField> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                // 普通条件
                List<Predicate> list = new ArrayList<>();

                if (divingField != null){

                    if (divingField.getId() != null){
                        list.add(cb.equal(root.get("id").as(Integer.class),divingField.getId()));
                    }
                    if (divingField.getName() != null){
                        list.add(cb.equal(root.get("name").as(String.class),divingField.getName()));
                    }


                }
                //删除的不显示
                list.add(cb.notEqual(root.get("isDelete").as(String.class),"0"));
//                合并查询条件
                Predicate [] predicates = new Predicate[list.size()];
                query.where(cb.and(list.toArray(predicates)));
                return query.getRestriction();
            }
        };
        return null;
    }
}
