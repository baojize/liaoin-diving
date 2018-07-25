package com.liaoin.diving.service.impl;

import com.liaoin.diving.common.PageBean;
import com.liaoin.diving.dao.ImageRepository;
import com.liaoin.diving.entity.Image;
import com.liaoin.diving.service.ImageService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class ImageServiceImpl implements ImageService {

    @Resource
    private ImageRepository imageRepository;

    @Override
    public void insert(Image image) {
        image.setIsDelete("0");
        imageRepository.save(image);
    }

    @Override
    public void update(Image image) {
        imageRepository.save(image);
    }

    @Override
    public void delete(Integer[] ids) {
        List<Image> imageList = new ArrayList<>();
        for (Integer id : ids) {
            Image image = imageRepository.findOne(id);
            if (image == null) {
                continue;
            }
            image.setIsDelete("1");
            imageList.add(image);
        }
        imageRepository.save(imageList);
    }

    @Override
    public Image findOne(Integer id) {
        return imageRepository.findOne(id);
    }

    @Override
    public PageBean<Image> pageQuery(Pageable pageable, Image image) {
        // 1.查询条件
        Specification<Image> specification = new Specification<Image>() {
            @Override
            public Predicate toPredicate(Root<Image> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // 普通条件
                List<Predicate> list = new ArrayList<>();
                if (image != null) {
                    if (image.getId() != null) {
                        list.add(cb.equal(root.get("id").as(Integer.class), image.getId()));
                    }
                    if ((image.getContent() != null) && (!image.getContent().trim().equals(""))) {
                        list.add(cb.like(root.get("content").as(String.class), "%" + image.getContent() + "%"));
                    }
                    if ((image.getType() != null) && (!image.getType().trim().equals(""))) {
                        list.add(cb.like(root.get("type").as(String.class), "%" + image.getType() + "%"));
                    }
                    if ((image.getUrl() != null) && (!image.getUrl().trim().equals(""))) {
                        list.add(cb.like(root.get("url").as(String.class), "%" + image.getUrl() + "%"));
                    }
                }
                // 逻辑删除条件
                list.add(cb.notEqual(root.get("isDelete").as(String.class), "1"));
                // 合并查询条件
                Predicate[] predicates = new Predicate[list.size()];
                query.where(cb.and(list.toArray(predicates)));
                return query.getRestriction();
            }
        };
        // 2.获取数据
        Page<Image> page = imageRepository.findAll(specification, pageable);
        if (page.getTotalElements() == 0) {
            return null;
        }
        // 3.封装数据
        PageBean<Image> pageBean = new PageBean<>();
        pageBean.setTotal(page.getTotalElements());
        pageBean.setRows(page.getContent());
        return pageBean;
    }

    /**
     * 批量插入
     * @param list
     */
    @Override
    public void insert(List<Image> list) {
        imageRepository.save(list);
    }

}

