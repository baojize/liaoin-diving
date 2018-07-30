package com.liaoin.diving.service.impl;

import com.github.pagehelper.PageHelper;
import com.liaoin.diving.common.PageHelp;
import com.liaoin.diving.dao.NavRepository;
import com.liaoin.diving.entity.Nav;
import com.liaoin.diving.service.NavService;
import com.liaoin.diving.utils.UpdateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @author : huqingxi
 * @describe :
 * @date 2018/06/07
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class NavServiceImpl implements NavService {
    @Resource
    private NavRepository navRepository;

    @Override
    public void del(Integer id) {
        navRepository.delete(id);
    }

    @Override
    public void update(Nav nav) {
        Nav one = navRepository.findOne(nav.getId());
        if (Objects.isNull(one)){
            return;
        }
        UpdateUtils.copyNonNullProperties(nav, one);
        navRepository.save(one);
    }

    @Override
    public void add(Nav nav) {
        navRepository.save(nav);
    }

    @Override
    public Nav findById(Integer id) {
        Nav nav = navRepository.findOne(id);
        return nav;
    }

    @Override
    public List<Nav> findAll(PageHelp pageHelp) {
        PageHelper.startPage(pageHelp.getStart(), pageHelp.getPageSize());
        List<Nav> navs = navRepository.findNav();
        return navs;
    }
}
