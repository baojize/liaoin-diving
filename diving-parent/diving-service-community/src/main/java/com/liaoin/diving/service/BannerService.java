package com.liaoin.diving.service;

import com.liaoin.diving.common.PageHelp;
import com.liaoin.diving.entity.Banner;
import com.liaoin.diving.entity.Content;
import com.liaoin.diving.view.BannerView;

import java.util.List;

/**
 * @author : huqingxi
 * @describe :
 * @date 2018/06/07
 */
public interface BannerService {
    void  add(Banner banner);

    List<Content> get(Integer size);

    List<Banner> findAll();

    List<Integer> getContentIds(Integer size);

    Content findById(Integer id);

    void delBanner(Integer id);

    Banner findByContentId(Integer id);

    Banner findByActivityId(Integer id);

    List<Integer> findActivityId();

    List<Integer> findContentId();

    void updateBanner(Integer id, Integer isHidden);

    Banner findBannerById(Integer id);

    List<BannerView> findToActivity();

    List<BannerView> findAllBanner(PageHelp pageHelp);



}
