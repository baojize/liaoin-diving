package com.liaoin.diving.service;

import com.liaoin.diving.common.PageBean;
import com.liaoin.diving.entity.DivingField;
import org.springframework.data.domain.Pageable;

/**
 * @作者：tt
 * @描述：
 * @日期：2018/6/29 10:33
 */
public interface DivingFieldService {
    void insert(DivingField divingField);

    void update(DivingField divingField);

    void delete(Integer[] ids);

    DivingField findOne(Integer id);

    PageBean<DivingField> pageQuery(Pageable pageable, DivingField divingField);
}
