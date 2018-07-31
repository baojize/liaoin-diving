package com.liaoin.diving.dao;

import com.liaoin.diving.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author : huqingxi
 * @describe :
 * @date 2018/06/07
 */
public interface AddressRepository extends JpaRepository<Address, Integer>, JpaSpecificationExecutor<Address>{

    @Query(value = "update t_address set is_default = 0 where user_id = ?1", nativeQuery = true)
    @Modifying
    void setDefaultAddrZero(Integer id);
}
