package com.liaoin.diving.service.impl;

import com.github.pagehelper.PageHelper;
import com.liaoin.diving.common.PageHelp;
import com.liaoin.diving.dao.BannerRepository;
import com.liaoin.diving.entity.Banner;
import com.liaoin.diving.entity.Content;
import com.liaoin.diving.mapper.BannerMapper;
import com.liaoin.diving.service.BannerService;
import com.liaoin.diving.view.BannerView;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author : huqingxi
 * @describe :
 * @date 2018/06/07
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BannerServiceImpl implements BannerService {
    @Resource
    private  BannerRepository bannerRepository;
    @Resource
    private BannerMapper bannerMapper;

    // 设置(添加) 轮播图
    @Override
    public void add(Banner banner) {
        bannerRepository.save(banner);
    }

    // 获取轮播图, 默认获取前三张
    @Override
    public List<Content> get(Integer size) {
        return bannerRepository.get(size);
        //return bannerRepository.test();
    }

    @Override
    public List<Banner> findAll() {
        List<Banner> bannerList = bannerRepository.findAll();
        return bannerList;
    }

    @Override
    public List<Integer> getContentIds(Integer size) {
        return bannerRepository.getContentIds(size);
    }

    @Override
    public Content findById(Integer id) {
        return bannerRepository.findByIdA(id);
    }

    @Override
    public void delBanner(Integer id) {
     bannerRepository.delBanner(id);
    }

    @Override
    public Banner findByContentId(Integer id) {
        return bannerRepository.findByContent(id);
    }

    @Override
    public Banner findByActivityId(Integer id) {
        return bannerRepository.findByActivityId(id);
    }

    @Override
    public List<Integer> findActivityId() {
        return bannerMapper.findActivityId();
    }

    @Override
    public List<Integer> findContentId() {
        return bannerMapper.findContentId();
    }

    @Override
    public void updateBanner(Integer id, Integer hiddenId) {
        try {
            bannerMapper.updateBanner(id, hiddenId);
            return;
        } catch (Exception e) {
            throw new RuntimeException("内部错误", e);
        }
    }

    @Override
    public Banner findBannerById(Integer id) {
        Banner banner = bannerMapper.findBannerById(id);
        return banner;
    }


    @Override
    public List<BannerView> findToActivity() {

        List<BannerView> bannerViews = bannerMapper.findToActivity();
        return bannerViews;
    }


}
