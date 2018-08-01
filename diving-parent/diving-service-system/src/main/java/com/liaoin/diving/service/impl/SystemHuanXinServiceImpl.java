package com.liaoin.diving.service.impl;

import com.liaoin.diving.dao.SystemHuanXinRepository;
import com.liaoin.diving.entity.SystemHuanXin;
import com.liaoin.diving.service.SystemHuanXinService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 环信系统参数服务实现
 *
 * @author 张权立
 * @date 2018/6/19 15:33
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SystemHuanXinServiceImpl implements SystemHuanXinService {

    private static Logger logger = LoggerFactory.getLogger(SystemHuanXinServiceImpl.class);

    @Resource
    private SystemHuanXinRepository systemHuanXinRepository;

    @Override
    public void update(SystemHuanXin systemHuanXin) {
        systemHuanXinRepository.save(systemHuanXin);
        logger.info("修改环信系统参数");
    }

    @Override
    public SystemHuanXin findOne(int id) {
        return systemHuanXinRepository.findOne(id);
    }
}
