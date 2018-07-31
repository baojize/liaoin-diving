package com.liaoin.diving.search;

import com.liaoin.diving.entity.Goods;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface GoodsSearchRepository extends ElasticsearchRepository<Goods, Integer> {

}
