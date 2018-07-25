package com.liaoin.diving.mapper;

import com.liaoin.diving.entity.Topic;
import org.apache.ibatis.annotations.Param;

/**
 * @author : huqingxi
 * @describe :
 * @date 2018/06/07
 */
public interface TopicMapper {
    Topic findById(@Param("id") Integer id);
}
