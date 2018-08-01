package com.liaoin.diving.service;

import com.liaoin.diving.common.PageHelp;
import com.liaoin.diving.entity.CommodityOrder;
import com.liaoin.diving.entity.User;

import java.util.List;

public interface CommodityOrderService {
    void insert(User user,CommodityOrder commodityOrder);

    void update(CommodityOrder commodityOrder);

    void delete(Integer[] ids);

    CommodityOrder findOne(Integer id);

    List<CommodityOrder> findList(PageHelp pageHelp);
}
