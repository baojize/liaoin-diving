package com.liaoin.diving.mapper;

import com.liaoin.diving.entity.Group;

public interface GroupMapper {
    /**
     * 移动端详情查询
     * @param id
     * @return
     */
    Group mobileFindOne(Integer id);
}
