package com.liaoin.diving.service.impl;

import com.liaoin.diving.dao.AddressRepository;
import com.liaoin.diving.entity.Address;
import com.liaoin.diving.mapper.AddressMapper;
import com.liaoin.diving.service.AddressService;
import io.swagger.models.auth.In;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author : huqingxi
 * @describe :
 * @date 2018/06/07
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AddressServiceImpl implements AddressService {

    @Resource
    private AddressRepository addressRepository;
    @Resource
    private AddressMapper addressMapper;

    @Override
    public void insert(Address address) {
        addressRepository.save(address);
    }

    @Override
    public void setDefaultAddrZero(Integer id) {
        addressRepository.setDefaultAddrZero(id);
    }

    @Override
    public List<Address> findCurrenAddr(Integer id) {
        return addressMapper.findCurrenAddr(id);
    }

    @Override
    public void deleteAddr(Integer id) {
        addressMapper.deleteAddr(id);
    }


    @Override
    public Address findOne(Integer id) {
        return addressRepository.findOne(id);
    }

    @Override
    public Address save(Address address) {
        return addressRepository.save(address);
    }
}
