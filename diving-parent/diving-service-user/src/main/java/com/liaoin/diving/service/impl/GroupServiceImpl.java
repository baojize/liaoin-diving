package com.liaoin.diving.service.impl;

import com.github.pagehelper.PageHelper;
import com.liaoin.diving.common.PageHelp;
import com.liaoin.diving.dao.ContentRepository;
import com.liaoin.diving.dao.GroupRepository;
import com.liaoin.diving.entity.Group;
import com.liaoin.diving.mapper.ContentMapper;
import com.liaoin.diving.mapper.GroupMapper;
import com.liaoin.diving.service.GroupService;
import com.liaoin.diving.utils.UpdateUtils;
import com.liaoin.diving.view.ContentView;
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
    @Autowired
    private ContentRepository contentRepository;
    @Autowired
    private ContentMapper contentMapper;

    @Override
    public void insert(Group group) {
        group.setIsDelete("0");
        group.setStatus(0);
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
        List<Group> groups = groupMapper.findAll();
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

    @Override
    public List<ContentView> mobileFindList(PageHelp pageHelp) {
        //查询列表 包含图片
        PageHelper.startPage(pageHelp.getStart(),pageHelp.getPageSize());
        List<ContentView> contentViewList = groupMapper.mobileFindList((Integer)pageHelp.getData());
        //查询点赞数
        for (ContentView c:contentViewList
             ) {
            c.setLikeNum(contentRepository.findLikeCount(c.getId()));
        }
        //查询评论数
        for (ContentView c:contentViewList
             ) {
            c.setCommentNum(contentMapper.findCommentNum(c.getId()));
        }
        return contentViewList;
    }

    /**
     * 更改发布数量
     * @param groupId
     * @param i
     */
    @Override
    public void updateReleaseNum(Integer groupId, long i) {
        if (Objects.isNull(groupId))return;
        Group one = groupRepository.findOne(groupId);
        Long num = 0L;
        if (!Objects.isNull(one.getReleaseNum())){
            num = one.getReleaseNum();
        }
        one.setReleaseNum(num+i);
        groupRepository.save(one);
    }

    /**
     * 更改成员数量
     * @param groupId
     * @param i
     */
    @Override
    public void updateMemberNum(Integer groupId,long i){
        if (Objects.isNull(groupId))return;
        Group one = groupRepository.findOne(groupId);
        Long num = 0L;
        if (!Objects.isNull(one.getMemberNum())){
            num = one.getMemberNum();
        }
        one.setMemberNum(num+i);
        groupRepository.save(one);
    }

    /**
     * 更改粉丝数量
     * @param groupId
     * @param i
     */
    @Override
    public void updateFansNum(Integer groupId, long i) {
        if (Objects.isNull(groupId))return;
        Group one = groupRepository.findOne(groupId);
        Long num = 0L;
        if (!Objects.isNull(one.getFansNum())){
            num = one.getFansNum();
        }
        one.setFansNum(num+i);
        groupRepository.save(one);
    }
}
