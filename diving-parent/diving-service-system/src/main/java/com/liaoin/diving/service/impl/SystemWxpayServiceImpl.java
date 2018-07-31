package com.liaoin.diving.service.impl;

import com.liaoin.diving.dao.SystemWxpayRepository;
import com.liaoin.diving.entity.SystemWxpay;
import com.liaoin.diving.service.SystemWxpayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 微信支付系统参数服务实现
 *
 * @author 张权立
 * @date 2018/6/19 15:44
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SystemWxpayServiceImpl implements SystemWxpayService {

    private static Logger logger = LoggerFactory.getLogger(SystemWxpayServiceImpl.class);

    @Resource
    private SystemWxpayRepository systemWxpayRepository;

    @Override
    public void update(SystemWxpay systemWxpay) {
        systemWxpayRepository.save(systemWxpay);
        logger.info("修改微信支付系统参数");
    }

    @Override
    public SystemWxpay findOne(int id) {
        return systemWxpayRepository.findOne(id);
    }
}
