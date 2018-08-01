package com.liaoin.diving.mapper;

import com.liaoin.diving.entity.Banner;
import com.liaoin.diving.entity.Content;
import com.liaoin.diving.entity.Theme;
import com.liaoin.diving.view.BannerView;
import com.liaoin.diving.view.RecoContentView;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author : huqingxi
 * @describe :
 * @date 2018/06/07
 */
public interface ContentMapper {
     List<Content> findContentByThemeId(Integer id);

    List<Content> queryColumnConten(Integer id);

    List<Banner> findAllBanner();

    List<BannerView> findAllBannerView();

    List<Content> findFocusConten(List<Integer> ids);

    Integer findUpNum(@Param("id") Integer id);

    List<Content> findUserUp(Integer id);

    List<Theme> getTheme();

    /**
     * 通过contentId 查询评论数
     * @param id
     * @return
     */
    Integer findCommentNum(@Param("id") Integer id);

    List<RecoContentView> getRecommend();


    RecoContentView getRecommendById(@Param("id") Integer id);

    Integer setReco(Integer id);

    Content queryById(@Param("id") Integer id);
}
