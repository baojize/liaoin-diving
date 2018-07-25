package com.liaoin.diving.service.impl;

import com.liaoin.diving.common.PageBean;
import com.liaoin.diving.entity.Goods;
import com.liaoin.diving.search.GoodsSearchRepository;
import com.liaoin.diving.service.GoodsSearchService;
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
public class GoodsSearchServiceImpl implements GoodsSearchService {

    @Resource
    private GoodsSearchRepository goodsSearchRepository;

    @Override
    public void save(List<Goods> contentList) {
        goodsSearchRepository.save(contentList);
    }

    @Override
    public void delete(List<Goods> contentList) {
        goodsSearchRepository.delete(contentList);
    }

    @Override
    public PageBean<Goods> search(Pageable pageable, String key) {
        BoolQueryBuilder query = new BoolQueryBuilder();

        // 1.商品名称匹配
        {
            BoolQueryBuilder nameQuery = new BoolQueryBuilder();
            // 单词条模糊查询
            WildcardQueryBuilder wildcardQuery = new WildcardQueryBuilder("name", "*" + key + "*");
            nameQuery.should(wildcardQuery);
            // 多词条模糊查询
            QueryStringQueryBuilder queryStringQuery = new QueryStringQueryBuilder(key).field("name");
            nameQuery.should(queryStringQuery);
            query.should(nameQuery);
        }
        // 2.商品介绍匹配
        {
            BoolQueryBuilder introductionQuery = new BoolQueryBuilder();
            // 单词条模糊查询
            WildcardQueryBuilder wildcardQuery = new WildcardQueryBuilder("introduction", "*" + key + "*");
            introductionQuery.should(wildcardQuery);
            // 多词条模糊查询
            QueryStringQueryBuilder queryStringQuery = new QueryStringQueryBuilder(key).field("introduction");
            introductionQuery.should(queryStringQuery);
            query.should(introductionQuery);
        }

        // 3.封装数据
        Page<Goods> page = goodsSearchRepository.search(query, pageable);
        PageBean<Goods> pageBean = new PageBean<>();
        pageBean.setTotal(page.getTotalElements());
        pageBean.setRows(page.getContent());
        return pageBean;
    }
}
