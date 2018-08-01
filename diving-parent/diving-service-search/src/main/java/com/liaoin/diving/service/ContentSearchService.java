package com.liaoin.diving.service;

import com.liaoin.diving.common.PageBean;
import com.liaoin.diving.entity.Content;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ContentSearchService {
    void save(List<Content> contentList);

    void delete(List<Content> contentList);

    PageBean<Content> search(Pageable pageable, String key);
}
