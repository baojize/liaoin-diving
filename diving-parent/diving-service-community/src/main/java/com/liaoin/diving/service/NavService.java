package com.liaoin.diving.service;

import com.liaoin.diving.common.PageHelp;
import com.liaoin.diving.entity.Nav;

import java.util.List;

/**
 * @author : huqingxi
 * @describe :
 * @date 2018/06/07
 */
public interface NavService {
    void add(Nav nav);
    void del(Integer id);
    void update(Nav nav);
    Nav findById(Integer id);
    List<Nav > findAll(PageHelp pageHelp);

}
