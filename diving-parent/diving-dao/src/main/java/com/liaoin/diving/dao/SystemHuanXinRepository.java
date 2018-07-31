package com.liaoin.diving.dao;

import com.liaoin.diving.entity.SystemHuanXin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 环信系统参数JPA
 *
 * @author 张权立
 * @date 2018/6/19 15:36
 */
public interface SystemHuanXinRepository extends JpaRepository<SystemHuanXin, Integer>, JpaSpecificationExecutor<SystemHuanXin> {

}
