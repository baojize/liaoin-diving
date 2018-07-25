package com.liaoin.diving.service;

import com.liaoin.diving.entity.Official;

import java.util.List;
import java.util.Map;

/**
 * @author : huqingxi
 * @describe :
 * @date 2018/06/07
 */
public interface OfficialService {

    public List<Official> findAll();
    Integer del(Integer id);
    /**
     * 新增官方消息
     * @param official
     */
    Integer insert(Official official);

    /**
     * 新增  消息->图片(视频) 关系表
     * @param
     * @param
     */
    Integer insertImgRelation(Map<String, Object> map);

    Integer insertLabelRelation(Map<String, Object> map);
}
