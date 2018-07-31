package com.liaoin.diving.mapper;

import com.liaoin.diving.entity.Official;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author : huqingxi
 * @describe :
 * @date 2018/06/07
 */
public interface OfficialMapper {
    Integer insert(Official official);

    Integer insertRelation(Map<String, Object> map);

    List<Official> findAll();

    Integer del(Integer id);
}
