package com.liaoin.diving.service;

import com.liaoin.diving.common.PageBean;
import com.liaoin.diving.common.PageHelp;
import com.liaoin.diving.entity.*;
import com.liaoin.diving.entity.relationship.UserLike;
import com.liaoin.diving.view.BannerView;
import com.liaoin.diving.vo.NoticeVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public interface ContentService {
    void insert(Content content);

    void update(Content content);

    void delete(Integer[] ids);

    Content findOne(Integer id);

    PageBean<Content> pageQuery(Pageable pageable, Content content);

    void like(Content content, User loginUser);


    Page<Content> recommend(Integer current, Integer size);

    void bacthReco(Integer[] ids);

    Page<Content> ring(Integer current, Integer size);

    List<Content> findByType(String s);

    List<Content> findColumnConten(Integer id);

    List<Content> findNewest();


    List<Content> findFocusConten(PageHelp pageHelp, List<Integer> list);

    List<SecondHand> findSecondHand();

    List<Content> findHotContent();

    void collect(User loginUser, Content content);

    Content findById(Integer id);

    Integer likeCount(Integer id);

    Image findImgById(Integer id);

    // 查询我的消息[点赞]
    List<Integer> findNewMsg4Like(Integer userId);

    // 查询内容的作者id(发布内容的用户)
    Integer findAuthor(Integer id);

    // 查询点赞用户的ids
    List<UserLike> findLikeUserIds(Integer userId);

    List<NoticeVo> findByLikeVo(Integer userId);

    Content findOther(Integer id);

    /**
     * 根据内容ID删除 内容——用户 关系表中的住居
     * @param contentId 内容ID
     * @return
     */
    Integer deleteRelationship(Integer contentId);

    /**
     * 内容——用户 关系表
     * 保存数据
     * @param contentId
     * @param likeList
     * @return
     */
    Integer saveRelationship(Integer contentId, List<ContentUser> likeList);

    List<Content> findContentByThemeId(PageHelp pageHelp, Integer id);

    List<Content> queryColumnConten(PageHelp pageHelp, Integer id);

    List<BannerView> findAllBanner(PageHelp pageHelp);

    Integer findUpNum(Integer id);

    List<Content> findUserUp(PageHelp pageHelp, Integer id);



}

