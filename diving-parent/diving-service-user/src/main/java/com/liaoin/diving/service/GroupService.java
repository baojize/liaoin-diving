package com.liaoin.diving.service;

import com.liaoin.diving.common.PageHelp;
import com.liaoin.diving.entity.Group;

import java.util.List;

public interface GroupService {

    void insert(Group group);

    void update(Group group);

    void delete(Integer[] ids);

    Group findOne(Integer id);

    List<Group> findList(PageHelp pageHelp);

    Group mobileFindOne(Integer id);
}
