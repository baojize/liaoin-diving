package com.liaoin.diving.dao;

import com.liaoin.diving.entity.DivingField;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @作者：
 * @描述：
 * @日期：2018/6/29 9:33
 * 浅水场
 */
public interface DivingFieldRepository extends JpaRepository<DivingField,Integer>,JpaSpecificationExecutor<DivingField> {
}
