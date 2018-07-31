package com.liaoin.diving.mapper;

import com.liaoin.diving.entity.Address;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author : huqingxi
 * @describe :
 * @date 2018/06/07
 */
public interface AddressMapper {
    List<Address> findCurrenAddr(@Param("id") Integer id);

    void deleteAddr(Integer id);


}
