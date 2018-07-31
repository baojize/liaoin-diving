package com.liaoin.diving.dao;

import com.liaoin.diving.entity.ContentUser;
import com.liaoin.diving.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface ContentUserRepository extends JpaRepository<ContentUser,Integer>,JpaSpecificationExecutor<ContentUser> {

    /**
     * 根据内容ID删除 内容——用户 关系表中的住居
     * @param contentId 内容ID
     * @return
     */
    @Query(value = "DELETE FROM m_content_user WHERE content_id = ?1", nativeQuery = true)
    @Modifying
    Integer deleteRelationship(Integer contentId);

    @Query(value = "insert into m_content_user (content_id, create_time, user_id) values (?1, ?2, ?3)",nativeQuery = true)
    @Modifying
    void saveUni(Integer contentId, Date date, Integer userId);

    ContentUser findByContentIdAndUserId(Integer contentId, Integer userId);

    List<ContentUser> findByContentId(Integer id);
}
