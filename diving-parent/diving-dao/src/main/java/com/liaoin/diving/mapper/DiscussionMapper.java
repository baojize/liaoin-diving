package com.liaoin.diving.mapper;

import com.liaoin.diving.entity.Content;
import com.liaoin.diving.entity.Discussion;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author : huqingxi
 * @describe :
 * @date 2018/06/07
 */
public interface DiscussionMapper {
    List<Discussion> findDisForContentId(Integer contentId);


    void like(Map<String, Object> map);

    // 根据评论id和用户id查询关系表
    Discussion findDisForDisIdAndUserId(Map<String, Object> map);

    void notLike(Integer id);

    // 更新通知数量 + 1
    void updateAgreeNum(@Param("agreeNum") Integer agreeNum, @Param("id") Integer id);
}
