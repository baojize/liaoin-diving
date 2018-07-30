package com.liaoin.diving.mapper;

import com.liaoin.diving.entity.Group;
import com.liaoin.diving.view.ContentView;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GroupMapper {
    /**
     * 移动端详情查询
     * @param id
     * @return
     */
    Group mobileFindOne(@Param("id") Integer id);

    /**
     * 移动端查询 俱乐部下的 content 列表
     * @param
     * @return
     */
    List<ContentView> mobileFindList(@Param("id") Integer id);
}
