package com.liaoin.diving.service;

import com.liaoin.diving.common.PageHelp;
import com.liaoin.diving.view.SecondHandView;

import java.util.List;

/**
 * @author : huqingxi
 * @describe :
 * @date 2018/06/07
 */
public interface SecondHandService {

    SecondHandView findById(Integer id);

    List<SecondHandView> findAll(PageHelp pageHelp);

    // 通过二级分类查询
    List<SecondHandView> findByCategory(PageHelp pageHelp);

    // 通过大分类查询
    List<SecondHandView> findByBigCategory(PageHelp pageHelp, Integer id);
}
