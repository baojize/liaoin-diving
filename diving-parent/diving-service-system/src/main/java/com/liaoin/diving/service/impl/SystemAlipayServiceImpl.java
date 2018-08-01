package com.liaoin.diving.service.impl;

import com.liaoin.diving.dao.SystemAlipayRepository;
import com.liaoin.diving.entity.SystemAlipay;
import com.liaoin.diving.service.SystemAlipayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 支付宝支付系统参数服务实现
 *
 * @author 张权立
 * @date 2018/06/08
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SystemAlipayServiceImpl implements SystemAlipayService {

    private static Logger logger = LoggerFactory.getLogger(SystemAlipayServiceImpl.class);

    @Resource
    private SystemAlipayRepository systemAlipayRepository;

    @Override
    public void update(SystemAlipay systemAlipay) {
        systemAlipayRepository.save(systemAlipay);
        logger.info("修改支付宝支付系统参数");
    }

    @Override
    public SystemAlipay findOne(int id) {
        return systemAlipayRepository.findOne(id);
    }
}
