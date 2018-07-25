package com.liaoin.diving.service;

import com.liaoin.diving.entity.Banner;
import com.liaoin.diving.entity.Content;

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
}
