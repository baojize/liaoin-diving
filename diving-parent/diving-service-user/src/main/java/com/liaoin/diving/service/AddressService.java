package com.liaoin.diving.service;

import com.liaoin.diving.entity.Address;

import java.util.List;

/**
 * @author : huqingxi
 * @describe :
 * @date 2018/06/07
 */
public interface AddressService {
    /**
     *
     * @param address
     */
    void insert(Address address);

    /**
     * 将登录用户的所有地址的is_default 设置为0
     * @param id
     */
    void setDefaultAddrZero(Integer id);

    /**
     * 获取当前登录用户的收货地址
     * @return
     */
    List<Address> findCurrenAddr(Integer id);

    void  deleteAddr(Integer id);

    Address findOne(Integer id);

    Address save(Address address);
}
