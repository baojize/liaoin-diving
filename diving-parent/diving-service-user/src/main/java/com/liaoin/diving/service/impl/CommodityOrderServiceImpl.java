package com.liaoin.diving.service.impl;

import com.github.pagehelper.PageHelper;
import com.liaoin.diving.common.PageHelp;
import com.liaoin.diving.dao.CommodityOrderRepository;
import com.liaoin.diving.dao.CommodityRepository;
import com.liaoin.diving.entity.Commodity;
import com.liaoin.diving.entity.CommodityOrder;
import com.liaoin.diving.entity.User;
import com.liaoin.diving.mapper.CommodityOrderMapper;
import com.liaoin.diving.service.CommodityOrderService;
import com.liaoin.diving.service.UserService;
import com.liaoin.diving.utils.UpdateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommodityOrderServiceImpl implements CommodityOrderService {
    
    @Autowired
    private CommodityOrderRepository commodityOrderRepository;
    @Autowired
    private CommodityOrderMapper commodityOrderMapper;
    @Autowired
    private CommodityRepository commodityRepository;
    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public void insert(User user,CommodityOrder commodityOrder) {
        //查询出积分商品
        Commodity commodity = commodityRepository.findOne(commodityOrder.getCommodityId());
        //判断积分
        if (user.getPoints() < commodity.getPoints()){
            throw new RuntimeException("您的积分不足");
        }
        //扣除积分
        userService.updatePoints(user.getId(),-commodity.getPoints());
        commodityOrder.setIsDelete(0);
        commodityOrder.setStatus(0);
        commodityOrder.setUserId(user.getId());
        commodityOrderRepository.save(commodityOrder);
    }

    @Override
    public void update(CommodityOrder commodityOrder) {
        CommodityOrder one = commodityOrderRepository.findOne(commodityOrder.getId());
        UpdateUtils.copyNonNullProperties(commodityOrder, one);
        commodityOrderRepository.save(one);
    }

    @Override
    public void delete(Integer[] ids) {
        List<CommodityOrder> list = new ArrayList<>();
        for (Integer id : ids) {
            CommodityOrder commodityOrder = commodityOrderRepository.findOne(id);
            if (commodityOrder == null) {
                continue;
            }
            commodityOrder.setIsDelete(1);
            list.add(commodityOrder);
        }
        commodityOrderRepository.save(list);
    }

    @Override
    public CommodityOrder findOne(Integer id) {
        return commodityOrderRepository.findOne(id);
    }

    /**
     * 条件分页查询
     * @param pageHelp
     * @return
     */
    @Override
    public List<CommodityOrder> findList(PageHelp pageHelp) {
        PageHelper.startPage(pageHelp.getStart(), pageHelp.getPageSize());
        List<CommodityOrder> commodityOrderList = commodityOrderMapper.findAll((Integer) pageHelp.getData());
        return commodityOrderList;
    }
}
