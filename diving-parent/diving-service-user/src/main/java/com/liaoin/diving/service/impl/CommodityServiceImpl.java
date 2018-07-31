package com.liaoin.diving.service.impl;

import com.github.pagehelper.PageHelper;
import com.liaoin.diving.common.PageHelp;
import com.liaoin.diving.dao.CommodityRepository;
import com.liaoin.diving.entity.Commodity;
import com.liaoin.diving.entity.Group;
import com.liaoin.diving.mapper.CommodityMapper;
import com.liaoin.diving.service.CommodityService;
import com.liaoin.diving.utils.UpdateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommodityServiceImpl implements CommodityService {

    @Autowired
    private CommodityRepository commodityRepository;
    @Autowired
    private CommodityMapper commodityMapper;

    @Override
    public void insert(Commodity commodity) {
        commodity.setIsDelete(0);
        commodityRepository.save(commodity);
    }

    @Override
    public void update(Commodity commodity) {
        Commodity one = commodityRepository.findOne(commodity.getId());
        UpdateUtils.copyNonNullProperties(commodity, one);
        commodityRepository.save(one);
    }

    @Override
    public void delete(Integer[] ids) {
        List<Commodity> list = new ArrayList<>();
        for (Integer id : ids) {
            Commodity commodity = commodityRepository.findOne(id);
            if (commodity == null) {
                continue;
            }
            commodity.setIsDelete(1);
            list.add(commodity);
        }
        commodityRepository.save(list);
    }

    @Override
    public Commodity findOne(Integer id) {
        return commodityRepository.findOne(id);
    }

    /**
     * 分页查询
     * @param pageHelp
     * @return
     */
    @Override
    public List<Commodity> findList(PageHelp pageHelp) {
        PageHelper.startPage(pageHelp.getStart(), pageHelp.getPageSize());
        List<Commodity> commodityList = commodityMapper.findAll();
        return commodityList;
    }
}
