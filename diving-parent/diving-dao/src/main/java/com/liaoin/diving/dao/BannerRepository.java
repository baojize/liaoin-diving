package com.liaoin.diving.dao;

import com.liaoin.diving.entity.Banner;
import com.liaoin.diving.entity.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author : huqingxi
 * @describe :
 * @date 2018/06/07
 */
public interface BannerRepository extends JpaRepository<Banner, Integer>, JpaSpecificationExecutor<Banner>{
    @Query(value = "select con.* from t_content con where id in ( select ban.content_id from t_banner ban where is_delete =  0 order by create_time desc ) limit ?1", nativeQuery = true)
    List<Content> get(Integer size);

    @Query(value = "select ban.content_id from t_banner ban where is_delete =  0 order by create_time desc limit ?1", nativeQuery = true)
    List<Integer> getContentIds(Integer size);

    @Query(value = "select * from t_content where id = ?1", nativeQuery = true)
    Content findByIdA(Integer id);

    @Query(value = "delete from t_banner where id = ?1", nativeQuery = true)
    @Modifying
    void delBanner(Integer id);

    @Query(value = "select * from t_banner where content_id = ?1", nativeQuery = true)
    Banner findByContent(Integer id);

    Banner findByActivityId(Integer id);
}
