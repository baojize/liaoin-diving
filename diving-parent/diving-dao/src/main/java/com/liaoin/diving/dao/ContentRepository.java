package com.liaoin.diving.dao;

import com.liaoin.diving.entity.Content;
import com.liaoin.diving.entity.Image;
import com.liaoin.diving.entity.User;
import com.liaoin.diving.entity.relationship.UserLike;
import com.liaoin.diving.vo.NoticeVo;
import org.hibernate.annotations.SQLUpdate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContentRepository extends JpaRepository<Content, Integer>, JpaSpecificationExecutor<Content> {



    List<Content> findByTypeAndIsDelete(String type, String isDelete);

    // 查询某话题下面的所有未删除内容
    @Query(value =
            "select * from t_content c \n" +
            "\tleft join  t_topic t\n" +
            "\ton c.topic_id = t.id\n" +
            "\twhere t.is_delete=0\n" +
            "\tand c.is_delete=0\n" +
            "\tand t.is_column =1\n" +
            "\tand t.id = ?1",
            nativeQuery = true)
    List<Content> findContentToTopicCol(Integer topicId);


    // 查询最新内容按发布时间降序
    List<Content> findAllByIsDeleteOrderByCreateTimeDesc(String isDelete);

    @Query(value = "select * from t_content where user_id =?1 and is_delete =0 ", nativeQuery = true)
    List<Content>  findContentByUserId(Integer userId);

    // 查询指定内容的点赞数
    @Query(value = "select count(1) from t_content  c join m_content_user m  on c.id = m.content_id  where m.content_id = ?1 and c.is_delete =  0", nativeQuery = true)
    Integer findLikeCount(Integer id);

    // 查询热门内容    点赞数>2
    @Query(value = "select * from t_content c join m_content_user m on  c.id = m.content_id where c.is_delete = 0  group by m.content_id having count(1) > 2", nativeQuery = true)
    List<Content> findHotContent();


    @Query(value = "select * from t_content where id = ?1 and type =?2 and is_delete = 0  ", nativeQuery = true)
    Content findById(Integer id , String type );

    @Query(value = "select * from t_content where id = ?1  and is_delete = 0 and type = 2  ", nativeQuery = true)
    Content findOther(Integer id);

    @Query(value = "select * from t_image where id = ?1", nativeQuery = true)
    Image findImgById(Integer id);

    @Query(value = "select content_id from m_content_user where user_id = ?1 ", nativeQuery = true)
    List<Integer> findNewMsg4Like(Integer userId);

    @Query(value = "select con.user_id from t_content con  JOIN  t_user_like li  on con.id = li.content_id  where con.id = ?1 ", nativeQuery = true)
    Integer findAuthor(Integer content_id);

    @Query(value = "select * from t_user_like where content_id in ( select id from t_content where user_id = ?1 ) order by create_time desc  ", nativeQuery = true)
    List<UserLike> findLikeUserIds(Integer userId);

    @Query(value = "select *  from t_content con join (select * from t_user_like where content_id in ( select id from t_content where user_id = ?1 ) )tar on con.id = tar.content_id order by tar.create_time desc", nativeQuery = true)
    List<NoticeVo> findByLikeVo(Integer userId);

    /*@Query(value = "alter table m_content_user add column NEW_COLUMN_NAME datetime", nativeQuery = true)
    @Modifying
    void addCol();*/
}