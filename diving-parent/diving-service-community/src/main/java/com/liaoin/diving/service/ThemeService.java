package com.liaoin.diving.service;

import com.liaoin.diving.entity.Theme;

import java.util.List;

/**
 * @author : huqingxi
 * @describe :
 * @date 2018/06/07
 */
public interface ThemeService {
    void  add(Theme theme);
    void  del(Integer id);
    void  update(Theme theme);
    Theme  findById(Integer id);
    List<Theme> findAll();
}
