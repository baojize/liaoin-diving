package com.liaoin.diving.service.Impl;

import com.liaoin.diving.dao.UserManagerRepository;
import com.liaoin.diving.entity.manager.Admin;
import com.liaoin.diving.mapper.UserManagerMapper;
import com.liaoin.diving.service.UserManagerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author : huqingxi
 * @describe :
 * @date 2018/06/07
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserManagerServiceImpl implements UserManagerService {
    @Resource
    private UserManagerMapper userManagerMapper;
    @Resource
    private UserManagerRepository userManagerRepository;

    @Override
    public Admin login(String account, String password) {
        Admin admin = userManagerMapper.login(account, password);
        if (Objects.isNull(admin)){
            return null;
        }
        return admin;
    }

    @Override
    public void add(String account, String password) {
        Admin admin = new Admin();
        admin.setAccount(account);
        admin.setPassword(password);
        admin.setCreate_time(new Date());
        userManagerRepository.save(admin);
    }

    @Override
    public Integer del(Integer id) {
        try {
            userManagerRepository.delete(id);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public Integer update(Admin admin) {
        Admin dbAdmin = userManagerRepository.findOne(admin.getId());
        if (Objects.isNull(dbAdmin)){
            return 0;
        }
        return null;
    }

    @Override
    public Admin findById() {
        return null;
    }

    @Override
    public List<Admin> findAll() {
        return null;
    }
}
