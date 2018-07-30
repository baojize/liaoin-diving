package com.liaoin.diving.service.impl;


import com.github.pagehelper.PageHelper;
import com.liaoin.diving.common.PageHelp;
import com.liaoin.diving.mapper.SecondHandMapper;
import com.liaoin.diving.service.SecondHandService;
import com.liaoin.diving.view.SecondHandView;
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
public class SecondHandServiceImpl implements SecondHandService {
    @Resource
    private SecondHandMapper secondHandMapper;


    @Override
    public SecondHandView findById(Integer id) {
        return null;
    }

    @Override
    public List<SecondHandView> findAll(PageHelp pageHelp) {
        PageHelper.startPage(pageHelp.getStart(), pageHelp.getPageSize());
        List<SecondHandView> all = secondHandMapper.findAll();
        return all;
    }

    @Override
    public List<SecondHandView> findByCategory(PageHelp pageHelp) {
        return null;
    }

    @Override
    public List<SecondHandView> findByBigCategory(PageHelp pageHelp, Integer id) {
        PageHelper.startPage(pageHelp.getStart(), pageHelp.getPageSize());
        List<SecondHandView> byBigCategory = secondHandMapper.findByBigCategory(id);
        return byBigCategory;
    }
}
