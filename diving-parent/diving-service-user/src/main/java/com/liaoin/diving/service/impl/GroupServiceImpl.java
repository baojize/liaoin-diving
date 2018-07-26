package com.liaoin.diving.service.impl;

import com.github.pagehelper.PageHelper;
import com.liaoin.diving.common.PageHelp;
import com.liaoin.diving.common.PageHelpSelect;
import com.liaoin.diving.dao.GroupRepository;
import com.liaoin.diving.entity.Group;
import com.liaoin.diving.entity.Group;
import com.liaoin.diving.entity.Ware;
import com.liaoin.diving.mapper.GroupMapper;
import com.liaoin.diving.service.GroupService;
import com.liaoin.diving.utils.UpdateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Transactional(rollbackFor = Exception.class)
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private GroupMapper groupMapper;

    @Override
    public void insert(Group group) {
        group.setIsDelete("0");
        groupRepository.save(group);
    }

    @Override
    public void update(Group group) {
        Group one = groupRepository.findOne(group.getId());
        UpdateUtils.copyNonNullProperties(group, one);
        groupRepository.save(one);
    }

    @Override
    public void delete(Integer[] ids) {
        List<Group> groupList = new ArrayList<>();
        for (Integer id : ids) {
            Group group = groupRepository.findOne(id);
            if (group == null) {
                continue;
            }
            group.setIsDelete("1");
            groupList.add(group);
        }
        groupRepository.save(groupList);
    }

    @Override
    public Group findOne(Integer id) {
        return groupRepository.findOne(id);
    }

    @Override
    public List<Group> findList(PageHelp pageHelp) {
        PageHelper.startPage(pageHelp.getStart(), pageHelp.getPageSize());
        List<Group> groups = groupRepository.findAll();
        return groups;
    }

    @Override
    public Group mobileFindOne(Integer id) {
        if (Objects.isNull(id)){
            throw new RuntimeException("id is null");
        }
        //查询详情
        Group group = groupMapper.mobileFindOne(id);
        return group;
    }
}
