package com.liaoin.diving.service;

import com.liaoin.diving.common.PageBean;
import com.liaoin.diving.entity.Image;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ImageService {
    void insert(Image image);

    void update(Image image);

    void delete(Integer[] ids);

    Image findOne(Integer id);

    PageBean<Image> pageQuery(Pageable pageable, Image image);

    void insert(List<Image> list);
}

