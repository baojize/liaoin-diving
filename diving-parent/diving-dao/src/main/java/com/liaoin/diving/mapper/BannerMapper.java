package com.liaoin.diving.mapper;

import com.liaoin.diving.entity.Banner;
import com.liaoin.diving.view.BannerView;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author : huqingxi
 * @describe :
 * @date 2018/06/07
 */
public interface BannerMapper {
    List<Integer> findActivityId();
    List<Integer> findContentId();

    void updateBanner(@Param("id") Integer id, @Param("hiddenId") Integer hiddenId);

    Banner findBannerById(Integer id);

    List<BannerView> findToActivity();

}
