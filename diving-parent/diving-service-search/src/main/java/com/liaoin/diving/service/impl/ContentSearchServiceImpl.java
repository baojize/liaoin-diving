package com.liaoin.diving.service.impl;

import com.liaoin.diving.common.PageBean;
import com.liaoin.diving.entity.Content;
import com.liaoin.diving.search.ContentSearchRepository;
import com.liaoin.diving.service.ContentSearchService;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.index.query.WildcardQueryBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class ContentSearchServiceImpl implements ContentSearchService {

    @Resource
    private ContentSearchRepository contentSearchRepository;

    @Override
    public void save(List<Content> contentList) {
        contentSearchRepository.save(contentList);
    }

    @Override
    public void delete(List<Content> contentList) {
        contentSearchRepository.delete(contentList);
    }

    @Override
    public PageBean<Content> search(Pageable pageable, String key) {
        BoolQueryBuilder query = new BoolQueryBuilder();
        // 1.主题匹配
        {
            BoolQueryBuilder themeQuery = new BoolQueryBuilder();
            // 单词条模糊查询
            WildcardQueryBuilder wildcardQuery = new WildcardQueryBuilder("theme", "*" + key + "*");
            themeQuery.should(wildcardQuery);
            // 多词条模糊查询
            QueryStringQueryBuilder queryStringQuery = new QueryStringQueryBuilder(key).field("theme");
            themeQuery.should(queryStringQuery);
            query.should(themeQuery);
        }
        // 2.正文匹配
        {
            BoolQueryBuilder textQuery = new BoolQueryBuilder();
            // 单词条模糊查询
            WildcardQueryBuilder wildcardQuery = new WildcardQueryBuilder("text", "*" + key + "*");
            textQuery.should(wildcardQuery);
            // 多词条模糊查询
            QueryStringQueryBuilder queryStringQuery = new QueryStringQueryBuilder(key).field("text");
            textQuery.should(queryStringQuery);
            query.should(textQuery);
        }
        // 3.封装数据
        Page<Content> page = contentSearchRepository.search(query, pageable);
        PageBean<Content> pageBean = new PageBean<>();
        pageBean.setTotal(page.getTotalElements());
        pageBean.setRows(page.getContent());
        return pageBean;
    }
}
