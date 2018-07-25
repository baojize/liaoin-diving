package com.liaoin.diving.service.impl;

import com.liaoin.diving.dao.ThemeRepository;
import com.liaoin.diving.entity.Theme;
import com.liaoin.diving.service.ThemeService;
import com.liaoin.diving.utils.UpdateUtils;
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
public class ThemeServiceImpl implements ThemeService {
    @Resource
    private ThemeRepository themeRepository;

    @Override
    public void add(Theme theme) {
        themeRepository.save(theme);
    }

    @Override
    public void del(Integer id) {
        themeRepository.delete(id);
    }

    @Override
    public void update(Theme theme) {
        Theme one = themeRepository.findOne(theme.getId());
        UpdateUtils.copyNonNullProperties(theme, one);
        themeRepository.save(theme);
    }

    @Override
    public Theme findById(Integer id) {
        return themeRepository.findOne(id);
    }

    @Override
    public List<Theme> findAll() {
        return themeRepository.findAll();
    }
}
