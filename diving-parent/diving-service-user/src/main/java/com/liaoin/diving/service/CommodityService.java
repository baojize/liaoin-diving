package com.liaoin.diving.service;

import com.liaoin.diving.common.PageHelp;
import com.liaoin.diving.entity.Commodity;

import java.util.List;

public interface CommodityService {

    void insert(Commodity commodity);

    void update(Commodity commodity);

    void delete(Integer[] ids);

    Commodity findOne(Integer id);

    List<Commodity> findList(PageHelp pageHelp);
}
